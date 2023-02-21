import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidHilt : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("hilt.android"))
                apply(libs.plugin("kotlin.kapt"))
            }

            dependencies {
                "implementation"(libs.lib("hilt.android"))
                "kapt"(libs.lib("hilt.compiler"))
            }

            extensions.configure<KaptExtension> {
                correctErrorTypes = true
            }
        }
    }

}