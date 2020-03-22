package com.aungpyaesone.paginglibrary.views.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.paginglibrary.R
import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(news: NewsVO?){
        if(news != null){
            itemView.txt_news_name.text = news.title
            Glide.with(itemView.context)
                .load(news.image)
                .into(itemView.img_news_banner)
        }
    }

    companion object{
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
            return NewsViewHolder(view)
        }
    }
}