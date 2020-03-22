package com.aungpyaesone.paginglibrary.data.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.aungpyaesone.paginglibrary.networks.NetworkService
import com.aungpyaesone.paginglibrary.utils.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class NewsDataSource(private val networkService: NetworkService,
                     private val compositeDisposable: CompositeDisposable ) : PageKeyedDataSource<Int,NewsVO>() {

    var state:MutableLiveData<State> = MutableLiveData()
    private var retryCompletable:Completable ? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsVO>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getNews(1,params.requestedLoadSize)
                .subscribe(
                    {
                        updateState(State.DONE)
                        callback.onResult(it.news,null,2)
                    },{
                        updateState(State.ERROR)
                        setRetry(Action{ loadInitial(params,callback)})
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsVO>) {
       updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getNews(params.key,params.requestedLoadSize)
                .subscribe({
                    updateState(State.DONE)
                    callback.onResult(it.news,params.key+1)
                },{
                    updateState(State.ERROR)
                    setRetry(Action { loadAfter(params,callback) })
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsVO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun retry(){
        if(retryCompletable != null){
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }


}