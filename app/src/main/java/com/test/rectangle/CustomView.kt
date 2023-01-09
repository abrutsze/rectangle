package com.test.rectangle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.widget.Toast

class CustomView(
    context: Context?,
    rectanglesPoints: RectanglesPoints
) : View(context) {
    private val rectangle1: Rect
    private val rectangle2: Rect
    private val paint1: Paint
    private val paint2: Paint

    init {
        /* example point
            val l1 = Point(0, 0)
            val r1 = Point(100, 100)
            val l2 = Point(50, 50)
            val r2 = Point(150, 150)
        */
        val l1 = rectanglesPoints.topLeft1
        val r1 = rectanglesPoints.bottomRight1
        val l2 = rectanglesPoints.topLeft2
        val r2 = rectanglesPoints.bottomRight2
        val rectangleFirst = Rectangle(l1, r1)
        val rectangleSecond = Rectangle(l2, r2)

        val isOverlap = rectangleFirst.isOverlapping(rectangleSecond)
        val overlappingArea = rectangleFirst.overlappingArea(l2, r2)

        if (isOverlap) {
            Toast.makeText(context, "Rectangles Overlap and area is $overlappingArea IoU, == ${overlappingArea*100} %", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Rectangles Don't Overlap", Toast.LENGTH_LONG).show()
        }

        rectangle1 = Rect(l1.x, l1.y, r1.x, r1.y)
        rectangle2 = Rect(l2.x, l2.y, r2.x, r2.y)


        paint1 = Paint()
        paint1.color = Color.BLUE
        paint2 = Paint()
        paint2.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(rectangle1, paint1)
        canvas.drawRect(rectangle2, paint2)
    }

}