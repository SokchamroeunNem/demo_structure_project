package com.example.emailverificationauthenticationviaapi.presentation.blogscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailverificationauthenticationviaapi.domain.blog.entity.BlogEntity
import com.example.emailverificationauthenticationviaapi.domain.blog.use_case.GetAllMyBlogsUseCase
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val getAllMyBlogsUseCase: GetAllMyBlogsUseCase
) : ViewModel() {

    var stateActivity by mutableStateOf(BlogComposableState())
        private set

    private val _blogs = mutableStateOf<List<BlogEntity>>(emptyList())
    val blogs: State<List<BlogEntity>> = _blogs

    init {
        fetchAllMyBlogs()
    }

    private fun fetchAllMyBlogs() {
        viewModelScope.launch {
            getAllMyBlogsUseCase.invoke().onStart {
                setLoading()
                Timber.tag("BlogViewModel").d("Loading started")
            }.catch { exception ->
                hideLoading()
                showToast(exception.message.toString())
                Timber.tag("BlogViewModel").e(exception, "Error fetching blogs: ${exception.message}")
            }.collect { result ->
                hideLoading()
                when (result) {
                    is BaseResult.Success -> {
                        _blogs.value = result.data
                        Timber.tag("BlogViewModel").d("Success: ${result.data}")
                    }

                    is BaseResult.Error -> {
                        showToast(result.rawResponse.code.toString())
                        Timber.tag("BlogViewModel").e("Error xxxxxxxxxxxxx: ${result.rawResponse.code}")
                    }
                }
            }
        }
    }

    private fun setLoading() {
        stateActivity = stateActivity.copy(isLoading = true)
    }

    private fun hideLoading() {
        stateActivity = stateActivity.copy(isLoading = false)
    }

    private fun showToast(message: String) {
        stateActivity = stateActivity.copy(message = message)
    }
}

data class BlogComposableState(
    var isLoading: Boolean = false, var message: String? = null
)
