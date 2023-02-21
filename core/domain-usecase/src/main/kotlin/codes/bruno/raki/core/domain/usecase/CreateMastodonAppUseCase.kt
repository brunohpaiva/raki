package codes.bruno.raki.core.domain.usecase

import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import javax.inject.Inject

class CreateMastodonAppUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
) {

    suspend operator fun invoke(domain: String): MastodonApp {
        // TODO: move these arguments to constants
        return authDataRepository.createMastodonApp(
            domain,
            "Raki",
            "raki-oauth2-callback://",
            scopes = listOf("read", "write", "push"),
            website = "https://github.com/brunohpaiva/raki",
        )
    }

}