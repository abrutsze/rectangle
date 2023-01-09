package com.test.rectangle

import org.junit.Assert.*
import org.junit.Test

class RectangleUnitTest {

    @Test
    fun givenTwoOverlappingRectangles_whenisOverlappingCalled_shouldReturnTrue() {
        var rectangle1 = Rectangle(Point(0, 310), Point(900, 350))
        var rectangle2 = Rectangle(Point(250, 0), Point(600, 320))
        assertTrue(rectangle1.isOverlapping(rectangle2))
        rectangle1 = Rectangle(Point(-5, -2), Point(2, 3))
        rectangle2 = Rectangle(Point(-2, -1), Point(5, 2))
        assertTrue(rectangle1.isOverlapping(rectangle2))
        rectangle1 = Rectangle(Point(-5, 1), Point(2, 4))
        rectangle2 = Rectangle(Point(-2, -2), Point(5, 5))
        assertTrue(rectangle1.isOverlapping(rectangle2))
    }

    @Test
    fun givenTwoNonOverlappingRectangles_whenisOverlappingCalled_shouldReturnFalse() {
        var rectangle1 = Rectangle(Point(-5, 1), Point(-3, 4))
        var rectangle2 = Rectangle(Point(-2, -2), Point(5, 5))
        assertFalse(rectangle1.isOverlapping(rectangle2))
        rectangle1 = Rectangle(Point(-5, 1), Point(3, 4))
        rectangle2 = Rectangle(Point(-2, -2), Point(5, -1))
        assertFalse(rectangle1.isOverlapping(rectangle2))
        rectangle1 = Rectangle(Point(-2, 1), Point(0, 3))
        rectangle2 = Rectangle(Point(3, 1), Point(5, 4))
        assertFalse(rectangle1.isOverlapping(rectangle2))
    }

    @Test
    fun givenTwoOverlappingRectanglesWhenAreaIsNotEquals() {
        val rectangle1 = Rectangle(Point(0, 0), Point(100, 100))
        val rectangle2 = Rectangle(Point(50, 50), Point(150, 150))
        assertTrue(rectangle1.isOverlapping(rectangle2))
        assertEquals(
            "0.1429  Overlapping",
             0.1429,
            rectangle1.overlappingArea(rectangle2.l1, rectangle2.r1),
            0.0
        )
    }

    @Test
    fun givenTwoNonOverlappingRectanglesArea() {
        val rectangle1 = Rectangle(Point(0, 0), Point(100, 100))
        val rectangle2 = Rectangle(Point(101, 101), Point(150, 150))
        assertFalse(rectangle1.isOverlapping(rectangle2))
        assertEquals(
            "0.0% Overlapping",
            0.0,
            rectangle1.overlappingArea(rectangle2.l1, rectangle2.r1),
            0.0
        )

    }

    @Test
    fun givenTwoOverlappingRectanglesAreaWithAllOverlapping() {
        val rectangle1 = Rectangle(Point(0, 0), Point(100, 100))
        val rectangle2 = Rectangle(Point(0, 0), Point(100, 100))
        assertTrue(rectangle1.isOverlapping(rectangle2))
        assertEquals(
            "1.0 Overlapping",
            1.0,
            rectangle1.overlappingArea(rectangle2.l1, rectangle2.r1),
            0.0
        )
    }
}