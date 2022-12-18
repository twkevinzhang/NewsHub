package self.nesl.newshub.ui.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import self.nesl.hub_server.data.Paragraph
import self.nesl.newshub.interactor.CommentInteractor
import self.nesl.newshub.model.toThumbnail
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val commentInteractor: CommentInteractor,
) : ViewModel() {
    private val _commentsUrl = MutableStateFlow("")

    val pagingComments = _commentsUrl
        .flatMapLatest { threadUrl ->
            if (threadUrl.isNotBlank()) {
                commentInteractor.getAll(threadUrl)
            } else {
                emptyFlow()
            }
        }
        .catch { Log.e("CommentListViewModel", it.stackTraceToString()) }
        .cachedIn(viewModelScope)

    val gallery = pagingComments.map { _pagingComments ->
        _pagingComments.flatMap { p ->
            p.content.mapNotNull {
                when (it) {
                    is Paragraph.ImageInfo -> it.toThumbnail()
                    is Paragraph.VideoInfo -> it.toThumbnail()
                    else -> null

               }
            }
        }
    }

    fun thread(commentsUrl: String) {
        this._commentsUrl.update { commentsUrl }
    }
}