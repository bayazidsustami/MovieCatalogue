package com.the_b.moviecatalogue.ui.baseView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B): Fragment() {

    private var _binding: B? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingFactory(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(binding)

    }

    abstract fun setupView(binds: B?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}