package com.pascal.udacodingpagging3.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.udacodingpagging3.model.ResponseCommentId
import com.pascal.udacodingpagging3.network.ApiService
import java.lang.Exception

class CommentDataSource(val api: ApiService, val id: Int) : PagingSource<Int, ResponseCommentId>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseCommentId>): Int? {

        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseCommentId> {

        return try {
            val start = params.key ?: 0

            val response = api.commentPost(id, start, params.loadSize)

            LoadResult.Page(response,
                prevKey = if (start == 0) null else start - 1,
                nextKey = if (start == 100) null else start + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}