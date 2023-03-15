package codes.bruno.raki.feature.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import codes.bruno.raki.core.domain.model.Account
import codes.bruno.raki.core.domain.usecase.FetchMastodonAppUseCase
import codes.bruno.raki.core.domain.usecase.FinishAuthorizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val fetchMastodonAppUseCase: FetchMastodonAppUseCase,
    private val finishAuthorizationUseCase: FinishAuthorizationUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun startAuthorization(): Uri? {
        _uiState.update { it.copy(loading = true) }

        val domain = _uiState.value.domainFieldValue.trim()

        return try {
            val mastodonApp = fetchMastodonAppUseCase(domain)

            Uri.Builder().run {
                scheme("https")
                authority(domain)
                appendEncodedPath("oauth/authorize")
                appendQueryParameter("client_id", mastodonApp.clientId)
                appendQueryParameter("scope", "read write")
                appendQueryParameter("redirect_uri", "raki://oauth2-callback")
                appendQueryParameter("response_type", "code")
                build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            _uiState.update { it.copy(loading = false) }
        }
    }

    suspend fun finishAuthorization(code: String): Account? {
        _uiState.update { it.copy(loading = true) }

        return try {
            finishAuthorizationUseCase(code)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            _uiState.update { it.copy(loading = false) }
        }
    }

    fun onChangeDomain(value: String) {
        _uiState.update { it.copy(domainFieldValue = value) }
    }

}

internal data class LoginScreenUiState(
    val domainFieldValue: String = "",
    val loading: Boolean = false,
)