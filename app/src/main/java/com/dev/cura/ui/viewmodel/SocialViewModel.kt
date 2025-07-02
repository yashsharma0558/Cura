package com.dev.cura.ui.viewmodel

import androidx.lifecycle.*
import com.dev.cura.data.model.CommentDto
import com.dev.cura.data.model.CreatePostDto
import com.dev.cura.data.model.PostDto
import com.dev.cura.data.repository.SocialRepository
import com.dev.cura.domain.model.UserDetails
import kotlinx.coroutines.launch

class SocialViewModel(private val repository: SocialRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<PostDto>>()
    val posts: LiveData<List<PostDto>> get() = _posts

    private val _comments = MutableLiveData<List<CommentDto>>()
    val comments: LiveData<List<CommentDto>> get() = _comments

    private val _newPost = MutableLiveData<PostDto>()
    val newPost: LiveData<PostDto> get() = _newPost

    private val _newComment = MutableLiveData<CommentDto>()
    val newComment: LiveData<CommentDto> get() = _newComment

    private val _userData = MutableLiveData<UserDetails>()
    val userData: LiveData<UserDetails> = _userData

    fun setUserData(data: UserDetails) {
        _userData.value = data
    }

    // Fetch all posts
    fun fetchPosts(token: String) {
        viewModelScope.launch {
            _posts.value = repository.getAllPosts(token)
        }
    }

    // Create a post
    fun createPost(createPostDto: CreatePostDto) {
        viewModelScope.launch {
            _newPost.value = repository.createPost(createPostDto)
        }
    }

    // Fetch comments for a post
    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _comments.value = repository.getComments(postId)
        }
    }

    // Create a comment
    fun createComment(postId: Int, content: String) {
        viewModelScope.launch {
            _newComment.value = repository.createComment(postId, content)
        }
    }
}
