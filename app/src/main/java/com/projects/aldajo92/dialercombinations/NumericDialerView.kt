package com.projects.aldajo92.dialercombinations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.min
import kotlin.math.roundToInt

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

    private var padsList = listOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    ).reversed()

    // User rotation
    private var rotorAngle = 0f
    private var lastFi = 0.0
    private var startFi = 0.0

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
            val hiddenNumbers = 3
            val textSize = currentRadius * 0.1f // TODO: Convert it as variable
            val textAdjustHeight = ((canvasPaint.descent() + canvasPaint.ascent()) / 2)

            if (rotorAngle != 0f) {
                canvas.rotate(rotorAngle, halfWidth, halfHeight)
            }

//            it.drawLine(0f, halfHeight, currentWidth.toFloat(), halfHeight, canvasPaint)
//            it.drawLine(halfWidth, 0f, halfWidth, currentHeight.toFloat(), canvasPaint)


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

//            it.drawText(
//                "1",
//                0f,
//                -textAdjustHeight,
//                canvasPaint
//            )
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
        var fi = Math.toDegrees(atan(tanFi))

        if (x < 0) {
            fi += 180
        }

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                rotorAngle = (fi - startFi).toFloat()
//                rotorAngle += abs(fi - lastFi).toFloat() + 0.25f
                rotorAngle %= 360
//                lastFi = fi
                Log.i("alejandrogomez", "x:$x, y:$y, Fi: $fi")
                invalidate()
                return true
            }
            MotionEvent.ACTION_DOWN -> {
                lastFi = fi
                startFi = fi
                Log.i("alejandrogomez", "Start Fi: $fi")
                return true
            }
            MotionEvent.ACTION_UP -> {

                if (abs(fi-ACTIVATION_ANGLE) < DELTA_ACTIVATION){
                    Log.i("alejandroGomez", "dial activated")
                }

                val angle = abs(rotorAngle % 360)
                var number = (angle - 20).roundToInt() / 30
                if (number > 0) {
//                    if (number == 10) {
//                        number = 0
//                    }
//                    fireDialListenerEvent(number)
                }
                rotorAngle = 0f
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        const val ACTIVATION_ANGLE = 30
        const val DELTA_ACTIVATION = 5
    }

}