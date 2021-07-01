package ivansantos.marvelcharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ivansantos.marvelcharacters.data.MarvelCharactersInMemoryRepository
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
        val marvelCharactersRepository = MarvelCharactersInMemoryRepository()

        val marvelCharacters: List<MarvelCharacter> =
            marvelCharactersRepository.marvelCharacters.getOrAwaitValue()

        assertThat(marvelCharacters).isNotEmpty
    }
}

