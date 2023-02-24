package codes.bruno.raki.core.domain.model

data class Account(
    val id: String,
    val username: String,
    val acct: String,
    val url: String,
    val displayName: String,
    val note: String,
    val avatar: String,
    val header: String,
    val locked: Boolean,
    val fields: List<Field>,
    val bot: Boolean,
    val createdAt: String,
    val statusesCount: Int,
    val followersCount: Int,
    val followingCount: Int,
)

data class Field(
    val name: String,
    val value: String,
    val verifiedAt: String?,
)