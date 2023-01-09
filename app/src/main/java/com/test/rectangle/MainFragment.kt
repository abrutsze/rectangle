package com.test.rectangle

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.test.rectangle.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : FragmentBaseNCMVVM<MainViewModel>() {

    private var isValidInputData: Boolean = true
    private lateinit var binding: FragmentMainBinding
    override val viewModel: MainViewModel by viewModels()
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
            //if (isValidInputData)
                viewModel.generate()

        }
        initDefaultData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.calculateRectangles
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    val directions = MainFragmentDirections.actionMainFragmentToDrawFragment(it)
                    navigateFragment(directions)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.calculateRectanglesError
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
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

                            CoordinateErrorType.ERROR_FIRST_X_EQUAL -> {
                                fTopLeftXLayout.error = getString(R.string.error_x_x_equal)
                                fBottomRightXLayout.error = getString(R.string.error_x_x_equal)
                            }
                            CoordinateErrorType.ERROR_FIRST_Y_EQUAL -> {
                                fTopLeftYLayout.error = getString(R.string.error_y_y_equal)
                                fBottomRightYLayout.error = getString(R.string.error_y_y_equal)
                            }
                            CoordinateErrorType.ERROR_SECOND_X_EQUAL -> {
                                sTopLeftYLayout.error = getString(R.string.error_x_x_equal)
                                sBottomRightYLayout.error = getString(R.string.error_x_x_equal)
                            }
                            CoordinateErrorType.ERROR_SECOND_Y_EQUAL -> {
                                sTopLeftYLayout.error = getString(R.string.error_y_y_equal)
                                sBottomRightYLayout.error = getString(R.string.error_y_y_equal)
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
                viewModel.validFTopLeftX = isValid
            }
            fTopLeftY.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(fTopLeftYLayout)
                if (isValid) {
                    viewModel.setFTopLeftY(text.toString().toInt())
                }
                viewModel.validFTopLeftY = isValid
            }
            fBottomXRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(fBottomRightXLayout)
                if (isValid) {
                    viewModel.setFBottomRightX(text.toString().toInt())
                }
                viewModel.validFBottomRightY = isValid
            }
            fBottomYRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(fBottomRightYLayout)
                if (isValid) {
                    viewModel.setFBottomRightY(text.toString().toInt())
                }
                viewModel.validFBottomRightY = isValid
            }
            sTopLeftX.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(sTopLeftXLayout)
                if (isValid) {
                    viewModel.setSTopLeftX(text.toString().toInt())
                }
                viewModel.validSTopLeftX = isValid
            }
            sTopLeftY.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(sTopLeftYLayout)
                if (isValid) {
                    viewModel.setSTopLeftY(text.toString().toInt())
                }
                viewModel.validSTopLeftY = isValid
            }
            sBottomXRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenX(sBottomRightXLayout)
                if (isValid) {
                    viewModel.setSBottomRightX(text.toString().toInt())
                }
                viewModel.validSBottomRightX= isValid
            }
            sBottomYRight.doOnTextChanged { text, _, _, _ ->
                val isValid = text.validateScreenY(sBottomRightYLayout)
                if (isValid) {
                    viewModel.setSBottomRightY(text.toString().toInt())
                }
                viewModel.validSBottomRightY = isValid
            }
        }
    }

    private fun initDefaultData() {
        with(binding) {
            fTopLeftX.setText("0")
            viewModel.setFTopLeftX(0)

            fTopLeftY.setText("0")
            viewModel.setFTopLeftY(0)

            fBottomXRight.setText("100")
            viewModel.setFBottomRightX(100)

            fBottomYRight.setText("100")
            viewModel.setFBottomRightY(100)

            sTopLeftX.setText("50")
            viewModel.setSTopLeftX(50)

            sTopLeftY.setText("50")
            viewModel.setSTopLeftY(50)

            sBottomXRight.setText("150")
            viewModel.setSBottomRightX(150)

            sBottomYRight.setText("150")
            viewModel.setSBottomRightY(150)
        }
    }

    private fun getScreenSize() {
        val metrics: DisplayMetrics = this.resources.displayMetrics
        width = metrics.widthPixels
        height = metrics.heightPixels
        viewModel.height = height
        viewModel.width = width
    }

    private fun CharSequence?.validateScreenX(textInputLayout: TextInputLayout): Boolean {
        return if (!isNullOrEmpty()) {
            val validate = width > toString().toInt()
            if (!validate) {
                textInputLayout.error = "X must be less than $width"
            } else {
                textInputLayout.error = ""
            }
            isValidInputData = validate
            validate
        } else {
            textInputLayout.error = "X not must be empty"
            isValidInputData = false
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
            isValidInputData = validate
            validate
        } else {
            textInputLayout.error = "Y not must be empty"
            isValidInputData = false
            false
        }
    }

}