package codes.bruno.raki.core.data.network.retrofit

import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.Account
import codes.bruno.raki.core.data.network.model.MastodonApp
import codes.bruno.raki.core.data.network.model.OAuthToken
import codes.bruno.raki.core.data.network.model.Status
import codes.bruno.raki.core.data.network.model.Timeline
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
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

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun createAccessToken(
        @Header(DOMAIN_HEADER) domain: String,
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("scope") scope: String,
    ): OAuthToken

    @GET("api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(): Account

    @GET("api/v1/timelines/home")
    suspend fun viewHomeTimeline(
        @Query("limit") limit: Int,
        @Query("min_id") minId: String? = null,
        @Query("max_id") maxId: String? = null,
        @Query("since_id") sinceId: String? = null,
    ): Response<List<Status>>

    @POST("api/v1/statuses/{id}/favourite")
    suspend fun favouriteStatus(
        @Path("id") id: String,
    ): Status

    @POST("api/v1/statuses/{id}/unfavourite")
    suspend fun unfavouriteStatus(
        @Path("id") id: String,
    ): Status

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
        website: String?,
    ): MastodonApp {
        return service.createApp(
            domain,
            clientName,
            redirectUris,
            scopes.joinToString(" "),
            website,
        )
    }

    override suspend fun createAccessToken(
        domain: String,
        grantType: String,
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        scopes: List<String>,
    ): OAuthToken {
        return service.createAccessToken(
            domain,
            grantType,
            code,
            clientId,
            clientSecret,
            redirectUri,
            scopes.joinToString(" "),
        )
    }

    override suspend fun verifyCredentials(): Account {
        return service.verifyCredentials()
    }

    override suspend fun fetchTimeline(
        limit: Int,
        minId: String?,
        maxId: String?,
        sinceId: String?,
    ): Timeline {
        val response = service.viewHomeTimeline(limit, minId, maxId, sinceId)
        if (!response.isSuccessful) {
            // TODO: handle errors
            throw IOException()
        }

        val pagingKeys = parsePagingKeys(response.headers()["Link"])

        return Timeline(
            statuses = response.body() ?: emptyList(),
            prevKey = pagingKeys.first,
            nextKey = pagingKeys.second,
        )
    }

    override suspend fun favouriteStatus(id: String): Status {
        return service.favouriteStatus(id)
    }

    override suspend fun unfavouriteStatus(id: String): Status {
        return service.unfavouriteStatus(id)
    }

    private companion object {
        val LINK_HEADER_REGEX = "=(\\d+)>; rel=\"(\\w+)\"".toRegex()

        fun parsePagingKeys(linkHeader: String?): Pair<String?, String?> {
            if (linkHeader == null) return null to null

            val results = LINK_HEADER_REGEX.findAll(linkHeader).map { it.groupValues }

            val prev = results.find { it[2] == "prev" }?.get(1)
            val next = results.find { it[2] == "next" }?.get(1)

            return prev to next
        }
    }
}