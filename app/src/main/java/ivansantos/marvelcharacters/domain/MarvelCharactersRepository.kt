package ivansantos.marvelcharacters.domain

import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.data.Result

interface MarvelCharactersRepository {

    val marvelCharacters: MutableLiveData<Result<List<MarvelCharacter>>>

    suspend fun createSampleCharacters()
}