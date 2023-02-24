package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.network.model.Account as NetworkAccount
import codes.bruno.raki.core.data.network.model.Field as NetworkField
import codes.bruno.raki.core.domain.model.Account as DomainAccount
import codes.bruno.raki.core.domain.model.Field as DomainField

fun NetworkAccount.asDomainModel() = DomainAccount(
    id = id,
    username = username,
    acct = acct,
    url = url,
    displayName = display_name,
    note = note,
    avatar = avatar,
    header = header,
    locked = locked,
    fields = fields.map { it.asDomainModel() },
    bot = bot,
    createdAt = created_at,
    statusesCount = statuses_count,
    followersCount = followers_count,
    followingCount = following_count,
)

fun NetworkField.asDomainModel() = DomainField(
    name = name,
    value = value,
    verifiedAt = verified_at,
)