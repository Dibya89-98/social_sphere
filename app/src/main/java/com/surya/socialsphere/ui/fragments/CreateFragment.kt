package com.surya.socialsphere.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.surya.socialsphere.R
import com.surya.socialsphere.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    // Initialize the Android Photo Picker
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivPostImage.setImageURI(uri)
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Photo Picker Logic
        binding.btnReplaceMedia.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
        binding.ivPostImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        // AI Polish Suggestion Logic
        binding.btnPolishAi.setOnClickListener {
            binding.etCaption.setText("Chasing neon shadows in the city. Every corner tells a story... ✨ #UrbanStories #VYRO")
            Toast.makeText(requireContext(), "Caption polished with VYRO AI!", Toast.LENGTH_SHORT).show()
        }

        // Share Button Logic
        binding.btnSharePost.setOnClickListener {
            Toast.makeText(requireContext(), "Processing upload...", Toast.LENGTH_SHORT).show()
        }

        // Initialize our new tabs
        setupModeSelector()

        // --- NEW: Bottom Sheet Click Listeners ---

        // Tag People Click
        binding.btnTagPeople.setOnClickListener {
            MetadataBottomSheet("TAG") { selectedItem ->
                binding.btnTagPeople.text = selectedItem
                binding.btnTagPeople.setTextColor(Color.parseColor("#3B82F6")) // Turns blue when selected
            }.show(childFragmentManager, "TAG_SHEET")
        }

        // Add Location Click
        binding.btnAddLocation.setOnClickListener {
            MetadataBottomSheet("LOCATION") { selectedItem ->
                binding.btnAddLocation.text = selectedItem
                binding.btnAddLocation.setTextColor(Color.parseColor("#3B82F6"))
            }.show(childFragmentManager, "LOC_SHEET")
        }

        // Add Music Click
        binding.btnAddMusic.setOnClickListener {
            MetadataBottomSheet("MUSIC") { selectedItem ->
                binding.btnAddMusic.text = selectedItem
                binding.btnAddMusic.setTextColor(Color.parseColor("#3B82F6"))
            }.show(childFragmentManager, "MUSIC_SHEET")
        }
    }

    private fun setupModeSelector() {
        val modes = listOf(binding.tvModePost, binding.tvModeStory, binding.tvModeReel, binding.tvModeLive)

        binding.tvModePost.setOnClickListener {
            updateTabStyling(binding.tvModePost, modes)
            setPreviewHeight(220) // Standard post height
            binding.btnReplaceMedia.text = "📷 Replace media"
            binding.etCaption.visibility = View.VISIBLE
        }

        binding.tvModeStory.setOnClickListener {
            updateTabStyling(binding.tvModeStory, modes)
            setPreviewHeight(400) // Taller 9:16 height
            binding.btnReplaceMedia.text = "📷 Add to Story"
            binding.etCaption.visibility = View.GONE // Hide caption for stories
        }

        binding.tvModeReel.setOnClickListener {
            updateTabStyling(binding.tvModeReel, modes)
            setPreviewHeight(400) // Taller 9:16 height
            binding.btnReplaceMedia.text = "🎥 Upload Video"
            binding.etCaption.visibility = View.VISIBLE
        }

        binding.tvModeLive.setOnClickListener {
            updateTabStyling(binding.tvModeLive, modes)
            setPreviewHeight(400) // Taller 9:16 height
            binding.btnReplaceMedia.text = "🔴 Start Live Camera"
            binding.etCaption.visibility = View.GONE
        }
    }

    private fun updateTabStyling(selectedTab: TextView, allTabs: List<TextView>) {
        // Reset all tabs to gray text and no background
        allTabs.forEach { tab ->
            tab.background = null
            tab.setTextColor(Color.parseColor("#6B7280"))
            tab.typeface = Typeface.DEFAULT
        }
        // Highlight the selected tab
        selectedTab.setBackgroundResource(R.drawable.bg_pill_dark)
        selectedTab.setTextColor(Color.parseColor("#FFFFFF"))
        selectedTab.typeface = Typeface.DEFAULT_BOLD
    }

    private fun setPreviewHeight(dp: Int) {
        val frameLayout = binding.ivPostImage.parent as FrameLayout
        val params = frameLayout.layoutParams
        val density = resources.displayMetrics.density
        params.height = (dp * density).toInt()
        frameLayout.layoutParams = params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}