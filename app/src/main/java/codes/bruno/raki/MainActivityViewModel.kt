package codes.bruno.raki

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class MainActivityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        // TODO: load auth data and check if it's logged in
        _uiState.update { MainActivityUiState.Success }
    }

}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    object Success : MainActivityUiState
}