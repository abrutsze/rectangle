package com.test.rectangle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.rectangle.databinding.FragmentDrowBinding

private const val ARG_RECTANGLE_POINTS = "points"

class DrowFragment : Fragment() {

    private var rectanglesPoints: RectanglesPoints? = null
    private lateinit var binding: FragmentDrowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rectanglesPoints = it.getParcelable(ARG_RECTANGLE_POINTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentDrowBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rectanglesPoints?.let {
            binding.container.addView(CustomView(context, it))
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(rectanglesPoints: RectanglesPoints) =
            DrowFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_RECTANGLE_POINTS, rectanglesPoints)
                }
            }
    }
}