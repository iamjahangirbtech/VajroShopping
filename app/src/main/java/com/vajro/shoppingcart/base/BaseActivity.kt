package com.vajro.shoppingcart.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vajro.shoppingcart.R

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: V

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = createBinding()
        this.binding = binding
        setContentView(binding.root)
    }

    abstract fun createBinding(): V


    protected open fun changeFragment(
        id: Int,
        fragment: Fragment,
        arg:Bundle? = null,
        isBackStack: Boolean = true,
        transition: Boolean = true
    ) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (isBackStack) transaction.addToBackStack(null)
        if (transition) transaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        if(arg !=null) fragment.arguments = arg
        transaction.add(id, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
    }

}