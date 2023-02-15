package codes.bruno.raki.feature.auth

import androidx.lifecycle.ViewModel
import codes.bruno.raki.data.remote.api.MastodonService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val mastodonService: MastodonService,
) : ViewModel() {

    fun logIn() {
        // TODO
    }

}