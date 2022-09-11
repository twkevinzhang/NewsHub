package self.nesl.newshub.ui.topic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.newshub.interactor.ClearAllNewsHead
import self.nesl.newshub.interactor.GetAllNewsHead
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getAllNewsHead: GetAllNewsHead,
    private val clearAllNewsHead: ClearAllNewsHead,
) : ViewModel() {
    companion object {
        val defaultTopic = TopicNavItems.Square
    }
    private val _topic = MutableStateFlow<TopicNavItems>(defaultTopic)
    private val _excludeHosts = MutableStateFlow(emptyList<Host>())

    val topic = _topic.asStateFlow()

    val enableHosts = _excludeHosts.mapLatest {
        Host.values().toList().minus(it.toSet())
    }

    val newsfeed = _topic.combine(enableHosts) { it, it2 ->
        getAllNewsHead.invoke(it, it2)
    }
        .flatMapLatest { it.mapLatest { it } }
        .cachedIn(viewModelScope)
        .distinctUntilChanged()
        .catch {
            Log.e("TopicViewModel", it.stackTraceToString())
            PagingData.empty<NewsHead>()
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

    fun clearAllNewsHead() {
        viewModelScope.launch {
            clearAllNewsHead.invoke(_topic.value)
        }
    }
}