package com.jibee.gym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jibee.gym.R
import com.jibee.gym.model.PopularClases

class PopularAdapter(
    private val itemList: List<PopularClases>,
    private var clickListener: OnItemClickedListener,
    private val favouriteList: ArrayList<String>
) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.popular_class_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        val popularItem = itemList[position]
        //set click listener
        holder.initialize(popularItem, clickListener, holder.adapterPosition)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val location: TextView = itemView.findViewById(R.id.location)
        val time: TextView = itemView.findViewById(R.id.time)
        val popularImage: ImageView = itemView.findViewById(R.id.popular_image)
        val favourite: ImageButton = itemView.findViewById(R.id.favourite)

        var isFavourite = false

        //init item click listener
        fun initialize(popularClases: PopularClases, action: OnItemClickedListener, position: Int) {

            title.text = popularClases.title
            price.append(popularClases.price.toString())
            location.text = popularClases.location
            time.text = popularClases.time

            when (popularClases.title) {
                "Fitness Class" -> popularImage.setImageResource(R.drawable.fitness_class)
                "Fitness with some friends" -> popularImage.setImageResource(R.drawable.fitness_with_some_friends)
                "Yoga Class" -> popularImage.setImageResource(R.drawable.yoga_class)
                "Crossfit Class" -> popularImage.setImageResource(R.drawable.crossfit_class)
            }

            //check if its favourite or not
            if (popularClases.title in favouriteList) {
                favourite.setImageResource(R.drawable.ic_favorite_selected)
                isFavourite = true
            }


            //favourite button onClick
            favourite.setOnClickListener {
                if (isFavourite)
                    favourite.setImageResource(R.drawable.ic_favorite)
                else
                    favourite.setImageResource(R.drawable.ic_favorite_selected)

                isFavourite = !isFavourite
                action.onItemCLicked(popularClases, isFavourite)
            }

            //implement click function
            itemView.setOnClickListener {

            }

        }

    }

    interface OnItemClickedListener {
        fun onItemCLicked(popularClases: PopularClases, isFavourite: Boolean)
    }
}