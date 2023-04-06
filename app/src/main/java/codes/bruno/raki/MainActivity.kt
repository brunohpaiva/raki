package codes.bruno.raki

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import codes.bruno.raki.core.designsystem.theme.RakiTheme
import codes.bruno.raki.ui.App
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach { uiState = it }.collect()
            }
        }

        splashScreen.setOnExitAnimationListener { provider ->
            ObjectAnimator.ofFloat(provider.view, View.ALPHA, 0F).apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 400L
                doOnEnd { provider.remove() }
            }.start()
        }

        splashScreen.setKeepOnScreenCondition {
            uiState == MainActivityUiState.Loading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RakiTheme {
                val state = uiState // Smart cast

                App(
                    windowSizeClass = calculateWindowSizeClass(this),
                    isLoggedIn = state is MainActivityUiState.Success && state.isLoggedIn,
                )
            }
        }
    }
}
