package codes.bruno.raki.core.data.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

internal const val DOMAIN_HEADER = "Instance-Domain"
internal const val DOMAIN_PLACEHOLDER = "default.default"

internal class InstanceDomainAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.host != DOMAIN_PLACEHOLDER) {
            return chain.proceed(request)
        }

        val targetDomain = request.header(DOMAIN_HEADER) ?: return chain.proceed(request)

        val newRequest = request.newBuilder().run {
            val newUrl = request.url.newBuilder().run {
                host(targetDomain)
                build()
            }

            url(newUrl)
            removeHeader(DOMAIN_HEADER)
            build()
        }

        return chain.proceed(newRequest)
    }

}
