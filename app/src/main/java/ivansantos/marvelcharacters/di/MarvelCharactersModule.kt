package ivansantos.marvelcharacters.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.data.MarvelAPI
import ivansantos.marvelcharacters.data.PicassoThumbnailService
import ivansantos.marvelcharacters.data.RemoteDataSource
import ivansantos.marvelcharacters.data.repositories.DefaultMarvelCharactersRepository
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import ivansantos.marvelcharacters.domain.ThumbnailService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MarvelCharactersModule {

    @Singleton
    @Provides
    fun provideMarvelApi(@ApplicationContext appContext: Context): MarvelAPI {
        return MarvelAPI(
            apiKey = appContext.getString(R.string.api_key),
            privateKey = appContext.getString(R.string.private_key),
        )
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(marvelAPI: MarvelAPI): RemoteDataSource {
        val retrofit = Retrofit.Builder()
            .baseUrl(marvelAPI.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideMarvelCharactersRepository(
        remoteDataSource: RemoteDataSource,
        marvelAPI: MarvelAPI,
    ): MarvelCharactersRepository {
        return DefaultMarvelCharactersRepository(remoteDataSource, marvelAPI)
    }

    @Singleton
    @Provides
    fun provideThumbnailService(): ThumbnailService {
        return PicassoThumbnailService()
    }
}