package codes.bruno.raki.core.domain.usecase.auth

import codes.bruno.raki.core.domain.repository.AuthDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsLoggedInFlowUseCase @Inject internal constructor(
    private val authDataRepository: AuthDataRepository,
) {

    operator fun invoke() = authDataRepository.currentUserFlow.map { it != null }

}