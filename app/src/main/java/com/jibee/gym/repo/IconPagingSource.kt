package com.jibee.gym.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jibee.gym.R

class IconPagingSource : PagingSource<Int, Int>() {
    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, Int>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Int> {
        return try {

            //get icons list
            val iconsList = getIconList()

            LoadResult.Page(
                iconsList,
                0,
                0
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    public fun getIconList() = listOf(
        R.drawable.ic_aerobics,
        R.drawable.ic_box,
        R.drawable.ic_children_selection,
        R.drawable.ic_dances,
        R.drawable.ic_gym,
        R.drawable.ic_run,
        R.drawable.ic_swimming,
        R.drawable.ic_wrestling,
        R.drawable.ic_yoga
    )

}