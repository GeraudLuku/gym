package com.jibee.gym.feature

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jibee.gym.R
import com.jibee.gym.adapter.GymPAdapter
import com.jibee.gym.adapter.IconPAdapter
import com.jibee.gym.adapter.PopularAdapter
import com.jibee.gym.model.Gym
import com.jibee.gym.model.PopularClases
import com.jibee.gym.repo.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment(), PopularAdapter.OnItemClickedListener,
    GymPAdapter.OnItemClickedListener,
    IconPAdapter.OnItemClickedListener {

    val ICON_ID = "icon"
    val FAV_ID = "fav"

    private lateinit var mainViewModel: MainViewModel

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var gymPAdapter: GymPAdapter
    private lateinit var iconPAdapter: IconPAdapter

    private var selectedIcons: ArrayList<Int> = arrayListOf()
    private var selectedFav: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set toolbar
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

        //get saved items from shared preferences
        try {
            loadIconData()
            loadFavData()
        } catch (e: Exception) {
            Log.d("Error", e.localizedMessage!!)
        }


        //subscribe to the view model
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //init paging3 compact adapter
        gymPAdapter = GymPAdapter(this, selectedFav)
        gym_recyclerview.adapter = gymPAdapter
        gym_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        gym_recyclerview.setHasFixedSize(true)

        //init paging3 compact adapter
        iconPAdapter = IconPAdapter(this, selectedIcons)
        icon_recyclerview.adapter = iconPAdapter
        icon_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        icon_recyclerview.setHasFixedSize(true)

        //init popular classes recyclerview
        popularAdapter =
            PopularAdapter(mainViewModel.getData().gyms[0].popularClasess, this, selectedFav)
        popular_recyclerview.adapter = popularAdapter
        popular_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        popular_recyclerview.setHasFixedSize(true)

        fetchGyms()
        fetchIcons()

    }

    override fun onItemCLicked(gym: Gym, isFavourite: Boolean) {
        Log.d("click", selectedFav.toString())

        if (isFavourite) {
            selectedFav.add(gym.title)
            saveFavData()
        } else {
            selectedFav.remove(gym.title)
            saveFavData()
        }
    }

    override fun onItemCLicked(icon: Int, isSelected: Boolean) {
        Log.d("click", "${icon}")
        //check if the user has selected or unselected
        if (isSelected) {
            //add to selected
            selectedIcons.add(icon)
            saveIconData()
        } else {
            //remove from selected
            selectedIcons.remove(icon)
            saveIconData()
        }
    }

    override fun onItemCLicked(popularClases: PopularClases, isFavourite: Boolean) {
        Log.d("click", popularClases.title)
        if (isFavourite) {
            selectedFav.add(popularClases.title)
            saveFavData()
        } else {
            selectedFav.remove(popularClases.title)
            saveFavData()
        }
    }

    private fun fetchGyms() {
        lifecycleScope.launch {
            mainViewModel.fetchGyms().collectLatest { pagingData ->
                gymPAdapter.submitData(pagingData)
            }
        }
    }

    private fun fetchIcons() {
        lifecycleScope.launch {
            mainViewModel.fetchIcons().collectLatest { pagingData ->
                iconPAdapter.submitData(pagingData)
            }
        }
    }

    private fun saveIconData() {
        val sharedPreferences = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(selectedIcons)
        editor?.putString(ICON_ID, json)
        editor?.apply()
        Log.d("DATA", "icon saved")

    }

    private fun saveFavData() {
        val sharedPreferences = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(selectedFav)
        editor?.putString(FAV_ID, json)
        editor?.apply()
        Log.d("DATA", "icon saved")
    }

    private fun loadIconData() {
        val sharedPreferences = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val json = sharedPreferences?.getString(ICON_ID, null)
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        val gson = Gson()
        selectedIcons = gson.fromJson(json, type)
    }

    private fun loadFavData() {
        val sharedPreferences = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val json = sharedPreferences?.getString(FAV_ID, null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        val gson = Gson()
        selectedFav = gson.fromJson(json, type)
    }
}