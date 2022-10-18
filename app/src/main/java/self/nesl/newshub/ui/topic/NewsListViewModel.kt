package self.nesl.newshub.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.board.Board
import self.nesl.newshub.interactor.NewsInteractor
import self.nesl.newshub.interactor.BoardInteractor
import self.nesl.newshub.interactor.TopicInteractor
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor,
    private val topicInteractor: TopicInteractor,
    private val boardInteractor: BoardInteractor,
) : ViewModel() {
    companion object {
        const val defaultTopicId = "Square"
    }

    private val _topicId = MutableStateFlow(defaultTopicId)
    private val _allBoards = MutableStateFlow(emptyList<Board>())
    private val _enableBoards = MutableStateFlow(emptyList<Board>())

    val topic = _topicId.mapLatest {
        topicInteractor.get(it).apply {
            val boards = boardInteractor.getAll(it)
            _allBoards.update { boards }
            _enableBoards.update { boards }
        }
    }

    val allBoards = _allBoards.asStateFlow()
    val enableBoards = _enableBoards.asStateFlow()

    val pagingNews = _enableBoards.map {
        newsInteractor.getAll(it.toSet())
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
            newsInteractor.clearAll(_enableBoards.value.toSet())
        }
    }

    suspend fun readBoardName(boardUrl: String): String {
        return boardInteractor.get(boardUrl).name
    }
}