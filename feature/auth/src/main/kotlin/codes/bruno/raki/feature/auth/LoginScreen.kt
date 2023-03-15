package codes.bruno.raki.feature.auth

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@Composable
internal fun LoginScreen(returnedCode: String? = null, onFinishAuth: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val customTabsIntent = remember {
        CustomTabsIntent.Builder().build().apply {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    val currentOnFinishAuth by rememberUpdatedState(onFinishAuth)
    LaunchedEffect(returnedCode) {
        if (returnedCode != null) {
            val account = viewModel.finishAuthorization(returnedCode)
            if (account != null) {
                currentOnFinishAuth()
            }
        }
    }

    LoginForm(
        domainFieldValue = uiState.domainFieldValue,
        onChangeDomain = viewModel::onChangeDomain,
        onClickAuthorize = {
            viewModel.viewModelScope.launch {
                val uri = viewModel.startAuthorization()
                if (uri != null) {
                    customTabsIntent.launchUrl(context, uri)
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    domainFieldValue: String,
    onChangeDomain: (String) -> Unit,
    onClickAuthorize: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        TextField(
            label = { Text(text = stringResource(R.string.instance_domain_label)) },
            value = domainFieldValue,
            onValueChange = onChangeDomain,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = onClickAuthorize,
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(text = stringResource(R.string.authorize_button))
        }
    }
}