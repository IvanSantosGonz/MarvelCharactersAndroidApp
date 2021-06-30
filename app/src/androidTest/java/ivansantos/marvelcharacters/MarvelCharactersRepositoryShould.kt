package ivansantos.marvelcharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.utils.getOrAwaitValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class MarvelCharactersRepositoryShould {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun return_some_marvel_characters() {
        val marvelCharactersRepository = MarvelCharactersRepository()

        val marvelCharacters: List<MarvelCharacter> =
            marvelCharactersRepository.marvelCharacters.getOrAwaitValue()

        assertThat(marvelCharacters).isNotEmpty
    }

}

class MarvelCharactersRepository {

    val marvelCharacters: LiveData<List<MarvelCharacter>> = MutableLiveData(listOf())
}
