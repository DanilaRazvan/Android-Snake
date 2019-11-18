package com.example.snake

import kotlin.random.Random

class Food(private val NUM_BLOCK_WIDE: Int, private val numBlocksHigh: Int) {
    private var _bobX = 0
    val bobX: Int
        get() {return this._bobX}

    private var _bobY = 0
    val bobY: Int
        get() {return this._bobY}


    fun spawnBob() {
        var ok = true

        _bobX = Random.nextInt(NUM_BLOCK_WIDE - 1) + 1
        _bobY = Random.nextInt(numBlocksHigh - 1) + 1

        for (i in 0..SnakeEngine.snake.snakeLength) {
            if (_bobX == SnakeEngine.snake.snakeXs[i] && _bobY == SnakeEngine.snake.snakeYs[i]) {
                ok = false
                break
            }
        }

        if (!ok) {
            spawnBob()
        }
    }
}