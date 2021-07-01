package ivansantos.marvelcharacters.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ivansantos.marvelcharacters.data.MarvelCharactersInMemoryRepository
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MarvelCharactersModule {

    @Singleton
    @Provides
    fun provideMarvelCharactersRepository(): MarvelCharactersRepository {
        return MarvelCharactersInMemoryRepository()
    }
}