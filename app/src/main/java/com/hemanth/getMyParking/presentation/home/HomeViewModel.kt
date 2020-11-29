package com.hemanth.getMyParking.presentation.home

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemanth.getMyParking.base.BaseViewModel
import com.hemanth.getMyParking.common.Constants
import com.hemanth.getMyParking.common.NoConnectivityException
import com.hemanth.getMyParking.data.model.BookResponse
import com.hemanth.getMyParking.data.repository.HomeRepository
import com.hemanth.getMyParking.utilities.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import java.util.concurrent.TimeUnit

class HomeViewModel @ViewModelInject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    companion object {
        var PAGE_OFFSET = 10
        var PAGE_SIZE = 0
    }

    private val compositeDisposable = CompositeDisposable()

    val loading = ObservableBoolean(true)
    val isRefresh = ObservableBoolean(true)

    private val _eventBookList = MutableLiveData<Event<Pair<String, *>>>()
    val eventBookList: LiveData<Event<Pair<String, *>>> = _eventBookList

    lateinit var bookList: ArrayList<BookResponse.Item>

    var searchText: String? = null

    lateinit var publishSubject: PublishSubject<String>

    /**
     * <h2>getBookList</h2>
     * this is the method to get news response from Api
     */
    fun getBooksList() {
        setLoading(true)
        val disposableObserver =
            object : DisposableSingleObserver<Response<BookResponse>>() {
                override fun onSuccess(value: Response<BookResponse>) {
                    when (value.code()) {
                        200, 400 -> {
                            saveResponse(value.body() as BookResponse)
                            _eventBookList.postValue(
                                Event(Pair(Constants.SUCCESS, value.body()))
                            )
                        }
                        else -> _eventBookList.postValue(
                            Event(Pair(Constants.ERROR, value.code().toString()))
                        )
                    }
                    setLoading(false)
                }

                override fun onError(e: Throwable) {
                    setLoading(false)
                    if (e is NoConnectivityException)
                        _eventBookList.postValue(Event(Pair(Constants.NO_INTERNET, e.message)))
                    else
                        _eventBookList.postValue(Event(Pair(Constants.ERROR, e.message)))
                }
            }
        homeRepository.getDetails(searchText).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)
    }

    private fun saveResponse(bookResponse: BookResponse?) {
        if (isRefresh.get()) {
            bookList.clear()
            PAGE_SIZE = 0
        }
        PAGE_SIZE += PAGE_OFFSET
        bookResponse?.items?.let { bookList.addAll(it) }
    }

    fun setLoading(isLoading: Boolean) {
        if (isRefresh.get())
            loading.set(isLoading)
    }

    fun onRefresh() {
        isRefresh.set(true)
        searchText = null
        getBooksList()
    }

    fun searchRxObserver() {
        publishSubject.debounce(800, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<String> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: String) {
                    searchText = t
                    isRefresh.set(true)
                    getBooksList()
                }

                override fun onError(e: Throwable) {}
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()

    }
}