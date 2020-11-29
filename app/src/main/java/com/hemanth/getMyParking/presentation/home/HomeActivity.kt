package com.hemanth.getMyParking.presentation.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hemanth.getMyParking.R
import com.hemanth.getMyParking.base.BaseActivity
import com.hemanth.getMyParking.common.Constants
import com.hemanth.getMyParking.data.model.BookResponse
import com.hemanth.getMyParking.databinding.ActivityHomeBinding
import com.hemanth.getMyParking.presentation.home.adapter.BookAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {


    @Inject
    lateinit var adapter: BookAdapter

    @Inject
    lateinit var bookList: ArrayList<BookResponse.Item>

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        viewModel.bookList = bookList
        viewModel.publishSubject = publishSubject
        init()
        viewModel.searchRxObserver()
        viewModel.getBooksList()
        setupObserver()
    }

    private fun init() {
        binding.rvHomeBookList.adapter = adapter

        binding.rvHomeBookList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if (HomeViewModel.PAGE_SIZE != 0 && (HomeViewModel.PAGE_SIZE - layoutManager.findLastVisibleItemPosition()) < 2) {
                    viewModel.isRefresh.set(false)
                    viewModel.getBooksList()
                }
            }
        })

        binding.svHomeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { publishSubject.onNext(it) }
                return false
            }
        })
    }

    /**
     * <h2>setupObserver</h2>
     * this method is for observing the response from api
     */
    private fun setupObserver() {
        viewModel.eventBookList.observe(this, Observer {
            when (it.getContentIfNotPending()?.first) {
                Constants.SUCCESS -> notifyAdapter()
                Constants.ERROR -> showError(it.getContent().second.toString())
                Constants.NO_INTERNET -> showInternetError()
            }
        })
    }

    private fun showInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.no_internet_error))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> viewModel.getBooksList() }
            .setOnCancelListener { viewModel.getBooksList() }
            .show()
    }

    private fun showError(error: String) {
        val snackBar: Snackbar = Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(this, R.color.snackbar_error_background_color)
        )
        snackBar.show()
    }

    /**
     * <h2>notifyAdapter</h2>
     * this is method for notifying   adapter
     */
    private fun notifyAdapter() {
        adapter.notifyDataSetChanged()
    }

}