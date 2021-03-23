package com.pascal.udacodingpagging3.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.udacodingpagging3.model.ResponseCommentId
import com.pascal.udacodingpagging3.model.ResponseListPost
import com.pascal.udacodingpagging3.model.ResponseListPostUserId
import com.pascal.udacodingpagging3.network.ApiService
import java.lang.Exception

class PostUserIdDataSource(val api: ApiService, val id: Int) : PagingSource<Int, ResponseListPost>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseListPost>): Int? {

        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseListPost> {

        return try {
            val start = params.key ?: 0

            val response = api.getPostuserId(id, start, params.loadSize)

            LoadResult.Page(response,
                prevKey = if (start == 0) null else start - 1,
                nextKey = if (start == 100) null else start + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}