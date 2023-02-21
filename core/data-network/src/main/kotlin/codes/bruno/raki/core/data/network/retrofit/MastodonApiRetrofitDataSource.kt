package codes.bruno.raki.core.data.network.retrofit

import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.MastodonApp
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

internal interface MastodonApiService {

    @POST("api/v1/apps")
    @FormUrlEncoded
    suspend fun createApp(
        @Header(DOMAIN_HEADER) domain: String,
        @Field("client_name") clientName: String,
        @Field("redirect_uris") redirectUris: String,
        @Field("scopes") scopes: String? = null,
        @Field("website") website: String? = null,
    ): MastodonApp

}

@Singleton
internal class MastodonApiRetrofitDataSource @Inject constructor(
    retrofit: Retrofit,
) : MastodonApiDataSource {

    private val service: MastodonApiService = retrofit.create()

    override suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String>,
        website: String?
    ): MastodonApp {
        return service.createApp(
            domain,
            clientName,
            redirectUris,
            scopes.joinToString(" "),
            website,
        )
    }
}