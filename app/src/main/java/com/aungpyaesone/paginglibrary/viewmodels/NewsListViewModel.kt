package com.aungpyaesone.paginglibrary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aungpyaesone.paginglibrary.data.models.BaseModel
import com.aungpyaesone.paginglibrary.data.models.NewsDataSource
import com.aungpyaesone.paginglibrary.data.models.NewsDataSourceFactory
import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.aungpyaesone.paginglibrary.networks.NetworkService
import com.aungpyaesone.paginglibrary.utils.State
import io.reactivex.disposables.CompositeDisposable

class NewsListViewModel : ViewModel() {
    private val networkService = NetworkService.getService()
    var newList : LiveData<PagedList<NewsVO>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory : NewsDataSourceFactory

    init {
        newsDataSourceFactory = NewsDataSourceFactory(compositeDisposable,networkService)
        val config  = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize*2)
            .setEnablePlaceholders(false)
            .build()

        newList = LivePagedListBuilder<Int,NewsVO>(newsDataSourceFactory,config).build()
    }

    fun getState():LiveData<State> = Transformations.switchMap<NewsDataSource,State>(
        newsDataSourceFactory.newsDataSourceLiveData,NewsDataSource::state)

    fun retry(){
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean{
        return newList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}