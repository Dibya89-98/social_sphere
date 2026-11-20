package com.surya.socialsphere.data.model

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val reactions: Map<String, String> = emptyMap(),
    val sharedPostTitle: String? = null
)