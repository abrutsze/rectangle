package com.test.rectangle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var fTopLeft: Point = Point()
    private var fBottomRight: Point = Point()
    private var sFopLeft: Point = Point()
    private var sBottomRight: Point = Point()
    var validFTopLeftX: Boolean = true
    var validFTopLeftY: Boolean = true
    var validFBottomRightX: Boolean = true
    var validFBottomRightY: Boolean = true
    var validSTopLeftX: Boolean = true
    var validSTopLeftY: Boolean = true
    var validSBottomRightX: Boolean = true
    var validSBottomRightY: Boolean = true

    var width: Int = 0
    var height: Int = 0
    private val _calculateRectangles = MutableSharedFlow<RectanglesPoints>()
    val calculateRectangles: SharedFlow<RectanglesPoints> = _calculateRectangles

    private val _calculateRectanglesError = MutableSharedFlow<CoordinateErrorType>()
    val calculateRectanglesError: SharedFlow<CoordinateErrorType> = _calculateRectanglesError

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
            if (fTopLeft.x >= 0 && validFTopLeftX &&
                fTopLeft.y >= 0 && validFTopLeftY &&
                fBottomRight.x >= 0 && validFBottomRightX &&
                fBottomRight.y >= 0 && validFBottomRightY &&
                sFopLeft.x >= 0 && validSTopLeftX &&
                sFopLeft.y >= 0 && validSTopLeftY &&
                sBottomRight.x >= 0 && validSBottomRightX &&
                sBottomRight.y >= 0 && validSBottomRightY
            ) {
                if (fTopLeft.x > fBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_X)
                } else if (fTopLeft.y > fBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_Y)
                } else if (sFopLeft.x > sBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_X)
                } else if (sFopLeft.y > sBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_Y)
                }else if (fTopLeft.x == fBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_X_EQUAL)
                } else if (fTopLeft.y == fBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_FIRST_Y_EQUAL)
                } else if (sFopLeft.x == sBottomRight.x) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_X_EQUAL)
                } else if (sFopLeft.y == sBottomRight.y) {
                    _calculateRectanglesError.emit(CoordinateErrorType.ERROR_SECOND_Y_EQUAL)
                } else {
                    _calculateRectangles.emit(rectanglesPoints)
                }
            } else {
                _calculateRectanglesError.emit(CoordinateErrorType.UNKNOWN)
            }
        }
    }
}