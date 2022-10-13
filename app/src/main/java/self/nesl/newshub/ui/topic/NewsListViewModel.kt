package self.nesl.newshub.ui.topic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.post.Board
import self.nesl.newshub.interactor.ClearAllNews
import self.nesl.newshub.interactor.GetAllBoard
import self.nesl.newshub.interactor.GetAllNews
import self.nesl.newshub.interactor.GetTopic
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getAllNews: GetAllNews,
    private val clearAllNews: ClearAllNews,
    private val getTopic: GetTopic,
    private val getAllBoard: GetAllBoard,
) : ViewModel() {
    companion object {
        const val defaultTopicId = "Square"
    }

    private val _topicId = MutableStateFlow(defaultTopicId)
    private val _allBoards = MutableStateFlow(emptyList<Board>())
    private val _enableBoards = MutableStateFlow(emptyList<Board>())

    val topic = _topicId.mapLatest {
        getTopic.invoke(it).apply {
            val boards = getAllBoard.invoke(it)
            _allBoards.update { boards }
            _enableBoards.update { boards }
        }
    }

    val allBoards = _allBoards.asStateFlow()
    val enableBoards = _enableBoards.asStateFlow()

    val pagingNews = _enableBoards.map {
        getAllNews.invoke(it.toSet())
    }
        .flatMapLatest { it }
        .cachedIn(viewModelScope)

    fun topicId(topicId: String) {
        this._topicId.update { topicId }
    }

    fun disableBoard(board: Board) {
        this._enableBoards.update { it.minus(board).toSet().toList() }
    }

    fun enableBoard(board: Board) {
        this._enableBoards.update { it.plus(board).toSet().toList() }
    }

    fun clearAllNews() {
        viewModelScope.launch {
            clearAllNews.invoke(_enableBoards.value.toSet())
        }
    }
}