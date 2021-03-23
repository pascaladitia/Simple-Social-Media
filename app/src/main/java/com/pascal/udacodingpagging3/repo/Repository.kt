package com.pascal.udacodingpagging3.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pascal.udacodingpagging3.dataSource.*
import com.pascal.udacodingpagging3.model.*
import com.pascal.udacodingpagging3.network.ModuleNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow

class Repository {

    val api = ModuleNetwork.getService()
    val api2 = ModuleNetwork.getService2()

    //post
    fun getPost(): Flow<PagingData<ResponseListPost>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            PostDataSource(api)
        }.flow
        return pager
    }

    fun detailPost(postId: Int, responHandler: (ResponseListPost) -> Unit, errorHandler: (Throwable) -> Unit) {
        api2.detailPost(postId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun deletePost(postId: Int, responHandler: (ResponseListPost) -> Unit, errorHandler: (Throwable) -> Unit) {
        api2.detailPost(postId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun addPost(title: String, body: String, userId: Int,
                responHandler: (ResponseListPost) -> Unit, errorHandler: (Throwable) -> Unit) {
        api2.addPost(title, body, userId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun updatePost(postId: Int, title: String, body: String, userId: Int,
                responHandler: (ResponseListPost) -> Unit, errorHandler: (Throwable) -> Unit) {
        api2.updatePost(postId, title, body, userId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun commentPost(id: Int): Flow<PagingData<ResponseCommentId>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            CommentDataSource(api, id)
        }.flow
        return pager
    }



    //todos
    fun getTodo(): Flow<PagingData<ResponseListTodo>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            TodoDataSource(api)
        }.flow
        return pager
    }



    //user
    fun getUser(responHandler: (List<ResponseListUser>) -> Unit, errorHandler: (Throwable) -> Unit) {
        api2.getUser().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun postUserId(id: Int): Flow<PagingData<ResponseListPost>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            PostUserIdDataSource(api, id)
        }.flow
        return pager
    }

    fun albumUserId(id: Int): Flow<PagingData<ResponseListAlbumUserId>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            AlbumUserIdDataSource(api, id)
        }.flow
        return pager
    }

    fun todoUserId(id: Int): Flow<PagingData<ResponseListTodo>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            TodoUserIdDataSource(api, id)
        }.flow
        return pager
    }

    fun photoAlbumId(id: Int): Flow<PagingData<ResponsePhotoAlbumId>>{
        val pager = Pager(PagingConfig(pageSize = 10)) {
            PhotoDataSource(api, id)
        }.flow
        return pager
    }
}