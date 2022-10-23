package self.nesl.newshub.ui.thread

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.hub_server.data.news.News
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
            if (url.isNotBlank()) {
                readBoardName(url)
            }
            url
        }.combine(_rePostId) { url, rePostId ->
            Pair(url, rePostId)
        }
        .flatMapLatest { (url, rePostId) ->
            if (url.isNotBlank() && rePostId.isNotBlank()) {
                postInteractor.getAll(url, rePostId)
            } else if (url.isNotBlank()) {
                postInteractor.getAll(url)
            } else {
                flowOf()
            }
        }
        .onEach {
            _loading.update { false }
        }
        .catch { Log.e("ThreadViewModel", it.stackTraceToString()) }

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