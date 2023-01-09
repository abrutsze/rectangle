package com.test.rectangle

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class Rectangle(var l1: Point, var r1: Point) {

    fun isOverlapping(other: Rectangle): Boolean {

        if (r1.y < other.l1.y || l1.y > other.r1.y) {
            return false
        }

        return !(r1.x < other.l1.x || l1.x > other.r1.x)
    }

    fun overlappingArea(l2: Point, r2: Point): Double {
        // if rectangle has area 0, no overlap
        val area1 = abs(l1.x - r1.x) * abs(l1.y - r1.y)
        val area2 = abs(l2.x - r2.x) * abs(l2.y - r2.y)
        val xDist = (r1.x.coerceAtMost(r2.x) - l1.x.coerceAtLeast(l2.x))
        val yDist = (r1.y.coerceAtMost(r2.y)
                - l1.y.coerceAtLeast(l2.y))
        var areaI = 0.0
        if (xDist > 0 && yDist > 0) {
            areaI = (xDist * yDist).toDouble()
        }

        val areaPx = (area1 + area2 - areaI)
        return (areaI / areaPx).round(4)
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}