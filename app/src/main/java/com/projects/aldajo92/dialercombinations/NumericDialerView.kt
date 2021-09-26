package com.projects.aldajo92.dialercombinations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan
import kotlin.math.min

class NumericDialerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private var listener: NumericDialListener? = null

    private var currentWidth = 0
    private var currentHeight = 0
    private var currentRadius = 0f

    private var halfWidth = 0f
    private var halfHeight = 0f

    private var ratioInnerCircles = 0.15f

    private var padsList = listOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    ).reversed()

    private val degreeSteps = CIRCLE_DEGREE / (padsList.size + HIDDEN_CIRCLES)

    private var rotorAngle = 0f
    private var startFi = 0f

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

        canvas?.let {
            val textAdjustHeight = ((canvasPaint.descent() + canvasPaint.ascent()) / 2)

            if (rotorAngle != 0f) {
                canvas.rotate(rotorAngle, halfWidth, halfHeight)
            }

            it.drawCircle(halfWidth, halfHeight, currentRadius, canvasPaint)
            it.translate(halfWidth, halfHeight)

            for (i in 0..(padsList.size + HIDDEN_CIRCLES)) {
                if (i < padsList.size) {

                    it.drawCircle(
                        0f,
                        currentRadius * 0.75f,
                        currentRadius * ratioInnerCircles,
                        canvasPaint
                    )

                    it.translate(0f, currentRadius * 0.75f)
                    it.rotate(-i * degreeSteps)
                    it.drawText(
                        padsList[i].toString(),
                        0f,
                        -textAdjustHeight,
                        canvasPaint
                    )
                    it.rotate(i * degreeSteps)
                    it.translate(0f, -currentRadius * 0.75f)

                    it.rotate(CIRCLE_DEGREE / (padsList.size + HIDDEN_CIRCLES))
                }
            }

            it.rotate(-padsList.size * CIRCLE_DEGREE / (padsList.size + HIDDEN_CIRCLES))
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x0 = (currentWidth / 2).toFloat()
        val y0 = (currentHeight / 2).toFloat()

        val x1 = event.x
        val y1 = event.y
        val x = x1 - x0
        val y = y1 - y0

        val tanFi = (y / x).toDouble()
        var fi = Math.toDegrees(atan(tanFi)).toFloat().fixAngleDegreeAxis(x)

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                rotorAngle = (fi - startFi) % CIRCLE_DEGREE
                invalidate()
                return true
            }
            MotionEvent.ACTION_DOWN -> {
                startFi = fi
                return true
            }
            MotionEvent.ACTION_UP -> {
                val rotation = if (rotorAngle < 0) CIRCLE_DEGREE + rotorAngle else rotorAngle
                val number = (rotation / degreeSteps).toInt()
                if (number in 1..10) {
                    val triggerNumber = if (number == 10) 0 else number
                    listener?.onDialNumberListener(triggerNumber)
                }
                rotorAngle = 0f
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setNumericDialListener(listener: NumericDialListener) {
        this.listener = listener
    }

    companion object {
        const val HIDDEN_CIRCLES = 3
        const val CIRCLE_DEGREE = 360f
    }

}

fun Float.fixAngleDegreeAxis(x: Float) = if (x < 0) this + 180 else this

interface NumericDialListener {
    fun onDialNumberListener(number: Int)
}