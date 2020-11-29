package com.hemanth.getMyParking.data.repositoryImpl


import com.hemanth.getMyParking.data.RepoService
import com.hemanth.getMyParking.data.model.BookResponse
import com.hemanth.getMyParking.data.repository.HomeRepository
import io.reactivex.Single
import retrofit2.Response

const val DEFAULT_LOCATION = "f"

class HomeRepositoryImpl(
    private val repoService: RepoService
) : HomeRepository {

    override fun getDetails(search: String?): Single<Response<BookResponse>> =
        repoService.getDetails(search ?: DEFAULT_LOCATION)

}