package dev.zlong.newshub.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import dev.zlong.hub_server.data.topic.Topic
import dev.zlong.newshub.interactor.*
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicInteractor: TopicInteractor,
) : ViewModel() {
    val topicList = topicInteractor.getAll()
}