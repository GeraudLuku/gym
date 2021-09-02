package com.jibee.gym.feature

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
import com.jibee.gym.adapter.GymPAdapter
import com.jibee.gym.adapter.IconPAdapter
import com.jibee.gym.R
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

    private lateinit var mainViewModel: MainViewModel

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var gymPAdapter: GymPAdapter
    private lateinit var iconPAdapter: IconPAdapter

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

        //subscribe to the view model
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //init paging3 compact adapter
        gymPAdapter = GymPAdapter(this, requireContext())
        gym_recyclerview.adapter = gymPAdapter
        gym_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        gym_recyclerview.setHasFixedSize(true)

        //init paging3 compact adapter
        iconPAdapter = IconPAdapter(this, requireContext())
        icon_recyclerview.adapter = iconPAdapter
        icon_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        icon_recyclerview.setHasFixedSize(true)

        //init popular classes recyclerview
        popularAdapter =
            PopularAdapter(mainViewModel.getData().gyms[0].popularClasess, this, requireContext())
        popular_recyclerview.adapter = popularAdapter
        popular_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        popular_recyclerview.setHasFixedSize(true)

        fetchGyms()
        fetchIcons()

    }

    override fun onItemCLicked(gym: Gym) {
        Log.d("click", "${gym}")
    }

    override fun onItemCLicked(icon: Int) {
        Log.d("click", "${icon}")
    }

    override fun onItemCLicked(popularClases: PopularClases) {
        Log.d("click", popularClases.title)
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
}