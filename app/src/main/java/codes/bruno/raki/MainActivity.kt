package codes.bruno.raki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import codes.bruno.raki.ui.theme.RakiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RakiTheme {
                App()
            }
        }
    }
}
