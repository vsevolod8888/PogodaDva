package apiservise

import com.example.pogoda2.dataanswer.Base
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val logging = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
private val httpClientBuilder = OkHttpClient.Builder().apply { addInterceptor(logging) }

var BASE_URL:String = "https://api.openweathermap.org/"

var retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(httpClientBuilder.build())
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface WeatherApiService {

    @GET("data/2.5/forecast/daily")
    suspend fun getProperties2(@Query("lat")lat:String,@Query("lon")lon:String,@Query("cnt")cnt: Int, @Query("units")units:String,@Query("lang")lang:String, @Query("appid")appid:String="e84d0e4e817077869ae03dca40e16f38"): Response<Base>

    @GET("data/2.5/forecast/daily")
    suspend fun getProperties1(@Query("q")q:String,@Query("cnt")cnt: Int,@Query("units")units:String,@Query("lang")lang:String,@Query("appid")appid:String ="e84d0e4e817077869ae03dca40e16f38"): Response<Base>
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java) }}