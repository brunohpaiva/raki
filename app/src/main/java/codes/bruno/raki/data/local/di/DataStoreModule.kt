package codes.bruno.raki.data.local.di

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import codes.bruno.raki.data.local.model.AuthData
import com.google.protobuf.InvalidProtocolBufferException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Singleton
    @Provides
    fun provideAuthDataDataStore(@ApplicationContext context: Context): DataStore<AuthData> {
        return DataStoreFactory.create(
            serializer = AuthDataSerializer,
            produceFile = { context.dataStoreFile("auth_data.pb") },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        )
    }

}

private object AuthDataSerializer : Serializer<AuthData> {

    override val defaultValue: AuthData = AuthData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AuthData {
        try {
            return AuthData.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: AuthData, output: OutputStream) {
        t.writeTo(output)
    }
}