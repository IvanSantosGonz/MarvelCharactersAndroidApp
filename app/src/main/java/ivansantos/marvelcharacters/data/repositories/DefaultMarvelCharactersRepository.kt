package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.data.RemoteDataSource
import ivansantos.marvelcharacters.data.Result
import ivansantos.marvelcharacters.data.network.MarvelAPI
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import javax.inject.Inject

class DefaultMarvelCharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val marvelAPI: MarvelAPI,
) : MarvelCharactersRepository {
    override val marvelCharacters: MutableLiveData<Result<List<MarvelCharacter>>> =
        MutableLiveData<Result<List<MarvelCharacter>>>()

    override suspend fun createSampleCharacters() { //TODO: remove and use only retrieveCharactersFrom
        marvelCharacters.postValue(Result.Loading)

        kotlin.runCatching {
            remoteDataSource.getCharacters(
                marvelAPI.timestamp.toString(),
                marvelAPI.apiKey,
                marvelAPI.hash)
        }
            .onSuccess { marvelAPIResponseDTO ->
                marvelCharacters.postValue(Result.Success(marvelAPIResponseDTO.getCharacters()))
            }
            .onFailure { error: Throwable ->
                marvelCharacters.postValue(Result.Error(error))
            }

    }

    override suspend fun retrieveCharactersFrom(numberOfLoadedCharacters: Int) {
        marvelCharacters.postValue(Result.Loading)

        kotlin.runCatching {
            remoteDataSource.getCharactersFrom(
                marvelAPI.timestamp.toString(),
                marvelAPI.apiKey,
                marvelAPI.hash,
                numberOfLoadedCharacters
            )

        }
            .onSuccess { marvelAPIResponseDTO ->
                marvelCharacters.postValue(Result.Success(marvelAPIResponseDTO.getCharacters()))
            }
            .onFailure { error: Throwable ->
                marvelCharacters.postValue(Result.Error(error))
            }
    }
}