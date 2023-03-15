package codes.bruno.raki.core.data.network.model

data class Timeline(
    val statuses: List<Status>,
    val prevKey: String?,
    val nextKey: String?,
)