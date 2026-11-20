package com.surya.socialsphere.data.model

data class Conversation(
    val roomId: String = "",
    val partnerId: String = "",
    val partnerName: String = "",
    val lastMessage: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val isOnline: Boolean = false
)