package codes.bruno.raki

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.bruno.raki.core.domain.usecase.auth.IsLoggedInFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    isLoggedInFlowUseCase: IsLoggedInFlowUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = isLoggedInFlowUseCase().mapLatest { isLoggedIn ->
        MainActivityUiState.Success(isLoggedIn = isLoggedIn)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainActivityUiState.Loading,
    )

}

internal sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(
        val isLoggedIn: Boolean,
    ) : MainActivityUiState
}