package ivansantos.marvelcharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ivansantos.marvelcharacters.data.Result
import ivansantos.marvelcharacters.data.repositories.InMemoryMarvelCharactersRepository
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
        val marvelCharactersRepository = InMemoryMarvelCharactersRepository()

        val marvelCharacters: Result<List<MarvelCharacter>>? =
            marvelCharactersRepository.marvelCharacters.getOrAwaitValue()

        val characters: List<MarvelCharacter> = (marvelCharacters as Result.Success).data
        assertThat(characters).isNotEmpty
    }

    @Test
    fun return_some_marvel_characters_with_name_and_thumbnail() {
        val marvelCharactersRepository = InMemoryMarvelCharactersRepository()

        val marvelCharacters: Result<List<MarvelCharacter>>? =
            marvelCharactersRepository.marvelCharacters.getOrAwaitValue()

        val characters: List<MarvelCharacter> = (marvelCharacters as Result.Success).data
        assertThat(characters).isNotEmpty
        characters.forEach {
            assertThat(it.characterName).isNotEmpty
            assertThat(it.thumbnailImage).isNotNull
        }
    }
}

