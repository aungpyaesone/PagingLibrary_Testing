package com.aungpyaesone.paginglibrary.data.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.aungpyaesone.paginglibrary.networks.NetworkService
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
): DataSource.Factory<Int,NewsVO>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    override fun create(): DataSource<Int, NewsVO> {
        val newsDataSource = NewsDataSource(networkService,compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}