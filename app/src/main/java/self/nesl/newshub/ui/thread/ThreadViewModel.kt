package self.nesl.newshub.ui.thread

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.newshub.interactor.BoardInteractor
import self.nesl.newshub.interactor.ThreadInteractor
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val threadInteractor: ThreadInteractor,
    private val boardInteractor: BoardInteractor,
) : ViewModel() {
    private val _threadUrl = MutableStateFlow("")
    private val _boardName = MutableStateFlow("")
    private val _rePostId = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    val boardName = _boardName.asStateFlow()

    val thread = _threadUrl.combine(_rePostId) { url, rePostId ->
        _loading.update { true }
        readBoardName(url)
        if (url.isNotBlank() && rePostId.isNotBlank()) {
            threadInteractor.get(url, rePostId)
        } else if (url.isNotBlank()) {
            threadInteractor.get(url)
        } else {
            null
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