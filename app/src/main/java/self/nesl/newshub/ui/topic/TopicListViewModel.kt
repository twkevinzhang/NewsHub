package self.nesl.newshub.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.post.Board
import self.nesl.newshub.interactor.*
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicInteractor: TopicInteractor,
) : ViewModel() {
    private val _topicList = MutableStateFlow(emptyList<Topic>())

    val topicList = _topicList.asStateFlow()

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        _topicList.update { topicInteractor.getAll() }
    }
}