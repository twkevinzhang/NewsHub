package self.nesl.hub_server.data

import android.util.Log
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHeadDao
import java.lang.reflect.Type

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
                JSONArray(jsonString).map {
                    when (it.getString("type")) {
                        ParagraphType.QUOTE.toString() -> Quote(
                            content = it.getString("content")
                        )
                        ParagraphType.REPLY_TO.toString() -> ReplyTo(
                            content = it.getString("content")
                        )
                        ParagraphType.TEXT.toString() -> Text(
                            content = it.getString("content")
                        )
                        ParagraphType.IMAGE.toString() -> ImageInfo(
                            thumb = it.getString("thumb"),
                            raw = it.getString("raw"),
                        )
                        ParagraphType.LINK.toString() -> Link(
                            content = it.getString("content")
                        )
                        else -> throw IllegalArgumentException()
                    }
                }
            } ?: emptyList()
        }

        private fun <O> JSONArray.map(callback: (JSONObject) -> O): List<O> {
            val list = mutableListOf<O>()
            for (i in 0 until this.length()) {
                list.add(callback(this.getJSONObject(i)))
            }
            return list
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