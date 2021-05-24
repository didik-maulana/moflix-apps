package com.didik.moflix.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
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

    protected fun toast(
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(this, message, duration).show()
    }
}