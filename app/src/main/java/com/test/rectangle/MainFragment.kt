package com.test.rectangle

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.test.rectangle.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var width: Int = 0
    private var height: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getScreenSize()
        binding.generate.setOnClickListener {
            viewModel.generate()
        }
        initDefaultData()
        lifecycleScope.launchWhenResumed {
            viewModel.calculateRectangles.collectLatest {
                (activity as? MainActivity)?.navigate(DrowFragment.newInstance(it))
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.calculateRectanglesError.collectLatest {
                with(binding) {
                    when (it) {
                        CoordinateErrorType.ERROR_FIRST_X -> {
                            fTopLeftXLayout.error = getString(R.string.error_x_x_less)
                            fBottomRightXLayout.error = getString(R.string.error_x_x_more)
                        }
                        CoordinateErrorType.ERROR_FIRST_Y -> {
                            fTopLeftYLayout.error = getString(R.string.error_y_y_less)
                            fBottomRightYLayout.error = getString(R.string.error_y_y_more)
                        }
                        CoordinateErrorType.ERROR_SECOND_X -> {
                            sTopLeftYLayout.error = getString(R.string.error_x_x_less)
                            sBottomRightYLayout.error = getString(R.string.error_x_x_more)
                        }
                        CoordinateErrorType.ERROR_SECOND_Y -> {
                            sTopLeftYLayout.error = getString(R.string.error_y_y_less)
                            sBottomRightYLayout.error = getString(R.string.error_y_y_more)
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                "Incorrect input coordinate ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        with(binding) {
            fTopLeftX.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(fTopLeftXLayout)
                if (isValid) {
                    viewModel.setFTopLeftX(text.toString().toInt())
                }
            }
            fTopLeftY.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(fTopLeftYLayout)
                if (isValid) {
                    viewModel.setFTopLeftY(text.toString().toInt())
                }
            }
            fBottomXRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(fBottomRightXLayout)
                if (isValid) {
                    viewModel.setFBottomRightX(text.toString().toInt())
                }
            }
            fBottomYRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(fBottomRightYLayout)
                if (isValid) {
                    viewModel.setFBottomRightY(text.toString().toInt())
                }
            }
            sTopLeftX.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(sTopLeftXLayout)
                if (isValid) {
                    viewModel.setSTopLeftX(text.toString().toInt())
                }
            }
            sTopLeftY.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(sTopLeftYLayout)
                if (isValid) {
                    viewModel.setSTopLeftY(text.toString().toInt())
                }
            }
            sBottomXRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(sBottomRightXLayout)
                if (isValid) {
                    viewModel.setSBottomRightX(text.toString().toInt())
                }
            }
            sBottomYRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(sBottomRightYLayout)
                if (isValid) {
                    viewModel.setSBottomRightY(text.toString().toInt())
                }
            }
        }
    }

    private fun initDefaultData() {
        with(binding) {
            fTopLeftX.setText("0")
            viewModel.setFTopLeftX(0)

            fTopLeftY.setText("310")
            viewModel.setFTopLeftY(310)

            fBottomXRight.setText("900")
            viewModel.setFBottomRightX(900)

            fBottomYRight.setText("350")
            viewModel.setFBottomRightY(350)

            sTopLeftX.setText("250")
            viewModel.setSTopLeftX(250)

            sTopLeftY.setText("0")
            viewModel.setSTopLeftY(0)

            sBottomXRight.setText("600")
            viewModel.setSBottomRightX(600)

            sBottomYRight.setText("320")
            viewModel.setSBottomRightY(320)
        }
    }

    private fun getScreenSize() {
        val metrics: DisplayMetrics = this.resources.displayMetrics
        width = metrics.widthPixels
        height = metrics.heightPixels
    }

    private fun CharSequence?.validateScreenX(textInputLayout: TextInputLayout): Boolean {
        return if (!isNullOrEmpty()) {
            val validate = width > toString().toInt()
            if (!validate) {
                textInputLayout.error = "X must be less than $width"
            } else {
                textInputLayout.error = ""
            }
            validate
        } else {
            textInputLayout.error = "X not must be empty"
            false
        }
    }

    private fun CharSequence?.validateScreenY(textInputLayout: TextInputLayout): Boolean {
        return if (!isNullOrEmpty()) {
            val validate = height > toString().toInt()
            if (!validate) {
                textInputLayout.error = "Y must be less than $height"
            } else {
                textInputLayout.error = ""
            }
            validate
        } else {
            textInputLayout.error = "Y not must be empty"
            false
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}