package com.codewithkhushbu.mygemini

import android.graphics.Bitmap
import android.hardware.biometrics.BiometricPrompt
import com.codewithkhushbu.mygemini.data.Chat

data class ChatState(
    val chatList:MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap?=null
)
