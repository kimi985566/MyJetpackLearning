package cn.yangchengyu.myjetpacklearning.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import cn.yangchengyu.libcommon.model.Feed

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    //feedType
    private val feedTypeLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(feedTypeLiveData) {
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