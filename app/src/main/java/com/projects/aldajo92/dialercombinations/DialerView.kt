package com.projects.aldajo92.dialercombinations

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt


class DialerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private val rotorDrawable by lazy {
        ResourcesCompat.getDrawable(context.resources, R.drawable.dialer, null)
    }

    private var rotorAngle = 0f
    private var lastFi = 0.0

    private var currentWidth = 0
    private var currentHeight = 0
    private var currentRadius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentWidth = w
        currentHeight = h
        currentRadius = min(w, h) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val availableWidth = currentWidth
        val availableHeight = currentHeight
        val x = availableWidth / 2
        val y = availableHeight / 2
        canvas.save()
        rotorDrawable?.setBounds(
            0, 0, rotorDrawable!!.intrinsicWidth,
            rotorDrawable!!.intrinsicHeight
        )
        if (rotorAngle != 0f) {
            canvas.rotate(rotorAngle, x.toFloat(), y.toFloat())
        }
        rotorDrawable!!.draw(canvas)
        canvas.restore()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x0 = (currentWidth / 2).toFloat()
        val y0 = (currentHeight / 2).toFloat()
        val x1 = event.x
        val y1 = event.y
        val x = x0 - x1
        val y = y0 - y1
        val r = sqrt((x * x + y * y).toDouble())
        val sinfi = y / r
        var fi = Math.toDegrees(asin(sinfi))

        if (x1 > x0 && y0 > y1) {
            fi = 180 - fi;
        } else if (x1 > x0 && y1 > y0) {
            fi = 180 - fi;
        } else if (x0 > x1 && y1 > y0) {
            fi += 360;
        }

        when (event.action) {
            MotionEvent.ACTION_MOVE -> if (r > r1 && r < r2) {
                rotorAngle += abs(fi - lastFi).toFloat() + 0.25f
                rotorAngle %= 360
                lastFi = fi
                invalidate()
                return true
            }
            MotionEvent.ACTION_DOWN -> {
                rotorAngle = 0f
                lastFi = fi
                return true
            }
            MotionEvent.ACTION_UP -> {
                val angle = rotorAngle % 360
                var number = (angle - 20).roundToInt() / 30
                if (number > 0) {
                    if (number == 10) {
                        number = 0
                    }
                    fireDialListenerEvent(number)
                }
                rotorAngle = 0f
                post {
                    val fromDegrees: Float = angle
                    val anim: Animation = RotateAnimation(
                        fromDegrees,
                        0f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    anim.interpolator = AnimationUtils.loadInterpolator(
                        context,
                        android.R.anim.decelerate_interpolator
                    )
                    anim.duration = (angle * 5).toLong()
                    startAnimation(anim)
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

//    fun isOnRegion(x0: Float, y0: Float): String {
//
//    }

    private fun fireDialListenerEvent(number: Int) {
        // TODO fire dial event
    }

    companion object {
        private const val r1 = 200
        private const val r2 = 400
    }

}