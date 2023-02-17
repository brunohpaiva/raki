package codes.bruno.raki.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.bruno.raki.domain.usecase.CreateMastodonAppUseCase
import codes.bruno.raki.domain.usecase.GetStoredMastodonAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val getStoredMastodonAppUseCase: GetStoredMastodonAppUseCase,
    private val createMastodonAppUseCase: CreateMastodonAppUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun logIn() = viewModelScope.launch {
        _uiState.update { it.copy(loading = true) }

        val domain = _uiState.value.domain.trim()

        try {
            val mastodonApp =
                getStoredMastodonAppUseCase(domain) ?: createMastodonAppUseCase(domain)

            // TODO: move to login
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _uiState.update { it.copy(loading = false) }
        }
    }

    fun onChangeDomain(domain: String) {
        _uiState.update { it.copy(domain = domain) }
    }

}

internal data class LoginScreenUiState(
    val domain: String = "",
    val loading: Boolean = false,
)