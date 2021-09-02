package com.jibee.gym.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jibee.gym.R
import kotlinx.android.synthetic.main.icon_item.view.*

class IconPAdapter(
    private var clickListener: OnItemClickedListener,
    private val context: Context
) :
    PagingDataAdapter<Int, IconPAdapter.IconViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it, clickListener)
        }
    }

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImage: ImageButton = itemView.icon

        fun bindPost(icon: Int, action: OnItemClickedListener) {
            with(icon) {

                //set icon image
                iconImage.setImageResource(icon)

                //implement click function
                itemView.setOnClickListener {
                    action.onItemCLicked(this)
                }
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
        fun onItemCLicked(icon: Int)
    }

}