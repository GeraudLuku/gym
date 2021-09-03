package com.jibee.gym.repo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jibee.gym.model.Gym
import com.jibee.gym.model.Gyms
import java.io.IOException

class GymPagingSource(val application: Application) : PagingSource<Int, Gym>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gym> {

        return try {

            //get list of all gyms from json
            val gymList = getData().gyms

            LoadResult.Page(
                gymList,
                null,  //so its finite on the left and scrolls infinite to the right
                0
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    fun getData(): Gyms {
        val jsonFileString = getJsonDataFromAsset(application, "data.json")
        //Log.i("data", jsonFileString!!)

        val gson = Gson()
        val gymsObject = object : TypeToken<Gyms>() {}.type

        val gyms: Gyms = gson.fromJson(jsonFileString, gymsObject)

        Log.d("data", gyms.toString())

        return gyms

    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, Gym>): Int? {
        return null
    }

}