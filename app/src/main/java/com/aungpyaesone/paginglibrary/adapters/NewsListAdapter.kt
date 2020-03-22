package com.aungpyaesone.paginglibrary.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.aungpyaesone.paginglibrary.utils.State
import com.aungpyaesone.paginglibrary.views.viewholders.ListFooterViewHolder
import com.aungpyaesone.paginglibrary.views.viewholders.NewsViewHolder

class NewsListAdapter(private val retry:()->Unit): PagedListAdapter<NewsVO,RecyclerView.ViewHolder>(NewsDiffCallback){


    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    companion object{
        val NewsDiffCallback = object : DiffUtil.ItemCallback<NewsVO>(){
            override fun areContentsTheSame(oldItem: NewsVO, newItem: NewsVO): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: NewsVO, newItem: NewsVO): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) NewsViewHolder.create(parent) else ListFooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == DATA_VIEW_TYPE){
            (holder as NewsViewHolder).bind(getItem(position))
        } else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}