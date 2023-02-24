package codes.bruno.raki.core.domain.usecase

import codes.bruno.raki.core.domain.model.Account
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import javax.inject.Inject

class FetchAccountUseCase @Inject internal constructor(
    private val mastodonDataRepository: MastodonDataRepository,
) {

    suspend operator fun invoke(): Account {
        return mastodonDataRepository.fetchAccount()
    }

}