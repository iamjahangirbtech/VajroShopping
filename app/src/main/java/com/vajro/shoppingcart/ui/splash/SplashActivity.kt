package com.vajro.shoppingcart.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.vajro.shoppingcart.R
import com.vajro.shoppingcart.databinding.ActivitySplashBinding
import com.vajro.shoppingcart.base.BaseActivity
import com.vajro.shoppingcart.ui.Main.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val splashTimeOut = 2500
        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
    }

    override fun createBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)



}