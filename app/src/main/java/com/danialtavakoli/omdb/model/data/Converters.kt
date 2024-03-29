import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromRatingsList(ratings: List<Rating>): String {
        return Gson().toJson(ratings)
    }

    @TypeConverter
    fun toRatingsList(json: String): List<Rating> {
        val type = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(json, type)
    }
}
