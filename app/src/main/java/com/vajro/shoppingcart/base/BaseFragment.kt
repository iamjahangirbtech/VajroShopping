package com.vajro.shoppingcart.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.vajro.shoppingcart.R
import com.vajro.shoppingcart.utils.SoftKeyboardUtils

abstract class BaseFragment<V : ViewBinding> : Fragment() {
    protected var binding: V? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)
        this.binding = binding
        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    abstract fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V


    open fun changeFragment(
        id: Int,
        fragment: Fragment,
        arg: Bundle? =null,
        isBackStack: Boolean = true,
        transition: Boolean = true
    ) {
        SoftKeyboardUtils.hideKeyboard( requireActivity())
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()
        if (isBackStack) transaction.addToBackStack(null)
        if (transition) transaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        if(arg !=null) fragment.arguments = arg
        transaction.replace(id, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
    }

    open fun back() {
        if(activity != null) {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }
    open fun backAll() {
        if(activity != null) {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            for (i in 0 until fm.getBackStackEntryCount()) {
                fm.popBackStack()
            }
        }

    }
}