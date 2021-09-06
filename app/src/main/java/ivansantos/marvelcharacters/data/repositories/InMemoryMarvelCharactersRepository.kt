package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import ivansantos.marvelcharacters.domain.ThumbnailImage
import kotlinx.coroutines.runBlocking

class InMemoryMarvelCharactersRepository : MarvelCharactersRepository {

    override val marvelCharacters: MutableLiveData<List<MarvelCharacter>> =
        MutableLiveData(
            runBlocking { createSampleCharacters() })

    override fun createSampleCharacters(): List<MarvelCharacter> {
        return mutableListOf(
            MarvelCharacter("Captain America", ThumbnailImage("urlExample/CaptainAmerica", "png")),
            MarvelCharacter("Wonder Woman", ThumbnailImage("urlExample/WonderWoman", "png")),
            MarvelCharacter("Hulk", ThumbnailImage("urlExample/Hulk", "png")),
            MarvelCharacter("Black Panther", ThumbnailImage("urlExample/Black Panther", "png")),
        )
    }
}