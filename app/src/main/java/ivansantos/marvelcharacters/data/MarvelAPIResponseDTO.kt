package ivansantos.marvelcharacters.data

import ivansantos.marvelcharacters.domain.MarvelCharacter

class MarvelAPIResponseDTO(
    val code: String,
    val data: Data,
    val status: String,
) {
    fun getCharacters(): List<MarvelCharacter> {
        return this.data.results.map {
            MarvelCharacter(it.name)
        }
    }

}

data class Data(
    val count: String,
    val limit: String,
    val offset: String,
    val results: List<Result>,
    val total: String,
)

data class Result(
    val description: String,
    val id: String,
    val modified: String,
    val name: String,
    val thumbnail: Thumbnail,
)

data class Thumbnail(
    val extension: String,
    val path: String,
)