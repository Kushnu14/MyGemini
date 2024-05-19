package com.codewithkhushbu.mygemini

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithkhushbu.mygemini.data.Chat
import com.codewithkhushbu.mygemini.data.ChatData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel:ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event:ChatUIEvent){
        when(event){
            is ChatUIEvent.SendPrompt ->{
                if(event.prompt.isNotEmpty()){
                    addPrompt(event.prompt,event.bitmap)
                    if(event.bitmap!=null){
                        getRespnoseWithImage(event.prompt,event.bitmap)

                    }else{
                        getRespnose(event.prompt)
                    }

                }

            }
            is ChatUIEvent.UpdatePrompt ->{
                _chatState.update {
                    it.copy(prompt =event.newPrompt)
                }
            }
        }
    }
    private  fun addPrompt(prompt:String,bitmap: Bitmap?){
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt,bitmap,true))
                },
                prompt = "",
                bitmap=null
            )
        }
    }
    private fun getRespnose(prompt: String){
        viewModelScope.launch {
            val chat= ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }
    private fun getRespnoseWithImage(prompt: String,bitmap: Bitmap){
        viewModelScope.launch {
            val chat= ChatData.getResponseWithImage(prompt,bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }
}