package self.nesl.local_news_receiver.framework.data

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import self.nesl.komica_api.model.Paragraph

@Database(
    entities = [
        News::class,
    ],
    version = 1
)

@TypeConverters(
    AppDatabase.ParagraphListConverter::class,
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "app.db"

        private var instance: AppDatabase? = null
        private val gson = Gson()

        private fun create(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        fun getInstance(context: Context): AppDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    object ParagraphListConverter {
        @TypeConverter
        @JvmStatic
        fun valueToRoom(value: List<Paragraph>?): String {
            return value?.let { gson.toJson(it) } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun roomToValue(json: String): List<Paragraph> {
            return json.takeIf { it.isNotBlank() }?.let { jsonString: String ->
                val type = object : TypeToken<List<Paragraph>>() {}.type
                gson.fromJson(jsonString, type)
            } ?: emptyList()
        }
    }

    abstract fun newsDao(): NewsDao
}