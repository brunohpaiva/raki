package codes.bruno.raki.core.domain.usecase

import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import javax.inject.Inject

class ToggleStatusFavouriteUseCase @Inject internal constructor(
    private val mastodonDataRepository: MastodonDataRepository,
) {

    suspend operator fun invoke(statusId: String) {
        mastodonDataRepository.toggleStatusFavourite(statusId)
    }

}