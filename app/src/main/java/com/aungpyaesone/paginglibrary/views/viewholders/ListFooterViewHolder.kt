package com.aungpyaesone.paginglibrary.views.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.paginglibrary.R
import com.aungpyaesone.paginglibrary.utils.State
import kotlinx.android.synthetic.main.activity_main.view.*

class ListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(status: State?){
        itemView.progress_bar.visibility = if(status == State.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object{
        fun create(retry:()->Unit, parent:ViewGroup): ListFooterViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer,parent,false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}