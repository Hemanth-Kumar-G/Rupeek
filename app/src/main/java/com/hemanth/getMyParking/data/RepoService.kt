package com.hemanth.getMyParking.data


import com.hemanth.getMyParking.data.model.BookResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    @GET("/books/v1/volumes")
    fun getDetails(@Query("q") search: String): Single<Response<BookResponse>>

}