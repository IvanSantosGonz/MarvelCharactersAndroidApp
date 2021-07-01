package ivansantos.marvelcharacters.data

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository

class MarvelCharactersInMemoryRepository : MarvelCharactersRepository {

    override val marvelCharacters: MutableLiveData<List<MarvelCharacter>> =
        MutableLiveData(createSampleCharacters())

    override fun createSampleCharacters(): List<MarvelCharacter> {
        return mutableListOf(
            MarvelCharacter("Captain America"),
            MarvelCharacter("Wonder Woman"),
            MarvelCharacter("Hulk"),
            MarvelCharacter("Black Panther"),
        )
    }
}