package ivansantos.marvelcharacters.data.datasources

import androidx.lifecycle.map
import ivansantos.marvelcharacters.data.db.MarvelCharacterEntity
import ivansantos.marvelcharacters.data.db.MarvelCharacterFavoritesDao
import ivansantos.marvelcharacters.data.db.asDomainModel
import ivansantos.marvelcharacters.domain.MarvelCharacter
import javax.inject.Inject

class FavoritesDbDataSource @Inject constructor(private val marvelCharacterFavoritesDao: MarvelCharacterFavoritesDao) {

    val favoriteCharacters = marvelCharacterFavoritesDao.getAll().map { it.asDomainModel() }

    fun exists(marvelCharacter: MarvelCharacter): Boolean {
        val character = marvelCharacterFavoritesDao.findByName(marvelCharacter.characterName)
        return character != null
    }

    fun addToFavorites(marvelCharacter: MarvelCharacter) {
        val marvelCharacterEntity = MarvelCharacterEntity(marvelCharacter.characterName,
            marvelCharacter.description,
            marvelCharacter.thumbnailImage.url,
            marvelCharacter.thumbnailImage.extension)
        marvelCharacterFavoritesDao.insert(marvelCharacterEntity)
    }

    fun removeFromFavorites(marvelCharacter: MarvelCharacter) {
        marvelCharacterFavoritesDao.deleteBy(marvelCharacter.characterName)
    }
}