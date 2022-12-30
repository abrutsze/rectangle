package com.test.rectangle

import kotlin.math.abs

class Rectangle(var topLeft: Point, var bottomRight: Point) {

    fun isOverlapping(other: Rectangle): Boolean {

        if (bottomRight.y < other.topLeft.y || topLeft.y > other.bottomRight.y) {
            return false
        }

        return !(bottomRight.x < other.topLeft.x || topLeft.x > other.bottomRight.x)
    }

    fun overlappingArea(l2: Point, r2: Point): Int {
        // if rectangle has area 0, no overlap
        val area1 = abs(topLeft.x - bottomRight.x) * abs(topLeft.y - bottomRight.y)
        val area2 = abs(l2.x - r2.x) * abs(l2.y - r2.y)
        val xDist = (bottomRight.x.coerceAtMost(r2.x) - topLeft.x.coerceAtLeast(l2.x))
        val yDist = (bottomRight.y.coerceAtMost(r2.y)
                - topLeft.y.coerceAtLeast(l2.y))
        var areaI = 0
        if (xDist > 0 && yDist > 0) {
            areaI = xDist * yDist;
        }

        return (area1 + area2 - areaI)
    }
}