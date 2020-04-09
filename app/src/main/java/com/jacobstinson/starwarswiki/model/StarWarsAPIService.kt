package com.jacobstinson.starwarswiki.model

import com.jacobstinson.starwarswiki.model.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface StarWarsAPIService {

    companion object {
        val BASE_URL: String = "https://jsonplaceholder.typicode.com/"

        fun create(): StarWarsAPIService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(StarWarsAPIService::class.java)
        }
    }

    /*@GET("users")
    fun getUsers(): LiveData<Resource<List<User>>>

    @GET("posts")
    fun getUserPosts(@Query(value = "userId", encoded = true) userId: Int): LiveData<Resource<List<Post>>>

    @POST("posts")
    fun addPost(@Body post: Post): Call<Post>*/
}