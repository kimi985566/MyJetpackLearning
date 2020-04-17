package cn.yangchengyu.myjetpacklearning.ui.home.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.myjetpacklearning.ui.home.repository.HomeFeedResult
import cn.yangchengyu.myjetpacklearning.ui.home.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    //feedType
    private val feedTypeLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<HomeFeedResult> = feedTypeLiveData.switchMap {
        repository.refresh(it)
    }

    val repos: LiveData<PagedList<Feed>> = Transformations.switchMap(repoResult) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it.networkErrors }

    /**
     * 刷新feedType
     * */
    fun refreshFeedType(feedType: String) {
        feedTypeLiveData.postValue(feedType)
    }
}