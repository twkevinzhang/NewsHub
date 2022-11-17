package self.nesl.newshub.ui.thread

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.news.News
import self.nesl.newshub.applyIf
import self.nesl.newshub.interactor.BoardInteractor
import self.nesl.newshub.interactor.NewsInteractor
import self.nesl.newshub.interactor.PostInteractor
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val postInteractor: PostInteractor,
    private val boardInteractor: BoardInteractor,
) : ViewModel() {
    private val _threadUrl = MutableStateFlow("")
    private val _boardName = MutableStateFlow("")
    private val _rePostId = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    val boardName = _boardName.asStateFlow()

    val pagingPosts = _threadUrl
        .mapLatest { url ->
            _loading.update { true }
            url.applyIf({ isNotBlank() }) {
                readBoardName(url)
            }
        }
        .combineTransform(_rePostId) { url, rePostId ->
            if (url.isNotBlank() && rePostId.isNotBlank()) {
                postInteractor.getAll(url, rePostId)
            } else if (url.isNotBlank()) {
                postInteractor.getAll(url)
            } else {
                emptyFlow()
            }.collect(this)
        }
        .onEach { _loading.update { false } }
        .catch { Log.e("ThreadViewModel", it.stackTraceToString()) }
        .cachedIn(viewModelScope)

    fun threadUrl(url: String) {
        this._threadUrl.update { url }
    }

    fun rePostId(rePostId: String) {
        this._rePostId.update { rePostId }
    }

    fun refresh() {

    }

    private suspend fun readBoardName(url: String) {
        if (url.isBlank()) return
        val board = boardInteractor.get(url)
        _boardName.update { board.name }
    }
}