package com.didik.moflix.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<V : ViewBinding> : AppCompatActivity() {

    private var _binding: V? = null
    protected val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initViewBinding(layoutInflater)
        setContentView(binding.root)
        renderView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    abstract fun initViewBinding(inflater: LayoutInflater): V

    abstract fun renderView()
}