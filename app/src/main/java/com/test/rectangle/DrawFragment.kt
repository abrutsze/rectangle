package com.test.rectangle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.test.rectangle.databinding.FragmentDrawBinding

class DrawFragment : FragmentBaseNCMVVM<ViewModel>() {

    private lateinit var binding: FragmentDrawBinding
    private val args: DrawFragmentArgs by navArgs()
    override val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentDrawBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args?.points?.let {
            binding.container.addView(CustomView(context, it))
        }
    }

}