package com.example.repositorysearchapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.repositorysearchapp.databinding.ItemRepositotyBinding
import com.example.repositorysearchapp.network.dto.Item

class GitHubAdapter() : ListAdapter<Item, GitHubAdapter.GitHubViewHolder>(DIFF_CALLBACK) {
    private val itemList = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositotyBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return GitHubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItemList(newItemList: List<Item>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }

    class GitHubViewHolder(private val binding: ItemRepositotyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            Glide.with(binding.ivAvatar)
                .load(item.owner.avatar_url)
                .into(binding.ivAvatar)
        }
    }

    companion object {
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
