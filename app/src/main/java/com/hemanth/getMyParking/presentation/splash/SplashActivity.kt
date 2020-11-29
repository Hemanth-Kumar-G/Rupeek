package com.hemanth.getMyParking.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.hemanth.getMyParking.R
import com.hemanth.getMyParking.base.BaseActivity
import com.hemanth.getMyParking.databinding.ActivitySplashBinding
import com.hemanth.getMyParking.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


/**
 *<h1>SplashActivity</h1>
 *<p>this is class for splash wait for 4 sec</p>
 * @author : Hemanth
 * @version : 1.0
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel.initStart()
        setupObserver()
        runAnimation()
    }

    /**
     * <h2>setupObserver</h2>
     * <p>this is the method for observing the duration</p>
     */
    private fun setupObserver() {
        viewModel.routeEvent.observe(this, Observer {
            if (it) {
                launchHomeScreen()
            }
        })
    }

    private fun launchHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun runAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.tvSplashTitle.startAnimation(animation)
    }
}