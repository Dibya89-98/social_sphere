package com.surya.socialsphere.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.surya.socialsphere.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root // This must be the absolute last line in onCreateView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Setup Edit Profile Button (Correctly placed to open the Bottom Sheet!)
        binding.btnEditProfile.setOnClickListener {
            EditProfileBottomSheet().show(childFragmentManager, "EDIT_PROFILE_SHEET")
        }

        // 2. Setup Settings Button
        binding.btnSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Settings...", Toast.LENGTH_SHORT).show()
        }

        // 3. Setup Profile Pic click
        binding.cvProfilePic.setOnClickListener {
            Toast.makeText(requireContext(), "View Profile Picture", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}