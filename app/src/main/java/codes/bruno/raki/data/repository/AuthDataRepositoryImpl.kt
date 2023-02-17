package codes.bruno.raki.data.repository

import codes.bruno.raki.data.local.source.LocalAuthDataDataSource
import codes.bruno.raki.data.remote.source.RemoteAuthDataDataSource
import codes.bruno.raki.domain.model.MastodonApp
import codes.bruno.raki.domain.repository.AuthDataRepository
import javax.inject.Inject

internal class AuthDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalAuthDataDataSource,
    private val remoteDataSource: RemoteAuthDataDataSource,
) : AuthDataRepository {

    override suspend fun getMastodonApp(domain: String): MastodonApp? {
        return localDataSource.getMastodonApp(domain)
    }

    override suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String>,
        website: String?
    ): MastodonApp {
        return remoteDataSource.createMastodonApp(
            domain,
            clientName,
            redirectUris,
            scopes,
            website,
        ).also { localDataSource.saveMastodonApp(it) }
    }
}