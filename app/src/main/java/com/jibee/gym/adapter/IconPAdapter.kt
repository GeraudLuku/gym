package com.jibee.gym.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jibee.gym.R
import kotlinx.android.synthetic.main.icon_item.view.*

class IconPAdapter(
    private var clickListener: OnItemClickedListener,
    private var selectedList: ArrayList<Int>
) :
    PagingDataAdapter<Int, IconPAdapter.IconViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it, clickListener, selectedList)
        }
    }

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImage: ImageView = itemView.icon
        var isSelected = false

        fun bindPost(icon: Int, action: OnItemClickedListener, selectedList: ArrayList<Int>) {
            //set icon image
            iconImage.setImageResource(icon)

            //check if an item was previously selected
            if (icon in selectedList) {
                //set selected to true and change icon drawables
                iconImage.setBackgroundResource(R.drawable.icon_selected_bg)
                ImageViewCompat.setImageTintList(
                    iconImage, ColorStateList.valueOf(
                        ContextCompat.getColor(iconImage.context, R.color.white)
                    )
                )
                isSelected = true
            }

            iconImage.setOnClickListener {
                //change drawable of the icon
                if (!isSelected) {
                    //if not selected change the background to selected
                    it.setBackgroundResource(R.drawable.icon_selected_bg)
                    ImageViewCompat.setImageTintList(
                        iconImage, ColorStateList.valueOf(
                            ContextCompat.getColor(it.context, R.color.white)
                        )
                    )
                    isSelected = !isSelected
                } else {
                    //if it was selected change the background to unselected
                    it.setBackgroundResource(R.drawable.icon_unselected_bg)
                    ImageViewCompat.setImageTintList(
                        iconImage, ColorStateList.valueOf(
                            ContextCompat.getColor(it.context, R.color.primary)
                        )
                    )
                    isSelected = !isSelected
                }

                action.onItemCLicked(icon, isSelected)
            }


            //implement click function
            itemView.setOnClickListener {


            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }


    }

    interface OnItemClickedListener {
        fun onItemCLicked(icon: Int, isSelected: Boolean)
    }

}