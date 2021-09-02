package com.jibee.gym.adapter

import android.content.Context
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
    private val context: Context
) :
    PagingDataAdapter<Gym, GymPAdapter.GymViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gym_item, parent, false)
        return GymViewHolder(view)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it, clickListener)
        }
    }

    class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val _title: TextView = itemView.title
        private val _price: TextView = itemView.price
        private val _rating: TextView = itemView.rating
        private val _date: TextView = itemView.date
        private val _gymImage: ImageView = itemView.gym_image
        private val _favourite: ImageButton = itemView.favourite

        fun bindPost(gym: Gym, clickListener: OnItemClickedListener) {
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

                //check if its favourite or not
                if (favorite)
                    _favourite.setImageResource(R.drawable.ic_favorite_selected)
                else
                    _favourite.setImageResource(R.drawable.ic_favorite)

                //favourite button onClick
                _favourite.setOnClickListener {
                    if (favorite)
                        _favourite.setImageResource(R.drawable.ic_favorite)
                    else
                        _favourite.setImageResource(R.drawable.ic_favorite_selected)
                    favorite = !favorite
                }


                //implement click function
                itemView.setOnClickListener {
                    clickListener.onItemCLicked(gym)
                }
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemCLicked(gym: Gym)
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