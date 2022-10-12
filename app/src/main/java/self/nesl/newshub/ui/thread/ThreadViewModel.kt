package self.nesl.newshub.ui.thread

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.newshub.interactor.GetThread
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val getThread: GetThread,
) : ViewModel() {
    private val _threadUrl = MutableStateFlow("")
    private val _rePostId = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val thread = _threadUrl.combine(_rePostId) { url, rePostId ->
        _loading.update { true }
        if (url.isNotBlank() && rePostId.isNotBlank()) {
            getThread.invoke(url, rePostId)
        } else if (url.isNotBlank()) {
            getThread.invoke(url)
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
}