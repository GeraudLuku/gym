package com.jibee.gym.repo

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jibee.gym.model.Gym
import kotlinx.coroutines.flow.Flow

class GymRepo(val application: Application) {

    fun fetchGyms(): Flow<PagingData<Gym>> {
        return Pager(
            PagingConfig(pageSize = 2, enablePlaceholders = false)
        ) {
            GymPagingSource(application)
        }.flow
    }
}