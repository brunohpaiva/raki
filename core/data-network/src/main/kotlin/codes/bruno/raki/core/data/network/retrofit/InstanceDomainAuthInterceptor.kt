package codes.bruno.raki.core.data.network.retrofit

import codes.bruno.raki.core.data.datastore.AuthDataDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal const val DOMAIN_HEADER = "Instance-Domain"
internal const val DOMAIN_PLACEHOLDER = "default.default"

internal class InstanceDomainAuthInterceptor @Inject constructor(
    private val authDataDataSource: AuthDataDataSource,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.host != DOMAIN_PLACEHOLDER) {
            return chain.proceed(request)
        }

        val domainHeaderValue = request.header(DOMAIN_HEADER)
        // TODO: a better way to cache the currentUser value?
        val currentUser = if (domainHeaderValue == null)
            runBlocking { authDataDataSource.getCurrentUser() }
        else
            null
        val targetDomain = currentUser?.domain ?: domainHeaderValue ?: error("no domain")

        val newRequest = request.newBuilder().run {
            val newUrl = request.url.newBuilder().run {
                host(targetDomain)
                build()
            }

            url(newUrl)
            removeHeader(DOMAIN_HEADER)

            if (currentUser?.hasTokenType() == true && currentUser.hasAccessToken()) {
                header("Authorization", "${currentUser.tokenType} ${currentUser.accessToken}")
            }

            build()
        }

        return chain.proceed(newRequest)
    }

}
