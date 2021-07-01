package ivansantos.marvelcharacters.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import javax.inject.Inject

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(private val marvelCharactersRepository: MarvelCharactersRepository) :
    ViewModel() {

    val characters: MutableLiveData<List<MarvelCharacter>> =
        marvelCharactersRepository.marvelCharacters
    var selectedCharacter: MutableLiveData<MarvelCharacter> = MutableLiveData<MarvelCharacter>()

    fun createSampleCharacters() {
        marvelCharactersRepository.createSampleCharacters()
    }

    fun setSelected(marvelCharacter: MarvelCharacter) {
        selectedCharacter.postValue(marvelCharacter)
    }
}