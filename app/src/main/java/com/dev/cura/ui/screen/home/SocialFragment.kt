package com.dev.cura.ui.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.cura.R
import com.dev.cura.data.model.CommentDto
import com.dev.cura.data.model.PostDto
import com.dev.cura.databinding.FragmentSocialBinding
import com.dev.cura.domain.model.UserDetails
import com.dev.cura.ui.adapter.PostAdapter
import com.dev.cura.ui.viewmodel.SocialViewModel


class SocialFragment : Fragment() {
    private var _binding: FragmentSocialBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: SocialViewModel
    private lateinit var userDetails: UserDetails
    private lateinit var postsAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val apiService = RetrofitClient.instance
//        val repository = SocialRepository(apiService)
//        val factory = SocialViewModelFactory(repository)
//        viewModel = factory.create(SocialViewModel::class.java)
//        lifecycleScope.launch {
//            val data = viewModel.userData.value
//            if (data != null) {
//                userDetails = data
//            } else {
//                userDetails = UserDetails(
//                    name = "Yash",
//                    token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YXNoc2hhcm1hMDU1OEBnbWFpbC5jb20iLCJpYXQiOjE3MzUwNjg3NTksImV4cCI6MTczNTE1NTE1OX0._vUsJRbIfbEGYFhKraX5kJOhzNJlDs0CqSz_-86UAJ4",
//                    email = "yashsharma0558@gmail.com",
//                    photo = "https://res.cloudinary.com/talkey/image/upload/v1/yashsharma0558%40gmail.com/uzsm1fvv1chktn14azlo",
//                    surveyResponse = SurveyResponse(
//                        selected_remedies = listOf(
//                            Remedy(name = "Gaming", time = null),
//                            Remedy(name = "Meditation", time = "30 minutes"),
//                            Remedy(name = "Reading", time = "1 hour"),
//                            Remedy(name = "Swimming", time = null)
//                        ), severity_tags = listOf("mild", "moderate", "dangerous")
//                    )
//                )
//            }
////            viewModel.fetchPosts(userDetails.token)
//        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val mockPosts = getMockPosts()

        // Initialize RecyclerView

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val postsAdapter = PostAdapter(mockPosts)
        binding.postsRecyclerView.adapter = postsAdapter

        

//        // Handle FAB Click
//        binding.fabAddPost.setOnClickListener {
//            showCreatePostDialog()
//        }
    }


    private fun getMockPosts(): List<PostDto> {
        val mockPosts = listOf(
            PostDto(
                imageUrl = R.drawable.img1,
                title = "First Post",
                caption = "Each step forward leads to a brighter tomorrow.",
                updatedAt = "2023-10-01",
                userName = "John Doe",
                userImageUrl = R.drawable.userimg1,
                id = "1",
                likes = 10,
                liked = true,
                commentsList = listOf(
                    CommentDto(
                        id = 1,
                        content = "Great post!",
                        userName = "Alice",
                        userImageUrl = "https://via.placeholder.com/40",
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        userId = 101
                    ),
                    CommentDto(
                        id = 2,
                        content = "Very inspiring!",
                        userName = "Bob",
                        userImageUrl = "https://via.placeholder.com/40",
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        userId = 102
                    )
                )
            ),
            PostDto(
                imageUrl = R.drawable.img2,
                title = "Second Post",
                caption = "Strength comes from overcoming your challenges.",
                updatedAt = "2023-10-02",
                userName = "Jane Doe",
                userImageUrl = R.drawable.userimg2,
                id = "2",
                likes = 25,
                liked = false,
                commentsList = listOf(
                    CommentDto(
                        id = 3,
                        content = "Amazing!",
                        userName = "Charlie",
                        userImageUrl = "https://via.placeholder.com/40",
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        userId = 103
                    )
                )
            ),
            PostDto(
                imageUrl = R.drawable.img3,
                title = "Third Post",
                caption = "The journey is tough, but so are you. Keep going!",
                updatedAt = "2023-10-03",
                userName = "Mark Smith",
                userImageUrl = R.drawable.userimg3,
                id = "3",
                likes = 42,
                liked = true,
                commentsList = emptyList() // No comments for this post
            )
        )

        return mockPosts

    }


//    private fun showCreatePostDialog() {
//        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_create_post, null)
//        val dialog = AlertDialog.Builder(requireContext())
//            .setView(dialogView)
//            .setCancelable(true)
//            .create()
//
//        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
//        val captionEditText = dialogView.findViewById<EditText>(R.id.editTextCaption)
//        val submitButton = dialogView.findViewById<Button>(R.id.buttonSubmit)
//
//        submitButton.setOnClickListener {
//            val title = titleEditText.text.toString()
//            val caption = captionEditText.text.toString()
//
//            if (title.isNotBlank() && caption.isNotBlank()) {
//                val createPostDto = CreatePostDto(
//                    image = stringToMultipartBody("https://res.cloudinary.com/talkey/image/upload/v1/yashsharma0558%40gmail.com/uzsm1fvv1chktn14azlo"), // Replace with actual image upload logic
//                    title = title,
//                    caption = caption
//                )
//                viewModel.createPost(createPostDto) // Call ViewModel to create a post
//                dialog.dismiss()
//            } else {
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        dialog.show()
//    }
//    fun stringToMultipartBody(
//        string: String,
//        partName: String = "file",
//        fileName: String = "file.txt"
//    ): MultipartBody.Part {
//        val stringBody = string.toRequestBody("text/plain".toMediaTypeOrNull())
//        val file = File.createTempFile(fileName, null)
//        file.writeText(string)
//
//        val requestBody = MultipartBody.Part.createFormData(
//            "$partName; filename=\"$fileName\"",
//            fileName,
//            file.asRequestBody("text/plain".toMediaTypeOrNull())
//        )
//
//        return requestBody
//    }

}