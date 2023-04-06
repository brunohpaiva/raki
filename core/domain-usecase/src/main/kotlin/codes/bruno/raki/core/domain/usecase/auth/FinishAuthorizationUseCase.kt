package codes.bruno.raki.core.domain.usecase.auth

import codes.bruno.raki.core.domain.model.Account
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import codes.bruno.raki.core.domain.usecase.FetchAccountUseCase
import javax.inject.Inject

class FinishAuthorizationUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
    private val fetchAccountUseCase: FetchAccountUseCase,
) {

    suspend operator fun invoke(code: String): Account {
        val currentUser = authDataRepository.getCurrentUser()
            ?: error("current user not found") // TODO: exceptions

        val mastodonApp =
            authDataRepository.getMastodonApp(currentUser.domain) ?: error("mastodon app not found")

        authDataRepository.createAccessToken(
            domain = mastodonApp.domain,
            grantType = "authorization_code",
            code = code,
            clientId = mastodonApp.clientId,
            clientSecret = mastodonApp.clientSecret,
            redirectUri = "raki://oauth2-callback",
            scopes = listOf("read", "write", "push"), // TODO: fetch from MastodonApp
        )

        return fetchAccountUseCase()
    }

}