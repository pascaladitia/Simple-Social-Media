package com.pascal.udacodingpagging3.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pascal.udacodingpagging3.model.*
import com.pascal.udacodingpagging3.repo.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

class ViewModelMain : ViewModel() {

    var repo = Repository()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()
    var isEmpty = MutableLiveData<Boolean>()

    var responsePost: Flow<PagingData<ResponseListPost>>? = null
    var detailPost = MutableLiveData<ResponseListPost>()
    var responseComment: Flow<PagingData<ResponseCommentId>>? = null

    var responseTodo: Flow<PagingData<ResponseListTodo>>? = null

    var responseUser = MutableLiveData<List<ResponseListUser>>()
    var responseAlbum: Flow<PagingData<ResponseListAlbumUserId>>? = null
    var responsePhoto: Flow<PagingData<ResponsePhotoAlbumId>>? = null

    //post
    fun setPost() {
        responsePost = repo.getPost().cachedIn(viewModelScope)
    }
    fun getPost() = responsePost

    fun detailPostView(id: Int) {
        isLoading.value = true

        repo.detailPost(id, {
            isLoading.value = false
            detailPost.value = it
        },{
            isLoading.value = false
            isError.value = it
        })
    }

    fun addPostView(title: String, body: String, id: Int) {
        isLoading.value = true

        if (title.isNotEmpty() && body.isNotEmpty()) {
            isLoading.value = true
            repo.addPost(title, body, id, {
                isLoading.value = false
                detailPost.value = it
            },{
                isLoading.value = false
                isError.value = it
            })
        } else {
            isLoading.value = false
            isEmpty.value = true
        }
    }

    fun updatePostView(idPost: Int, title: String, body: String, id: Int) {
        isLoading.value = true

        if (title.isNotEmpty() && body.isNotEmpty()) {
            isLoading.value = true
            repo.updatePost(idPost, title, body, id, {
                isLoading.value = false
                detailPost.value = it
            },{
                isLoading.value = false
                isError.value = it
            })
        } else {
            isLoading.value = false
            isEmpty.value = true
        }
    }

    fun setCommentPost(id: Int) {
        responseComment = repo.commentPost(id).cachedIn(viewModelScope)
    }
    fun getCommentPost() = responseComment



    //todos
    fun setTodo() {
        responseTodo = repo.getTodo().cachedIn(viewModelScope)
    }
    fun getTodo() = responseTodo



    //user
    fun getUser() {
        isLoading.value = true

        repo.getUser( {
            isLoading.value = false
            responseUser.value = it
        },{
            isLoading.value = false
            isError.value = it
        })
    }

    fun setPostUserId(id: Int) {
        responsePost = repo.postUserId(id).cachedIn(viewModelScope)
    }
    fun getPostUserId() = responsePost

    fun setAlbumUserId(id: Int) {
        responseAlbum = repo.albumUserId(id).cachedIn(viewModelScope)
    }
    fun getAlbumUserId() = responseAlbum

    fun setTodoUserId(id: Int) {
        responseTodo = repo.todoUserId(id).cachedIn(viewModelScope)
    }
    fun getTodoUserId() = responseTodo

    fun setPhotoAlbumId(id: Int) {
        responsePhoto = repo.photoAlbumId(id).cachedIn(viewModelScope)
    }
    fun getPhotoAlbumId() = responsePhoto
}