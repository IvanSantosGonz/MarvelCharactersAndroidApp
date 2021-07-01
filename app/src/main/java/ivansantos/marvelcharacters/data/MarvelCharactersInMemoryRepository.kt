package ivansantos.marvelcharacters.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository

class MarvelCharactersInMemoryRepository : MarvelCharactersRepository {

    val marvelCharacters: LiveData<List<MarvelCharacter>> =
        MutableLiveData(createSampleCharacters())

    private fun createSampleCharacters(): List<MarvelCharacter> {
        return mutableListOf(
            MarvelCharacter("Captain America"),
            MarvelCharacter("Wonder Woman"),
            MarvelCharacter("Hulk"),
            MarvelCharacter("Black Panther"),
        )
    }
}