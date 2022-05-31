package com.example.repositorysearchapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.repositorysearchapp.databinding.ItemLoadingBinding
import com.example.repositorysearchapp.databinding.ItemRepositotyBinding
import com.example.repositorysearchapp.network.dto.Item

class GitHubAdapter() : ListAdapter<Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val itemList = mutableListOf<Item>()

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).full_name) {
            "" -> TYPE_LOADING
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRepositotyBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                GitHubViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GitHubViewHolder) {
            holder.bind(getItem(position))
        }
    }

    fun deleteLastItem() {
        val copyList = currentList.toMutableList()
        copyList.removeLast()
        submitList(copyList)
    }

    class GitHubViewHolder(private val binding: ItemRepositotyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.tvName.isSelected = true
            Glide.with(binding.ivAvatar)
                .load(item.owner.avatar_url)
                .into(binding.ivAvatar)
        }
    }

    class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }
        }
    }
}
