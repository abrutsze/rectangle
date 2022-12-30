package com.test.rectangle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    var x: Int = -1,
    var y: Int = -1
): Parcelable