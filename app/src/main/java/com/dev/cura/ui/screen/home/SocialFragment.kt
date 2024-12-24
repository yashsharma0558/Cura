package com.dev.cura.ui.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.cura.R
import com.dev.cura.databinding.FragmentHomeBinding
import com.dev.cura.databinding.FragmentProfileBinding
import com.dev.cura.databinding.FragmentSocialBinding


class SocialFragment : Fragment() {
    private var _binding: FragmentSocialBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}