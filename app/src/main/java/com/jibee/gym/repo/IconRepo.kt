package com.jibee.gym.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class IconRepo {

    fun fetchIcons(): Flow<PagingData<Int>> {
        return Pager(
            PagingConfig(pageSize = 5, enablePlaceholders = false)
        ) {
            IconPagingSource()
        }.flow
    }
}