package com.the_b.moviecatalogue.ui.baseView

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding>(val bindingFactory : (LayoutInflater) -> B): AppCompatActivity() {

    private var binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binds: B
        get() = binding as B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(requireNotNull(binding).root)

        setupView(binds)
    }

    abstract fun setupView(binds: B)

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}