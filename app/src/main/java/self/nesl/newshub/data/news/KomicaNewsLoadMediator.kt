package self.nesl.newshub.data.news

import android.util.Log
import androidx.paging.LoadType
import androidx.room.withTransaction
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.KPost
import self.nesl.newshub.data.AppDatabase
import self.nesl.newshub.data.toParagraph
import self.nesl.newshub.di.TransactionProvider
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject


interface KomicaNewsLoadMediator {
    suspend fun getEndOfPaginationReachedAfterLoad(loadType: LoadType, page: Int, topicNavItems: TopicNavItems): Boolean
}
