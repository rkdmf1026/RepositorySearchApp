package com.example.repositorysearchapp.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repositorysearchapp.main.GitHubAdapter
import com.example.repositorysearchapp.main.MainViewModel

@BindingAdapter("EndlessScroll")
fun RecyclerView.setEndlessScroll(
    viewModel: MainViewModel
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val itemCount = recyclerView.adapter?.itemCount ?: return
            val itemType = recyclerView.adapter?.getItemViewType(itemCount - 1)

            if (lastItemPosition == itemCount - 1 && itemType == GitHubAdapter.TYPE_LOADING) {
                (recyclerView.adapter as GitHubAdapter).deleteLastItem()
                viewModel.searchItem()
            }
        }
    })
}
