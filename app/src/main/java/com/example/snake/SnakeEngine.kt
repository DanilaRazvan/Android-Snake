package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.SurfaceView
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("ViewConstructor")
class SnakeEngine(ctxt: Context, size: Point) : SurfaceView(ctxt), Runnable {

    enum class Heading { UP, RIGHT, DOWN, LEFT }

    private var FPS = 10
    private var fpsUpdated = false

    private val MILLIS_PER_SECOND = 1000
    private val NUM_BLOCK_WIDE = 40

    private var thread: Thread? = null

    private val screenX = size.x
    private val screenY = size.y

    private var blockSize = screenX / NUM_BLOCK_WIDE
    private var numBlocksHigh = screenY / blockSize

    private var nextFrameTime: Long = 0

    private lateinit var canvas: Canvas
    private var surfaceHolder = holder
    private var paint: Paint = Paint()



    companion object {

        private lateinit var _snake: Snake
        val snake: Snake
            get() {
                return this._snake
            }

        private lateinit var _food: Food
        val food: Food
            get() {
                return this._food
            }

        var score = 0
    }


    private var isPLaying = true

    init {
        newGame()
    }

    override fun run() {

        while (isPLaying) {
            if (updateRequired()) {
                update()
                draw()
            }
        }
    }

    private fun newGame() {
        _snake = Snake(NUM_BLOCK_WIDE, numBlocksHigh)
        _food = Food(NUM_BLOCK_WIDE, numBlocksHigh)

        for (i in 0 until _snake.snakeXs.size) {
            _snake.snakeXs[i] = 0
            _snake.snakeYs[i] = 0
        }

        _snake.snakeLength = 5
        FPS = 10
        _snake.snakeXs[0] = NUM_BLOCK_WIDE / 2
        _snake.snakeYs[0] = numBlocksHigh / 2

        _food.spawnBob()

        score = 0
        nextFrameTime = System.currentTimeMillis()
    }


    private fun update() {
        if (_snake.snakeXs[0] == _food.bobX && _snake.snakeYs[0] == _food.bobY) {
            _snake.eatBob()
        }

        _snake.moveSnake()

        if (fpsUpdated) {
            fpsUpdated = !fpsUpdated
        }

        if (_snake.detectDeath()) {
            newGame()
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.rgb(0, 0, 0))
            paint.color = Color.WHITE

            paint.textSize = 90F
            canvas.drawText("Score:$score", 10F, 70F, paint)

            for (i in 0 until _snake.snakeLength) {

                if (i == 0) {
                    paint.color = Color.rgb(50, 230, 50)
                    canvas.drawCircle(
                        (_snake.snakeXs[i].toFloat() * blockSize + blockSize / 2),
                        (_snake.snakeYs[i].toFloat() * blockSize + blockSize / 2),
                        (blockSize / 2).toFloat(),
                        paint
                    )
                } else {
                    paint.color = Color.rgb(100, 190, 100)
                    canvas.drawCircle(
                        (_snake.snakeXs[i].toFloat() * blockSize + blockSize / 2),
                        (_snake.snakeYs[i].toFloat() * blockSize + blockSize / 2),
                        (blockSize / 2).toFloat(),
                        paint
                    )
                }
            }

            paint.color = Color.RED
            canvas.drawCircle(
                (_food.bobX.toFloat() * blockSize + blockSize / 2),
                (_food.bobY.toFloat() * blockSize + blockSize / 2),
                (blockSize / 2).toFloat(),
                paint
            )

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun updateRequired(): Boolean {

        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS

            return true
        }

        return false
    }

    fun moveFast() {
        val lastFPS = FPS
        FPS = 100

        Timer("SettingUp", false).schedule(500) {
            FPS = lastFPS
        }
    }


    fun pause() {
        isPLaying = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            //ERROR
        }
    }

    fun resume() {
        isPLaying = true
        thread = Thread(this)
        thread?.start()
    }
}