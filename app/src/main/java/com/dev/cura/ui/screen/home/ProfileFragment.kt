package com.dev.cura.ui.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.SocialRepository
import com.dev.cura.databinding.FragmentProfileBinding
import com.dev.cura.domain.model.UserDetails
import com.dev.cura.ui.screen.auth.LoginActivity
import com.dev.cura.ui.viewmodel.SocialViewModel
import com.dev.cura.ui.viewmodel.SocialViewModelFactory
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ① Grab the Activity-scoped ViewModel instance
    private val socialViewModel: SocialViewModel by activityViewModels {
        SocialViewModelFactory(
            SocialRepository(RetrofitClient.instance)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ② Observe userData and update UI when it's set
        socialViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let { bindUserData(it) }
        }

        binding.btnLogout.setOnClickListener {
            // Clear all saved prefs
            requireContext()
                .getSharedPreferences("UserPrefs", 0)
                .edit()
                .clear()
                .apply()

            // Navigate back to Login
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun bindUserData(user: UserDetails) {
        binding.profileName.text = user.name
        Picasso.get()
            .load(user.photo)
            .into(binding.profileImage)
        // Bind any additional fields here (e.g., email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
