package self.nesl.hub_server.data

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHeadDao

@Database(
    entities = [
        KomicaNewsHead::class,
    ],
    version = 1
)

@TypeConverters(
    AppDatabase.ParagraphListConverter::class,
    AppDatabase.HostConverter::class,
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private val gson = Gson()
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

    object HostConverter {
        @TypeConverter
        @JvmStatic
        fun valueToRoom(value: Host?): String {
            return value?.let { gson.toJson(it) } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun roomToValue(json: String): Host? {
            return json.takeIf { it.isNotBlank() }?.let { jsonString: String ->
                val type = object : TypeToken<Host>() {}.type
                gson.fromJson(jsonString, type)
            }
        }
    }

    abstract fun komicaNewsHeadDao(): KomicaNewsHeadDao
}