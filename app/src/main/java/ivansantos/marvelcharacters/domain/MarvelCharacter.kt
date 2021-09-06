package ivansantos.marvelcharacters.domain

data class MarvelCharacter(val characterName: String, val thumbnailImage: ThumbnailImage)

data class ThumbnailImage(val url: String, val extension: String)