package com.projects.aldajo92.dialercombinations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class NumericDialerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private var currentWidth = 0
    private var currentHeight = 0
    private var currentRadius = 0f


    private var halfWidth = 0f
    private var halfHeight = 0f

    private var ratioInnerCircles = 0.15f

    private var rotorAngle = 0f

    private var padsList = listOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    ).reversed()

    private var centerNumbersPositions = mutableMapOf<Int, Point>()

    private val canvasPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.STROKE
            textSize = currentRadius * 0.1f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.i("alejandrogomez", "$currentWidth $currentHeight $currentRadius")

        canvas?.let {
            val hiddenNumbers = 3
            val textSize = currentRadius * 0.1f // TODO: Convert it as variable
            val textAdjustHeight = ((canvasPaint.descent() + canvasPaint.ascent()) / 2)

            if (rotorAngle != 0f) {
                canvas.rotate(rotorAngle, halfWidth, halfHeight)
            }

            it.drawLine(0f, halfHeight, currentWidth.toFloat(), halfHeight, canvasPaint)
            it.drawLine(halfWidth, 0f, halfWidth, currentHeight.toFloat(), canvasPaint)

//            it.drawText(
//                "0",
//                halfWidth,
//                halfHeight - textAdjustHeight,
//                canvasPaint
//            )
            it.drawCircle(halfWidth, halfHeight, currentRadius, canvasPaint)
            it.translate(halfWidth, halfHeight)

            val degreeSteps = 360f / (padsList.size + hiddenNumbers)
            for (i in 0..(padsList.size + hiddenNumbers)) {
                if (i < padsList.size) {
                    it.drawCircle(
                        0f,
                        currentRadius * 0.7f,
                        currentRadius * ratioInnerCircles,
                        canvasPaint
                    )

                    it.translate(0f, currentRadius * 0.7f)
                    it.rotate(-i * degreeSteps)
                    it.drawText(
                        padsList[i].toString(),
                        0f,
                        -textAdjustHeight,
                        canvasPaint
                    )
                    it.rotate(i * degreeSteps)
                    it.translate(0f, -currentRadius * 0.7f)

                    it.rotate(360f / (padsList.size + hiddenNumbers))
                }
            }

            it.rotate(-padsList.size * 360f / (padsList.size + hiddenNumbers))

            it.drawText(
                "1",
                0f,
                -textAdjustHeight,
                canvasPaint
            )
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentWidth = w
        currentHeight = h
        currentRadius = min(w, h) / 2f
        halfWidth = currentWidth / 2f
        halfHeight = currentHeight / 2f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_MOVE -> {
                val x1 = event.x - halfWidth
                val y1 = event.y - halfHeight
                Log.i("alejandrogomez", "$x1, $y1")
                return true
            }
            MotionEvent.ACTION_DOWN -> {
//                val x1 = event.x
//                val y1 = event.y
//                Log.i("alejandrogomez", "$x1, $y1")
                return true
            }
        }
        return super.onTouchEvent(event)
    }

}