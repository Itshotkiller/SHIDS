package com.example.smarthomesecurity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessage(
    val text: String,
    val sender: String
) : Parcelable