package self.nesl.newshub.ui.topic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.newshub.data.news.Host
import self.nesl.newshub.data.news.News
import self.nesl.newshub.interactor.GetAllNews
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getAllNews: GetAllNews,
) : ViewModel() {
    companion object {
        val defaultTopic = TopicNavItems.Square
    }
    private val _topic = MutableStateFlow<TopicNavItems>(defaultTopic)
    private val _excludeHosts = MutableStateFlow(emptyList<Host>())
    val enableHosts = _excludeHosts.mapLatest {
        Host.values().toList().minus(it.toSet())
    }

    val newsfeed = _topic
        .flatMapLatest { getAllNews.invoke(it) }
        .cachedIn(viewModelScope)
        .distinctUntilChanged()
        .combine(_excludeHosts) {
            pagingNews, excludeHosts -> pagingNews.filter { news -> excludeHosts.contains(news.host) }
        }.catch {
            Log.e("TopicViewModel", it.stackTraceToString())
            PagingData.empty<News>()
        }

    fun topic(topic: TopicNavItems) {
        this._topic.update { topic }
    }

    fun disableHost(host: Host) {
        this._excludeHosts.update { it.plus(host).toSet().toList() }
    }

    fun enableHost(host: Host) {
        this._excludeHosts.update { it.minus(host).toSet().toList() }
    }
}