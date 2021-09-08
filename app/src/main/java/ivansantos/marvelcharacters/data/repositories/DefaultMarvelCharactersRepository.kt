package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.data.RemoteDataSource
import ivansantos.marvelcharacters.data.network.MarvelAPI
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DefaultMarvelCharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val marvelAPI: MarvelAPI,
) : MarvelCharactersRepository {
    override val marvelCharacters: MutableLiveData<List<MarvelCharacter>> =
        MutableLiveData(createSampleCharacters())

    override fun createSampleCharacters(): List<MarvelCharacter> {
        val marvelCharacterDto =
            runBlocking {
                remoteDataSource.getCharacters(marvelAPI.timestamp.toString(),
                    marvelAPI.apiKey,
                    marvelAPI.hash)
            }

        return marvelCharacterDto.getCharacters()
    }
}