package self.nesl.newshub.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.post.Host
import self.nesl.newshub.interactor.ClearAllNews
import self.nesl.newshub.interactor.GetAllNews
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getAllNews: GetAllNews,
    private val clearAllNews: ClearAllNews,
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

    val pagingNews = _topic.combine(enableHosts) { it, it2 ->
        getAllNews.invoke(it, it2)
    }
        .flatMapLatest { it }
        .cachedIn(viewModelScope)

    fun topic(topic: TopicNavItems) {
        this._topic.update { topic }
    }

    fun disableHost(host: Host) {
        this._excludeHosts.update { it.plus(host).toSet().toList() }
    }

    fun enableHost(host: Host) {
        this._excludeHosts.update { it.minus(host).toSet().toList() }
    }

    fun clearAllNews() {
        viewModelScope.launch {
            clearAllNews.invoke(_topic.value)
        }
    }
}