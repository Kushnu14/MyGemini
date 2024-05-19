package com.codewithkhushbu.mygemini

import android.graphics.Bitmap

sealed class ChatUIEvent {
     data class UpdatePrompt(val newPrompt: String):ChatUIEvent()
     data class SendPrompt(
         val prompt: String,
         val bitmap: Bitmap?):ChatUIEvent()
}