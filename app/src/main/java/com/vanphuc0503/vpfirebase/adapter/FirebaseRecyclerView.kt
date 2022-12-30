package com.vanphuc0503.vpfirebase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.vanphuc0503.vpfirebase.BR

class FirebaseRecyclerView<T>(
    @LayoutRes private val layoutId: Int,
    private val listener: FirebaseRecyclerViewListener<T>
) : RecyclerView.Adapter<FirebaseRecyclerView.FirebaseViewHolder>() {

    private val data = mutableListOf<T>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<T>) {
        if (data.isNotEmpty()) {
            data.clear()
        }
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return FirebaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FirebaseViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, data[position])
            setVariable(BR.listener, listener)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = data.size

    class FirebaseViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}

interface FirebaseRecyclerViewListener<T> {
    fun onItemClick(item: T)
}