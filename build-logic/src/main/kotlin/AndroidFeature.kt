import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeature : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("raki.android.library")
                apply("raki.android.hilt")
            }

            dependencies {
                "implementation"(project(":core:design-system"))
                "implementation"(project(":core:domain-model"))
                "implementation"(project(":core:domain-usecase"))
                "implementation"(libs.lib("androidx.hilt.navigation.compose"))
            }
        }
    }

}