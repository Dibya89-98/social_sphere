package com.surya.socialsphere.ui.activities.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.surya.socialsphere.data.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel(
    private val roomId: String,
    private val currentUserId: String
) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val messagesRef = db.collection("chat_rooms").document(roomId).collection("messages")
    private val roomRef = db.collection("chat_rooms").document(roomId)

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isOtherUserTyping = MutableStateFlow(false)
    val isOtherUserTyping: StateFlow<Boolean> = _isOtherUserTyping.asStateFlow()

    init {
        listenForMessages()
        listenForTypingStatus()
    }

    private fun listenForMessages() {
        messagesRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener
                val list = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(ChatMessage::class.java)?.copy(id = doc.id)
                }
                _messages.value = list
            }
    }

    private fun listenForTypingStatus() {
        roomRef.addSnapshotListener { snapshot, _ ->
            val typingUsers = snapshot?.get("typingUsers") as? Map<*, *>
            val othersTyping = typingUsers?.any { (userId, isTyping) ->
                userId != currentUserId && isTyping == true
            } ?: false
            _isOtherUserTyping.value = othersTyping
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        val trimmed = text.trim()
        val now = System.currentTimeMillis()
        val newMessage = ChatMessage(
            senderId = currentUserId,
            text = trimmed,
            timestamp = now
        )
        messagesRef.add(newMessage)
        roomRef.set(
            mapOf(
                "roomId" to roomId,
                "lastMessage" to trimmed,
                "timestamp" to now
            ),
            SetOptions.merge()
        )
        setTypingStatus(false)
    }

    fun toggleReaction(messageId: String, emoji: String) {
        val docRef = messagesRef.document(messageId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            val currentReactions = (snapshot.get("reactions") as? Map<String, String>)?.toMutableMap()
                ?: mutableMapOf()

            if (currentReactions[currentUserId] == emoji) {
                currentReactions.remove(currentUserId)
            } else {
                currentReactions[currentUserId] = emoji
            }
            transaction.update(docRef, "reactions", currentReactions)
        }
    }

    fun setTypingStatus(isTyping: Boolean) {
        roomRef.set(
            mapOf("typingUsers" to mapOf(currentUserId to isTyping)),
            SetOptions.merge()
        )
    }
}