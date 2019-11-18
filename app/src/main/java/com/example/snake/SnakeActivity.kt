package com.example.snake

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.atan2

class SnakeActivity : Activity(), GestureDetector.OnGestureListener {

    private lateinit var snakeEngine: SnakeEngine

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display = windowManager.defaultDisplay

        val size = Point()
        display.getSize(size)


        gestureDetector = GestureDetector(this, this)

        snakeEngine = SnakeEngine(this, size)

        setContentView(snakeEngine)
    }

    override fun onResume() {
        super.onResume()

        snakeEngine.resume()
    }

    override fun onPause() {
        super.onPause()

        snakeEngine.pause()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun getSlope(x1: Float, y1: Float, x2: Float, y2: Float): Int {

        val angle = Math.toDegrees(atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()))

        if (angle > 45 && angle <= 135)
        // top
            return 1

        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
        // left
            return 2

        if (angle < -45 && angle >= -135)
        // down
            return 3

        if (angle > -45 && angle <= 45)
        // right
            return 4

        return 0
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        SnakeEngine.snake.eatBob()
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, x: Float, y: Float): Boolean {
        val dir = SnakeEngine.snake.heading

        when (getSlope(e1!!.x, e1.y, e2!!.x, e2.y)) {
            1 -> {  //up
                when (dir) {
//                    SnakeEngine.Heading.DOWN -> Toast.makeText(this, "UNKNOWN INPUT", Toast.LENGTH_LONG).show()
                    SnakeEngine.Heading.UP -> snakeEngine.moveFast()
                    else -> SnakeEngine.snake.setDirection(1)
                }
            }

            2 -> {  //left
                when (dir) {
//                    SnakeEngine.Heading.RIGHT -> Toast.makeText(this, "UNKNOWN INPUT", Toast.LENGTH_LONG).show()
                    SnakeEngine.Heading.LEFT -> snakeEngine.moveFast()
                    else -> SnakeEngine.snake.setDirection(2)
                }
            }

            3 -> {  //down
                when (dir) {
//                    SnakeEngine.Heading.UP -> Toast.makeText(this, "UNKNOWN INPUT", Toast.LENGTH_LONG).show()
                    SnakeEngine.Heading.DOWN -> snakeEngine.moveFast()
                    else -> SnakeEngine.snake.setDirection(3)
                }
            }

            4 -> {  //right
                when (dir) {
//                    SnakeEngine.Heading.LEFT -> Toast.makeText(this, "UNKNOWN INPUT", Toast.LENGTH_LONG).show()
                    SnakeEngine.Heading.RIGHT -> snakeEngine.moveFast()
                    else -> SnakeEngine.snake.setDirection(4)
                }
            }

            else -> {
//                Toast.makeText(this, "UNKNOWN INPUT", Toast.LENGTH_LONG).show()
            }
        }

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

}
