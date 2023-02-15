package codes.bruno.raki.data.remote.api

import codes.bruno.raki.data.remote.model.MastodonApp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface MastodonService {

    @POST("api/v1/apps")
    @FormUrlEncoded
    suspend fun createApp(
        @Field("client_name") clientName: String,
        @Field("redirect_uris") redirectUris: String,
        @Field("scopes") scopes: String? = null,
        @Field("website") website: String? = null,
    ): MastodonApp

}