package codes.bruno.raki.core.data.repository

import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.model.Account
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import javax.inject.Inject

internal class MastodonDataRepositoryImpl @Inject constructor(
    private val mastodonApi: MastodonApiDataSource,
) : MastodonDataRepository {

    override suspend fun fetchAccount(): Account {
        return mastodonApi.verifyCredentials().asDomainModel()
    }

}