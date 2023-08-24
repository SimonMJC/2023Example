package com.example.wit.base.view.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


abstract class BaseAdapter<T, VH : ViewHolder>() : RecyclerView.Adapter<VH>() {
    private lateinit var context: Context
    private var itemList: MutableList<T>? = null
    private var onItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : this() {
        this.context = context
    }

    constructor(itemList: MutableList<T>) : this() {
        this.itemList = itemList
    }

    constructor(context: Context, itemList: MutableList<T>) : this() {
        this.context = context
        this.itemList = itemList
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onItemLongClick(holder.itemView, position)
            false
        }
    }

    override fun getItemCount() = itemList?.size ?: 0

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun removeItem(position: Int) {
        this.itemList?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun MutableList<T>.update() {
        this@BaseAdapter.itemList?.clear()
        this.addList()
    }

    fun clearItem() {
        this.itemList?.clear()
        notifyDataSetChanged()
    }

    //    fun Pair<Int, Int>.moveItemFromList(): Boolean = this@BaseAdapter.itemList?.let {
    //        it.removeAt(this.first)
    //        it.add(this.second, it[this.first])
    //        notifyItemMoved(this.first, this.second)
    //        true
    //    } ?: false


    //    fun Int.removeFromList() {
    //        this@BaseAdapter.itemList?.removeAt(this)
    //        notifyItemRemoved(this)
    //    }

    fun Int.getItemOrNull(): T? = if (this < itemCount) itemList?.get(this) else null

    fun getListOrNull(): MutableList<T>? = itemList

    fun T.addItem(position: Int) {
        this@BaseAdapter.itemList?.add(position, this)
        notifyItemInserted(position)
    }

    fun List<T>.addList() {
        this@BaseAdapter.itemList?.addAll(this)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int) {}
        fun onItemLongClick(view: View, position: Int) {}
    }
}