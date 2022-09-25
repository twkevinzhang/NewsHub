package self.nesl.newshub.ui.news_thread

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.newshub.interactor.GetNewsThread
import javax.inject.Inject

@HiltViewModel
class NewsThreadViewModel @Inject constructor(
    private val getNewsThread: GetNewsThread,
) : ViewModel() {
    private val _newsThreadUrl = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val newsThread = _newsThreadUrl
        .mapLatest {
            _loading.update { true }
            getNewsThread.invoke(it)
        }
        .onEach {
            _loading.update { false }
        }
        .catch { Log.e("NewsThreadViewModel", it.stackTraceToString()) }

    fun newsThreadUrl(url: String) {
        this._newsThreadUrl.update { url }
    }

    fun refresh() {

    }
}