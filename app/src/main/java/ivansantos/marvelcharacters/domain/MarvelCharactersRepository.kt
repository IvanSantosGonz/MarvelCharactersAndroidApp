package ivansantos.marvelcharacters.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.data.Result

interface MarvelCharactersRepository {

    val marvelCharacters: MutableLiveData<Result<List<MarvelCharacter>>>
    val totalCharacters: LiveData<Int>

    suspend fun retrieveCharactersFrom(
        characterName: String?,
        numberOfLoadedCharacters: Int = 0,
    )
}