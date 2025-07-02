package com.dev.cura.ui.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.cura.R
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.SocialRepository
import com.dev.cura.databinding.FragmentHomeBinding
import com.dev.cura.domain.model.RemedialData
import com.dev.cura.domain.model.SurveyResponse
import com.dev.cura.domain.model.UserDetails
import com.dev.cura.ui.adapter.AllRemedialPlansAdapter
import com.dev.cura.ui.adapter.OngoingRemedialPlansAdapter
import com.dev.cura.ui.viewmodel.SocialViewModel
import com.dev.cura.ui.viewmodel.SocialViewModelFactory
import com.dev.cura.util.CircleTransform
import com.squareup.picasso.Picasso
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ① Share the Activity-scoped VM
    private val socialViewModel: SocialViewModel by activityViewModels {
        SocialViewModelFactory(
            SocialRepository(RetrofitClient.instance)
        )
    }

    private lateinit var ongoingAdapter: OngoingRemedialPlansAdapter
    private lateinit var allAdapter: AllRemedialPlansAdapter

    companion object {
        private const val TAG = "HomeFragment"

        // Map remedy names (lowercased) to drawable IDs
        private val remedyIconMap = mapOf(
            "cooking" to R.drawable.icon_cooking,
            "gaming" to R.drawable.icon_gaming,
            "meditation" to R.drawable.icon_meditation,
            "reading" to R.drawable.icon_reading,
            "swimming" to R.drawable.icon_swimming,
            "yoga" to R.drawable.icon_yoga
        )

        private fun getIconResId(remedy: String) =
            remedyIconMap[remedy.lowercase(Locale.getDefault())]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to profile on avatar click
        binding.profileImageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ProfileFragment)
        }

        // Initialize adapters with empty lists
        ongoingAdapter = OngoingRemedialPlansAdapter()
        allAdapter = AllRemedialPlansAdapter()

        binding.ongoingRemedialPlansRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ongoingAdapter
        }
        binding.allRemedialPlansRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = allAdapter
        }

        // ② Observe once the Activity sets the data
        socialViewModel.userData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Log.d(TAG, "Received userData: $user")
                bindUserData(user)
                setupRemedyLists(user.surveyResponse)
            } else {
                Log.d(TAG, "userData is still null")
            }
        }
    }

    private fun bindUserData(user: UserDetails) {
        Picasso.get()
            .load(user.photo)
            .transform(CircleTransform())
            .into(binding.profileImageView)
        // you can bind other user fields here if needed
    }

    private fun setupRemedyLists(survey: SurveyResponse) {
        // Ongoing plans
        val ongoing = survey.selected_remedies
            ?.mapNotNull { remedy ->
                getIconResId(remedy.name)?.let { icon ->
                    RemedialData(icon, remedy.name)
                }
            }
        if (ongoing != null) {
            ongoingAdapter.updateData(ongoing)
        }

        // All plans
        val all = remedyIconMap.map { (name, icon) ->
            RemedialData(icon, name)
        }
        allAdapter.updateData(all)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
