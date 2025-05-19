package com.example.smarthomesecurity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthomesecurity.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messages.add(ChatMessage("Hello! How can I assist you with your Smart Home Security?", "bot"))
        chatAdapter = ChatAdapter(messages)
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HelpActivity)
            adapter = chatAdapter
        }

        binding.emailText.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:support@smarthomesecurity.com")
            startActivity(intent)
        }

        binding.phoneText.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+18001234567")
            startActivity(intent)
        }

        binding.sendButton.setOnClickListener {
            val input = binding.chatInput.text.toString()
            if (input.isNotEmpty()) {
                messages.add(ChatMessage(input, "user"))
                val response = when (input.lowercase()) {
                    "how to arm system" -> "To arm the system, go to the Dashboard and tap the 'Arm System' button."
                    "how to check alerts" -> "Check the Alerts section in the Dashboard to view recent motion, fire, and smoke alerts."
                    "contact support" -> "Reach support at support@smarthomesecurity.com or call +1-800-123-4567."
                    else -> "Sorry, I didn’t understand. Try asking about arming the system, checking alerts, or contacting support."
                }
                messages.add(ChatMessage(response, "bot"))
                chatAdapter.notifyDataSetChanged()
                binding.chatInput.text.clear()
                binding.chatRecyclerView.scrollToPosition(messages.size - 1)
            }
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.chatbotToggle.setOnClickListener {
            binding.chatLayout.visibility = if (binding.chatLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }
}