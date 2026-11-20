package com.surya.socialsphere.ui.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surya.socialsphere.data.model.Conversation
import com.surya.socialsphere.databinding.ItemConversationBinding

class ConversationsAdapter(
    private val onChatClick: (Conversation) -> Unit
) : ListAdapter<Conversation, ConversationsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemConversationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Conversation) {
            binding.tvPartnerName.text = item.partnerName
            binding.tvLastMessage.text = item.lastMessage

            val initials = item.partnerName.split(" ")
                .mapNotNull { it.firstOrNull()?.uppercase() }
                .take(2)
                .joinToString("")
            binding.tvAvatarInitials.text = initials.ifEmpty { "U" }

            binding.viewOnlineDot.visibility = if (item.isOnline) View.VISIBLE else View.GONE

            binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(
                item.timestamp,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
            )

            if (item.unreadCount > 0) {
                binding.tvUnreadBadge.visibility = View.VISIBLE
                binding.tvUnreadBadge.text = item.unreadCount.toString()
                binding.tvLastMessage.setTextColor(Color.WHITE)
                binding.tvLastMessage.setTypeface(null, Typeface.BOLD)
            } else {
                binding.tvUnreadBadge.visibility = View.GONE
                binding.tvLastMessage.setTextColor(Color.parseColor("#8A94A6"))
                binding.tvLastMessage.setTypeface(null, Typeface.NORMAL)
            }

            binding.root.setOnClickListener { onChatClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConversationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation) =
            oldItem.roomId == newItem.roomId

        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation) =
            oldItem == newItem
    }
}