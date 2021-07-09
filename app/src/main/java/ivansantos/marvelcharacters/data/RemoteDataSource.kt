package ivansantos.marvelcharacters.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("characters")
    suspend fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): MarvelAPIResponseDTO
}