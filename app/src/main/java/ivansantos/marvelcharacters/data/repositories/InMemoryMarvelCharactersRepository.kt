package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.LiveData
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
    override val totalCharacters: LiveData<Int> = MutableLiveData()
    override val favoriteCharacters: LiveData<List<MarvelCharacter>> = MutableLiveData()

    override suspend fun retrieveCharactersFrom(
        characterName: String?,
        numberOfLoadedCharacters: Int,
    ) {
        marvelCharacters.postValue(Result.Success(sampleCharacters))
    }

    override fun addToFavorites(marvelCharacter: MarvelCharacter) {
        TODO("Not yet implemented")
    }

    override fun removeFromFavorites(marvelCharacter: MarvelCharacter) {
        TODO("Not yet implemented")
    }

    override fun isFavorite(marvelCharacter: MarvelCharacter): Boolean {
        TODO("Not yet implemented")
    }
}