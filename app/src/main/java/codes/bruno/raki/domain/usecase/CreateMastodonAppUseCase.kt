package codes.bruno.raki.domain.usecase

import codes.bruno.raki.domain.model.MastodonApp
import codes.bruno.raki.domain.repository.AuthDataRepository
import javax.inject.Inject

class CreateMastodonAppUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
) {

    suspend operator fun invoke(domain: String): MastodonApp {
        // TODO: move these arguments to constants
        return authDataRepository.createMastodonApp(
            domain,
            "Raki",
            "urn:ietf:wg:oauth:2.0:oob",
            scopes = listOf("read", "write", "push"),
            website = "https://github.com/brunohpaiva/raki",
        )
    }

}