package com.example.demo.presentation.postscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.post.entity.Post
import com.example.demo.domain.post.use_case.GetPostsUseCase
import com.example.demo.util.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {

        viewModelScope.launch {

            getPostsUseCase().collect { result ->

                when (result) {

                    is BaseResult.Success -> {

                        _uiState.update {
                            it.copy(
                                posts = result.data, error = null
                            )
                        }
                    }

                    is BaseResult.Error -> {

                        _uiState.update {
                            it.copy(
                                error = result.rawResponse.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class PostUiState(
    val isLoading: Boolean = false, val posts: List<Post> = emptyList(), val error: String? = null
)
