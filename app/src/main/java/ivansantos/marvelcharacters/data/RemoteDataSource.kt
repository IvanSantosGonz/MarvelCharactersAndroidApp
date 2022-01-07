package ivansantos.marvelcharacters.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("characters")
    suspend fun getCharactersFrom(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
    ): MarvelAPIResponseDTO

    @GET("characters")
    suspend fun getCharactersFilterByName(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("nameStartsWith") nameStartsWith: String,
    ): MarvelAPIResponseDTO
}