package com.surya.socialsphere.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surya.socialsphere.databinding.FragmentCreateBottomSheetBinding


class CreatePostBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateBottomSheetBinding? = null
    private val binding get() = _binding!!

    // Initialize the Android Photo Picker
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // A file was selected! We will pass this to the upload screen next.
            handleMediaSelection(uri)
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Launch picker for Images only
        binding.btnNewPost.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Launch picker for Videos only
        binding.btnNewMoment.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
    }

    private fun handleMediaSelection(uri: Uri) {
        // TODO: In the next step, we will pass this URI to the "UploadPostActivity"
        // to write captions, crop, and upload to Firebase Storage.
        Toast.makeText(requireContext(), "Selected: $uri", Toast.LENGTH_LONG).show()
        dismiss() // Close the bottom sheet
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CreatePostBottomSheet"
    }
}