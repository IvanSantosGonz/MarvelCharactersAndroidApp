package ivansantos.marvelcharacters.domain

import androidx.lifecycle.MutableLiveData

interface MarvelCharactersRepository {

    val marvelCharacters: MutableLiveData<List<MarvelCharacter>>

    fun createSampleCharacters(): List<MarvelCharacter>
}