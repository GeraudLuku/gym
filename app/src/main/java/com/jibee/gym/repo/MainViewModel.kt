package com.jibee.gym.repo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jibee.gym.model.Gym
import com.jibee.gym.model.Gyms
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val gymRepo = GymRepo(getApplication())
    private val iconRepo = IconRepo()

    fun fetchGyms(): Flow<PagingData<Gym>> {
        return gymRepo.fetchGyms().cachedIn(viewModelScope)
    }

    fun fetchIcons(): Flow<PagingData<Int>> {
        return iconRepo.fetchIcons().cachedIn(viewModelScope)
    }


    fun getData(): Gyms {
        val jsonFileString = getJsonDataFromAsset(getApplication(), "data.json")
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

}