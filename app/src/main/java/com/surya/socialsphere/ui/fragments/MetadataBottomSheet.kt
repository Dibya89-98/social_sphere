package com.surya.socialsphere.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surya.socialsphere.databinding.FragmentMetadataSheetBinding

class MetadataBottomSheet(
    private val type: String,
    private val onItemSelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentMetadataSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMetadataSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the list based on which button was clicked
        when (type) {
            "TAG" -> {
                binding.tvSheetTitle.text = "Tag People"
                binding.tvOption1.text = "👥 Aarav Sharma"
                binding.tvOption2.text = "👥 Meera Patel"
                binding.tvOption3.text = "👥 Rohan Kumar"
            }
            "LOCATION" -> {
                binding.tvSheetTitle.text = "Add Location"
                binding.tvOption1.text = "📍 Bhubaneswar, Odisha"
                binding.tvOption2.text = "📍 Keshari Talkies"
                binding.tvOption3.text = "📍 Esplanade One Mall"
            }
            "MUSIC" -> {
                binding.tvSheetTitle.text = "Add Music"
                binding.tvOption1.text = "🎵 Dooron Dooron - Paresh Pahuja"
                binding.tvOption2.text = "🎵 Blinding Lights - The Weeknd"
                binding.tvOption3.text = "🎵 Apna Bana Le - Arijit Singh"
            }
        }

        // When a user taps an option, send the text back to the main screen and close the sheet
        binding.tvOption1.setOnClickListener { selectItem(binding.tvOption1.text.toString()) }
        binding.tvOption2.setOnClickListener { selectItem(binding.tvOption2.text.toString()) }
        binding.tvOption3.setOnClickListener { selectItem(binding.tvOption3.text.toString()) }
    }

    private fun selectItem(item: String) {
        onItemSelected(item)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}