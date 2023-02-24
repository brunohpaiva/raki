package codes.bruno.raki.core.domain.repository

import codes.bruno.raki.core.domain.model.Account

interface MastodonDataRepository {
    suspend fun fetchAccount(): Account
}