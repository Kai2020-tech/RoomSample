package com.example.roomsample.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsample.R
import com.example.roomsample.room.Person
import kotlinx.android.synthetic.main.person_item.view.*

class RvAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RvAdapter.myHolder>() {

    private val list = mutableListOf<Person>()

    inner class myHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val rvName: TextView = itemView.tv_name
        val rvAddress: TextView = itemView.tv_address
        val rvPhone: TextView = itemView.tv_phone

        //      偵測點擊的item,要先放在viewholder class初始化
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(list[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(person: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val createView = LayoutInflater.from(parent.context).inflate(
            R.layout.person_item, parent, false
        )
        return myHolder(createView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        val currentItem = list[position]
        holder.rvName.text = currentItem.name
        holder.rvAddress.text = currentItem.address
        holder.rvPhone.text = currentItem.phone
    }

    fun update(updateList: List<Person>) {
        list.clear()
        list.addAll(updateList)
        notifyDataSetChanged()
    }
}