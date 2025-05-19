package com.example.smarthomesecurity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alert(
    val type: String? = null,
    val message: String? = null,
    val timestamp: String? = null
) : Parcelable