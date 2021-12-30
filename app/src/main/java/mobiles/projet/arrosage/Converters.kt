package mobiles.projet.arrosage
import  androidx.room.TypeConverter
import java.util.*


class Converters {

    @TypeConverter
    fun TimestampToCalendar(timeStamp: Long?): Calendar? {
        var calendar : Calendar = Calendar.getInstance();
        if(timeStamp != null)
            calendar.setTimeInMillis(timeStamp)
        return calendar
    }

    @TypeConverter
    fun CalendarToTimestamp(calendar: Calendar?): Long? {
        return calendar?.getTimeInMillis()
    }






}