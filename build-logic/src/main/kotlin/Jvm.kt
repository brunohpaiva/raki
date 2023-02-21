import org.gradle.api.Plugin
import org.gradle.api.Project

class Jvm : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("kotlin.jvm"))
            }

            configureKotlin()
        }
    }

}