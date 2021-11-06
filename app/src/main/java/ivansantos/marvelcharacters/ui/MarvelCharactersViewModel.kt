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

    private var _existCharacters: MutableLiveData<Boolean> = MutableLiveData(false)
    val existCharacters: LiveData<Boolean> = _existCharacters

    val characters: LiveData<List<MarvelCharacter>> =
        marvelCharactersRepository.marvelCharacters.map { marvelCharactersResult ->
            if (getCharactersFrom(marvelCharactersResult).value == null) {
                listOf()
            } else {
                getCharactersFrom(marvelCharactersResult).value!!
            }
        }

    val selectedCharacter: MutableLiveData<MarvelCharacter> = MutableLiveData<MarvelCharacter>()

    private val isLoadingCharacters: MutableLiveData<Boolean> = MutableLiveData(false)

    fun createSampleCharacters() {
        viewModelScope.launch { marvelCharactersRepository.createSampleCharacters() }
    }

    fun setSelected(marvelCharacter: MarvelCharacter) {
        selectedCharacter.postValue(marvelCharacter)
    }

    private fun getCharactersFrom(marvelCharactersResult: Result<List<MarvelCharacter>>?): LiveData<List<MarvelCharacter>> {
        val result = MutableLiveData<List<MarvelCharacter>>()
        when (marvelCharactersResult) {
            is Success -> {
                marvelCharactersResult.data.also { marvelCharacters ->
                    result.value = marvelCharacters
                }
                isLoadingCharacters.postValue(false)
                _existCharacters.postValue(true)
            }
            is Result.Error -> {
                result.value = emptyList()
            }
            is Result.Loading -> {
                isLoadingCharacters.postValue(true)
            }
        }

        return result
    }
}