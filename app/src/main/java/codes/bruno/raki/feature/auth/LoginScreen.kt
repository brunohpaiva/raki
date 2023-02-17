package codes.bruno.raki.feature.auth

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import codes.bruno.raki.R

@Composable
internal fun LoginScreen() {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LoginForm(
        uiState = uiState,
        onChangeDomain = viewModel::onChangeDomain,
        onLogin = {
            viewModel.logIn()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    uiState: LoginScreenUiState,
    onChangeDomain: (String) -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        TextField(
            label = { Text(text = stringResource(R.string.domain_label)) },
            value = uiState.domain,
            onValueChange = onChangeDomain,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = onLogin,
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(text = stringResource(R.string.log_in_button))
        }
    }
}