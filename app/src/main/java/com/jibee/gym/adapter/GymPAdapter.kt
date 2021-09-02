package com.jibee.gym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jibee.gym.R
import com.jibee.gym.model.Gym
import kotlinx.android.synthetic.main.gym_item.view.*


class GymPAdapter(
    private var clickListener: OnItemClickedListener,
    private val favouriteList: ArrayList<String>
) :
    PagingDataAdapter<Gym, GymPAdapter.GymViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gym_item, parent, false)
        return GymViewHolder(view)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it, clickListener, favouriteList)
        }
    }

    class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val _title: TextView = itemView.title
        private val _price: TextView = itemView.price
        private val _rating: TextView = itemView.rating
        private val _date: TextView = itemView.date
        private val _gymImage: ImageView = itemView.gym_image
        private val _favourite: ImageButton = itemView.favourite

        var isFavourite = false

        fun bindPost(
            gym: Gym,
            clickListener: OnItemClickedListener,
            favouriteList: ArrayList<String>
        ) {
            with(gym) {

                _title.text = title
                _price.text = "$${price}"
                _rating.text = rating.toString()
                _date.text = date

                //check gym type to assign image
                when (gym.title) {
                    "Gym Rebel" -> _gymImage.setImageResource(R.drawable.gym_rebel)
                    "Gym NonStop" -> _gymImage.setImageResource(R.drawable.gym_non_stop)
                }

                //check if its favourite or not,
                if (title in favouriteList) {
                    _favourite.setImageResource(R.drawable.ic_favorite_selected)
                    isFavourite = true
                }

                //favourite button onClick
                _favourite.setOnClickListener {
                    if (isFavourite) {
                        _favourite.setImageResource(R.drawable.ic_favorite)
                        isFavourite = !isFavourite
                    } else {
                        _favourite.setImageResource(R.drawable.ic_favorite_selected)
                        isFavourite = !isFavourite
                    }
                    clickListener.onItemCLicked(gym, isFavourite)
                }

            }
        }
    }

    interface OnItemClickedListener {
        fun onItemCLicked(gym: Gym, isFavourite: Boolean)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Gym>() {
        override fun areItemsTheSame(oldItem: Gym, newItem: Gym): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Gym, newItem: Gym): Boolean {
            return oldItem.title == newItem.title
        }

    }
}