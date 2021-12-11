package mobiles.projet.arrosage
import  androidx.room.TypeConverter
import java.util.*


class Converters {
// source : documentation officielle de android

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }






}