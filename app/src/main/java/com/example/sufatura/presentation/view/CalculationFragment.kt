package com.example.sufatura.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sufatura.R
import com.example.sufatura.databinding.FragmentCalculationBinding
import com.example.sufatura.presentation.viewmodel.CalculationViewModel
import java.io.File

class CalculationFragment : Fragment() {

    private lateinit var fragmentCalculationBinding: FragmentCalculationBinding
    val viewModel: CalculationViewModel by activityViewModels()
    private var imageUri: Uri? = null

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri?.let {
                    viewModel.processImageWithFirebase(it, requireContext())
                }
            } else {
                Toast.makeText(requireContext(), "Resim çekilemedi", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCalculationBinding = FragmentCalculationBinding.inflate(inflater, container, false)

        fragmentCalculationBinding.submitButton.setOnClickListener {
            val customerNumber = fragmentCalculationBinding.customerNumberInput.text.toString()
            val meterReading = fragmentCalculationBinding.meterReadingInput.text.toString()

            if (customerNumber.isBlank() || meterReading.isBlank()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT)
                    .show()
            } else {
                findNavController().navigate(
                    CalculationFragmentDirections.actionCalculationFragmentToResultFragment(
                        customerNumber,
                        meterReading
                    )
                )
            }
        }

        fragmentCalculationBinding.ocrButton.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        viewModel.ocrResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), "OCR Sonucu: $result", Toast.LENGTH_LONG).show()
        }

        return fragmentCalculationBinding.root
    }


    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Kamera izni gerekli", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        val imageFile = File.createTempFile("temp_image", ".jpg", requireContext().cacheDir)
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            imageFile
        )

        takePicture.launch(imageUri)
    }


    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
}
