package ivansantos.marvelcharacters.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface MarvelCharactersRepository

val marvelCharacters: LiveData<List<MarvelCharacter>> = MutableLiveData()