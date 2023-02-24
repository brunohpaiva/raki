package codes.bruno.raki.core.data.network.model

data class Account(
    val id: String,
    val username: String,
    val acct: String,
    val url: String,
    val display_name: String,
    val note: String,
    val avatar: String,
    val avatar_static: String,
    val header: String,
    val header_static: String,
    val locked: Boolean,
    val fields: List<Field>,
    val emojis: List<CustomEmoji>,
    val bot: Boolean,
    val group: Boolean,
    val discoverable: Boolean?,
    val noindex: Boolean?,
    val moved: Boolean?,
    val suspended: Boolean?,
    val limited: Boolean?,
    val created_at: String,
    val last_status_at: String?,
    val statuses_count: Int,
    val followers_count: Int,
    val following_count: Int,
    // CredentialAccount mastodon entity extra fields
    val source: Source?,
    val role: Role?,
    // TODO: MutedAccount
)

data class CustomEmoji(
    val shortcode: String,
    val url: String,
    val static_url: String,
    val visible_in_picker: Boolean,
    val category: String,
)

data class Field(
    val name: String,
    val value: String,
    val verified_at: String?,
)

data class Source(
    val privacy: String, // TODO: enum
    val sensitive: Boolean,
    val language: String,
    val note: String,
    val fields: List<Field>,
    val follow_requests_count: Int,
)

data class Role(
    val id: Int,
    val name: String,
    val color: String,
    val position: Int,
    val permissions: Int, // TODO: parse
    val highlighted: Boolean,
    val created_at: String,
    val updated_at: String,
)