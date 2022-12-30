package com.test.rectangle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RectanglesPoints(
    var topLeft1: Point,
    var bottomRight1: Point,
    var topLeft2: Point,
    var bottomRight2: Point
): Parcelable