package ivansantos.marvelcharacters.data.repositories

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import kotlinx.coroutines.runBlocking

class InMemoryMarvelCharactersRepository : MarvelCharactersRepository {

    override val marvelCharacters: MutableLiveData<List<MarvelCharacter>> =
        MutableLiveData(
            runBlocking { createSampleCharacters() })

    override fun createSampleCharacters(): List<MarvelCharacter> {
        return mutableListOf(
            MarvelCharacter("Captain America", "urlExample/CaptainAmerica.png"),
            MarvelCharacter("Wonder Woman", "urlExample/WonderWoman.png"),
            MarvelCharacter("Hulk", "urlExample/Hulk.png"),
            MarvelCharacter("Black Panther", "urlExample/Black Panther.png"),
        )
    }
}