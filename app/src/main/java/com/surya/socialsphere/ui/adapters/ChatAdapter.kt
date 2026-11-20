package com.surya.socialsphere.ui.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surya.socialsphere.R
import com.surya.socialsphere.data.model.ChatMessage
import com.surya.socialsphere.databinding.ItemChatMessageBinding

class ChatAdapter(
    private val currentUserId: String,
    private val onReact: (messageId: String, emoji: String) -> Unit
) : ListAdapter<ChatMessage, ChatAdapter.MessageViewHolder>(DiffCallback()) {

    inner class MessageViewHolder(private val binding: ItemChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: ChatMessage) {
            val isMe = msg.senderId == currentUserId

            binding.messageRoot.gravity = if (isMe) Gravity.END else Gravity.START
            binding.tvMessageText.setBackgroundResource(
                if (isMe) R.drawable.bg_bubble_sent else R.drawable.bg_bubble_received
            )
            binding.tvMessageText.text = msg.text

            binding.reactionPicker.visibility = View.GONE

            binding.tvMessageText.setOnLongClickListener {
                binding.reactionPicker.visibility =
                    if (binding.reactionPicker.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                true
            }

            val reactClick = { emoji: String ->
                onReact(msg.id, emoji)
                binding.reactionPicker.visibility = View.GONE
            }
            binding.reactHeart.setOnClickListener { reactClick("❤️") }
            binding.reactLaugh.setOnClickListener { reactClick("😂") }
            binding.reactFire.setOnClickListener { reactClick("🔥") }
            binding.reactLike.setOnClickListener { reactClick("👍") }

            if (msg.reactions.isNotEmpty()) {
                binding.tvReactions.visibility = View.VISIBLE
                binding.tvReactions.text = msg.reactions.values.distinct().joinToString(" ")
            } else {
                binding.tvReactions.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemChatMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) =
            oldItem == newItem
    }
}