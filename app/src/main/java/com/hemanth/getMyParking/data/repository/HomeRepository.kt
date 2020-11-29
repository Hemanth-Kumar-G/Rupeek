package com.hemanth.getMyParking.data.repository


import com.hemanth.getMyParking.data.model.BookResponse
import io.reactivex.Single
import retrofit2.Response

interface HomeRepository {

    fun getDetails(search: String?): Single<Response<BookResponse>>

}