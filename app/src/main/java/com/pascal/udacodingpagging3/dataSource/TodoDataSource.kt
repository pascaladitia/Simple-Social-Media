package com.pascal.udacodingpagging3.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.udacodingpagging3.model.ResponseListTodo
import com.pascal.udacodingpagging3.network.ApiService
import java.lang.Exception

class TodoDataSource(val api: ApiService) : PagingSource<Int, ResponseListTodo>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseListTodo>): Int? {

        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseListTodo> {

        return try {
            val start = params.key ?: 0

            val response = api.getTodo(start, params.loadSize)

            LoadResult.Page(response,
                prevKey = if (start == 0) null else start - 1,
                nextKey = if (start == 200) null else start + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}