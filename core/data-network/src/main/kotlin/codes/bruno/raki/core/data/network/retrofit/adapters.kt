package codes.bruno.raki.core.data.network.retrofit

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object OffsetDateTimeAdapter {
    private val FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @ToJson
    fun toJson(value: OffsetDateTime?) = value?.format(FORMAT)

    @FromJson
    fun fromJson(value: String?) = value?.let { OffsetDateTime.parse(it, FORMAT) }
}