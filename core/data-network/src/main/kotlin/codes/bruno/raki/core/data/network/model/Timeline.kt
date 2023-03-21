package codes.bruno.raki.core.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Timeline(
    val statuses: List<Status>,
    val prevKey: String?,
    val nextKey: String?,
)