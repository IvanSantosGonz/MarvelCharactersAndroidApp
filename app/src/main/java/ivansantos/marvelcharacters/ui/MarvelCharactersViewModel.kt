package ivansantos.marvelcharacters.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ivansantos.marvelcharacters.data.Result
import ivansantos.marvelcharacters.data.Result.Success
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(private val marvelCharactersRepository: MarvelCharactersRepository) :
    ViewModel() {

    val characters: LiveData<MutableList<MarvelCharacter>> =
        marvelCharactersRepository.marvelCharacters.switchMap { marvelCharactersResult ->
            insertFetchedCharactersFrom(marvelCharactersResult)
        }

    val selectedCharacter: MutableLiveData<MarvelCharacter> = MutableLiveData<MarvelCharacter>()

    fun loadCharacters() {
        viewModelScope.launch {
            val loadedCharacters = characters.value
            if (loadedCharacters == null) {
                marvelCharactersRepository.retrieveCharactersFrom()
            } else {
                marvelCharactersRepository.retrieveCharactersFrom(loadedCharacters.size)
            }
        }
    }

    fun removeCharacters() {
        characters.value?.clear()
    }

    fun loadCharactersBy(name: String) {
        viewModelScope.launch {
            val loadedCharacters = characters.value
            if (loadedCharacters == null) {
                marvelCharactersRepository.retrieveCharactersFrom(characterName = name)
            } else {
                marvelCharactersRepository.retrieveCharactersFrom(loadedCharacters.size, name)
            }
        }
    }

    fun setSelected(marvelCharacter: MarvelCharacter) {
        selectedCharacter.postValue(marvelCharacter)
    }

    private val _isLoadingCharacters: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingCharacters: LiveData<Boolean> = _isLoadingCharacters

    private val _isErrorLoadingCharacters: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorLoadingCharacters: LiveData<Boolean> = _isErrorLoadingCharacters

    private fun insertFetchedCharactersFrom(marvelCharactersResult: Result<List<MarvelCharacter>>): LiveData<MutableList<MarvelCharacter>> {
        val marvelCharacters = mutableListOf<MarvelCharacter>()
        this.characters.value?.let { marvelCharacters.addAll(it) }
        getCharactersDataFrom(marvelCharactersResult).value?.let { marvelCharacters.addAll(it) }
        return MutableLiveData(marvelCharacters)
    }

    private fun getCharactersDataFrom(marvelCharactersResult: Result<List<MarvelCharacter>>?): LiveData<List<MarvelCharacter>> {
        val result = MutableLiveData<List<MarvelCharacter>>()
        when (marvelCharactersResult) {
            is Success -> {
                marvelCharactersResult.data.also { marvelCharacters ->
                    result.value = marvelCharacters
                }
                _isLoadingCharacters.postValue(false)
                _isErrorLoadingCharacters.postValue(false)
            }
            is Result.Error -> {
                result.value = emptyList()
                _isLoadingCharacters.postValue(false)
                _isErrorLoadingCharacters.postValue(true)
            }
            is Result.Loading -> {
                _isErrorLoadingCharacters.postValue(false)
                _isLoadingCharacters.postValue(true)
            }
        }

        return result
    }
}