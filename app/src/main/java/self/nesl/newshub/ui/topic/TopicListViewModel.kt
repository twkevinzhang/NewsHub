package self.nesl.newshub.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.topic.Topic
import self.nesl.newshub.interactor.*
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicInteractor: TopicInteractor,
) : ViewModel() {
    val topicList = topicInteractor.getAll()
}