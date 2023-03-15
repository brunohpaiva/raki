package codes.bruno.raki.core.data.database

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object OffsetDateTimeTypeConverter {
    private val FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toString(value: OffsetDateTime?) = value?.format(FORMAT)

    @TypeConverter
    fun toOffsetDateTime(value: String?) = value?.let { OffsetDateTime.parse(it, FORMAT) }
}