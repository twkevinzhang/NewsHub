package self.nesl.newshub.ui.thread

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import self.nesl.newshub.interactor.BoardInteractor
import self.nesl.newshub.interactor.ThreadInteractor
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val threadInteractor: ThreadInteractor,
    private val boardInteractor: BoardInteractor,
) : ViewModel() {
    private val _threadUrl = MutableStateFlow("")
    private val _rePostId = MutableStateFlow("")

    val boardName = _threadUrl.mapLatest {
        if (it.isNotBlank()) {
            val board = boardInteractor.get(it)
            board.name
        } else {
            ""
        }
    }

    val pagingPosts = _threadUrl
        .combineTransform(_rePostId) { threadUrl, rePostId ->
            if (threadUrl.isNotBlank()) {
                threadInteractor.getAll(threadUrl, rePostId.takeIf { it.isNotBlank() })
            } else {
                emptyFlow()
            }.collect(this)
        }
        .catch { Log.e("ThreadViewModel", it.stackTraceToString()) }
        .cachedIn(viewModelScope)

    fun thread(threadUrl: String) {
        this._threadUrl.update { threadUrl }
    }

    fun rePostId(rePostId: String) {
        this._rePostId.update { rePostId }
    }

    fun clear() {
        viewModelScope.launch {
            threadInteractor.removeThread(_threadUrl.value)
        }
    }
}