package codes.bruno.raki.domain.usecase

import codes.bruno.raki.domain.model.MastodonApp
import codes.bruno.raki.domain.repository.AuthDataRepository
import javax.inject.Inject

class GetStoredMastodonAppUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
) {

    suspend operator fun invoke(domain: String): MastodonApp? {
        return authDataRepository.getMastodonApp(domain)
    }

}