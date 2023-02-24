package codes.bruno.raki.core.domain.usecase

import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import javax.inject.Inject

class FetchMastodonAppUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
) {

    suspend operator fun invoke(domain: String): MastodonApp {
        return authDataRepository.getMastodonApp(domain) ?: authDataRepository.createMastodonApp(
            domain,
            "Raki",
            "raki://oauth2-callback",
            listOf("read", "write", "push"),
            "https://github.com/brunohpaiva/raki",
        )
    }

}