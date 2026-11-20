package com.surya.socialsphere.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surya.socialsphere.databinding.FragmentEditProfileSheetBinding

// 1. Added a callback here so we can send the data back!
class EditProfileBottomSheet(
    private val onProfileSaved: (String, String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentEditProfileSheetBinding? = null
    private val binding get() = _binding!!

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivEditProfilePic.setImageURI(uri)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditProfileSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openGallery = View.OnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.ivEditProfilePic.setOnClickListener(openGallery)
        binding.tvChangePhoto.setOnClickListener(openGallery)

        // 2. Grab the text and send it back when Save is clicked
        binding.btnSaveProfile.setOnClickListener {
            val newName = binding.etEditName.text.toString()
            val newBio = binding.etEditBio.text.toString()

            // Send the data back to the ProfileFragment
            onProfileSaved(newName, newBio)

            Toast.makeText(requireContext(), "Profile Updated!", Toast.LENGTH_SHORT).show()
            dismiss() // Close the sheet
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}