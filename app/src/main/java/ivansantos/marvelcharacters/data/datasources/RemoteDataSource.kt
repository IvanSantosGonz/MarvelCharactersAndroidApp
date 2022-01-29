package ivansantos.marvelcharacters.data.datasources

import ivansantos.marvelcharacters.data.MarvelAPIResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("characters")
    suspend fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("nameStartsWith") nameStartsWith: String? = null,
    ): MarvelAPIResponseDTO
}