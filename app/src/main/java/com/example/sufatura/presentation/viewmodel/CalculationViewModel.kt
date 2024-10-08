package com.example.sufatura.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor() : ViewModel(){
    private val _ocrResult = MutableLiveData<String>()
    val ocrResult: LiveData<String> get() = _ocrResult

    fun processImageWithFirebase(uri: Uri, context: android.content.Context) {
        try {
            val image = FirebaseVisionImage.fromFilePath(context, uri)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    _ocrResult.value = firebaseVisionText.text
                }
                .addOnFailureListener { e ->
                    _ocrResult.value = "Hata: ${e.message}"
                }
        } catch (e: Exception) {
            _ocrResult.value = "İşlenemedi: ${e.message}"
        }
    }
}