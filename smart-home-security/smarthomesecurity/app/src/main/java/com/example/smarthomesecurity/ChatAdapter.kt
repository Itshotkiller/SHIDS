package com.example.smarthomesecurity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomesecurity.databinding.ItemChatBinding

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.chatText.text = message.text
        holder.binding.chatText.setBackgroundResource(
            if (message.sender == "user") R.drawable.bg_user_message else R.drawable.bg_bot_message
        )
        holder.binding.chatText.gravity = if (message.sender == "user") android.view.Gravity.END else android.view.Gravity.START
    }

    override fun getItemCount(): Int = messages.size
}