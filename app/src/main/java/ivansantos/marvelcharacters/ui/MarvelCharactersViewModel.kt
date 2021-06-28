package ivansantos.marvelcharacters.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ivansantos.marvelcharacters.domain.MarvelCharacter

class MarvelCharactersViewModel : ViewModel() {

    val characters: MutableLiveData<MutableList<MarvelCharacter>> =
        MutableLiveData<MutableList<MarvelCharacter>>()
    var selectedCharacter: MutableLiveData<MarvelCharacter> = MutableLiveData<MarvelCharacter>()

    fun createSampleCharacters() {
        val characters = mutableListOf(
            MarvelCharacter("Captain America"),
            MarvelCharacter("Wonder Woman"),
            MarvelCharacter("Hulk"),
            MarvelCharacter("Black Panther"),
        )
        this.characters.value = characters
    }

    fun setSelected(marvelCharacter: MarvelCharacter) {
        selectedCharacter.postValue(marvelCharacter)
    }
}