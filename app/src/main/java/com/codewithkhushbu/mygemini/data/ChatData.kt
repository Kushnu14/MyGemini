package com.codewithkhushbu.mygemini.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    val api_key="AIzaSyDLhu4b7Z8tGgqP6yuOgC9Q9jl3CTBglJs"
    //Without image i.e. bitmap
    suspend fun getResponse(prompt :String):Chat{
        val generativeModel =  GenerativeModel(
            modelName="gemini-pro",
            apiKey = api_key
        )
        try {
            val response= withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            return Chat(
                prompt=response.text?:"error",
                bitmap = null,
                isFromUser = false
            )

        }catch (e:Exception){
            return Chat(
                prompt=e.message?:"error",
                bitmap = null,
                isFromUser = false
            )


        }
    }
    //For image i.e. bitmap
    suspend fun getResponseWithImage(prompt :String,bitmap: Bitmap):Chat{
        val generativeModel =  GenerativeModel(
            modelName="gemini-pro-vision",
            apiKey = api_key
        )
        try {
            val inputContent= content {
                image(bitmap)
                text(prompt)
            }
            val response= withContext(Dispatchers.IO){
                generativeModel.generateContent(inputContent)
            }

            return Chat(
                prompt=response.text?:"error",
                bitmap = null,
                isFromUser = false
            )

        }catch (e:Exception){
            return Chat(
                prompt=e.message?:"error",
                bitmap = null,
                isFromUser = false
            )


        }
    }
}