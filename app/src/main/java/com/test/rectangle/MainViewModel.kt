package com.test.rectangle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var fTopLeft: Point = Point()
    private var fBottomRight: Point = Point()
    private var sFopLeft: Point = Point()
    private var sBottomRight: Point = Point()
    private val _calculateRectangles = MutableSharedFlow<RectanglesPoints>()
    val calculateRectangles: Flow<RectanglesPoints> = _calculateRectangles

    private val _calculateRectanglesError = MutableSharedFlow<CoordinateErrorType>()
    val calculateRectanglesError: Flow<CoordinateErrorType> = _calculateRectanglesError

    fun setFTopLeftX(x: Int) {
        fTopLeft.x = x
    }

    fun setFTopLeftY(y: Int) {
        fTopLeft.y = y
    }

    fun setFBottomRightX(x: Int) {
        fBottomRight.x = x
    }

    fun setFBottomRightY(y: Int) {
        fBottomRight.y = y
    }

    fun setSTopLeftX(x: Int) {
        sFopLeft.x = x
    }

    fun setSTopLeftY(y: Int) {
        sFopLeft.y = y
    }

    fun setSBottomRightX(x: Int) {
        sBottomRight.x = x
    }

    fun setSBottomRightY(y: Int) {
        sBottomRight.y = y
    }

    fun generate() {
        val rectanglesPoints = RectanglesPoints(
            fTopLeft,
            fBottomRight,
            sFopLeft,
            sBottomRight
        )
        validateCoordinates(rectanglesPoints)

    }

    private fun validateCoordinates(rectanglesPoints: RectanglesPoints) {
        viewModelScope.launch {
            if (fTopLeft.x >= 0 &&
                fTopLeft.y >= 0 &&
                fBottomRight.x >= 0 &&
                fBottomRight.y >= 0 &&
                sFopLeft.x >= 0 &&
                sFopLeft.y >= 0 &&
                sBottomRight.x >= 0 &&
                sBottomRight.y >= 0
            ) {
                if (fTopLeft.x > fBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_X)
                } else if (fTopLeft.y > fBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_Y)
                } else if (sFopLeft.x > sBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_X)
                } else if (sFopLeft.y > sBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_Y)
                } else {
                    _calculateRectangles.emit(rectanglesPoints)
                }
            } else {
                _calculateRectanglesError.emit(CoordinateErrorType.UNKNOWN)
            }
        }
    }
}