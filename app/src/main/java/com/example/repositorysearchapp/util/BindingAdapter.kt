package com.example.repositorysearchapp.util

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repositorysearchapp.main.GitHubAdapter
import com.example.repositorysearchapp.main.MainViewModel
import com.example.repositorysearchapp.network.repository.GitHubRepository

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
            val itemType = recyclerView.adapter?.getItemViewType(itemCount - 1) ?: return

            if (lastItemPosition == itemCount - 1 && itemType == GitHubAdapter.TYPE_LOADING) {
                (recyclerView.adapter as GitHubAdapter).deleteLastItem()
                viewModel.searchItem(GitHubRepository.PAGING)
            }
        }
    })
}

@BindingAdapter("viewModel", "context")
fun EditText.setSearchItem(
    viewModel: MainViewModel,
    context: Context
) {
    this.setOnEditorActionListener(object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchItem(0)
                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(this@setSearchItem.windowToken, 0)
                return true
            }
            return false
        }
    })
}
