package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.data.Result
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import ivansantos.marvelcharacters.domain.ThumbnailImage

class InMemoryMarvelCharactersRepository : MarvelCharactersRepository {

    private val sampleCharacters = listOf(
        MarvelCharacter("Captain America", ThumbnailImage("urlExample/CaptainAmerica", "png")),
        MarvelCharacter("Wonder Woman", ThumbnailImage("urlExample/WonderWoman", "png")),
        MarvelCharacter("Hulk", ThumbnailImage("urlExample/Hulk", "png")),
        MarvelCharacter("Black Panther", ThumbnailImage("urlExample/Black Panther", "png")),
    )

    override val marvelCharacters: MutableLiveData<Result<List<MarvelCharacter>>> =
        MutableLiveData(Result.Success(sampleCharacters))

    override suspend fun retrieveCharactersFrom(numberOfLoadedCharacters: Int) {
        marvelCharacters.postValue(Result.Success(sampleCharacters))
    }
}