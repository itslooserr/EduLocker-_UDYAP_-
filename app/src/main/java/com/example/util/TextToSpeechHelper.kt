package com.example.util

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

object TextToSpeechHelper {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var currentUtteranceId: String? = null
    var isSpeakingCallback: ((Boolean) -> Unit)? = null

    fun initialize(context: Context, onReady: () -> Unit = {}) {
        if (tts == null) {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts?.setLanguage(Locale("en", "IN"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts?.setLanguage(Locale.US)
                    }
                    tts?.setPitch(1.05f)
                    tts?.setSpeechRate(0.98f)
                    isInitialized = true
                    Log.d("TTSHelper", "TextToSpeech initialized successfully.")
                    onReady()
                } else {
                    Log.e("TTSHelper", "TextToSpeech initialization failed.")
                }
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    isSpeakingCallback?.invoke(true)
                }

                override fun onDone(utteranceId: String?) {
                    isSpeakingCallback?.invoke(false)
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    isSpeakingCallback?.invoke(false)
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    isSpeakingCallback?.invoke(false)
                }
            })
        }
    }

    fun speak(
        context: Context,
        text: String,
        onStart: () -> Unit = {},
        onDone: () -> Unit = {}
    ) {
        if (!isInitialized || tts == null) {
            initialize(context) {
                doSpeak(text, onStart, onDone)
            }
        } else {
            doSpeak(text, onStart, onDone)
        }
    }

    private fun doSpeak(
        text: String,
        onStart: () -> Unit,
        onDone: () -> Unit
    ) {
        val cleanText = text
            .replace("**", "")
            .replace("#", "")
            .replace("•", " ")
            .replace("*", "")
            .replace("\n", ". ")

        val utteranceId = "utterance_${System.currentTimeMillis()}"
        currentUtteranceId = utteranceId
        
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }

        onStart()
        isSpeakingCallback?.invoke(true)

        tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    fun stop() {
        try {
            if (tts?.isSpeaking == true) {
                tts?.stop()
            }
            isSpeakingCallback?.invoke(false)
        } catch (e: Exception) {
            Log.e("TTSHelper", "Error stopping TTS", e)
        }
    }

    fun isSpeaking(): Boolean {
        return tts?.isSpeaking == true
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (e: Exception) {
            Log.e("TTSHelper", "Error shutting down TTS", e)
        }
    }
}
