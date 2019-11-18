package com.example.snake

class Snake(private val NUM_BLOCK_WIDE: Int, private val numBlocksHigh: Int) {

    private var _snakeLength = 1
    var snakeLength: Int
        get() {
            return this._snakeLength
        }
        set(value) {
            this._snakeLength = value
        }

    private var _snakeXs: IntArray = IntArray(NUM_BLOCK_WIDE * numBlocksHigh)
    val snakeXs: IntArray
        get() {
            return this._snakeXs
        }

    private var _snakeYs: IntArray = IntArray(NUM_BLOCK_WIDE * numBlocksHigh)
    val snakeYs: IntArray
        get() {
            return this._snakeYs
        }

    private var _heading: SnakeEngine.Heading = SnakeEngine.Heading.RIGHT
    val heading: SnakeEngine.Heading
        get() {
            return this._heading
        }


    fun eatBob() {
        _snakeLength++
        SnakeEngine.food.spawnBob()
        SnakeEngine.score++
    }

    fun moveSnake() {

        for (i in snakeLength downTo 1) {
            _snakeXs[i] = _snakeXs[i - 1]
            _snakeYs[i] = _snakeYs[i - 1]
        }

        when (_heading) {
            SnakeEngine.Heading.UP -> {
                _snakeYs[0]--
            }

            SnakeEngine.Heading.RIGHT -> {
                _snakeXs[0]++
            }

            SnakeEngine.Heading.DOWN -> {
                _snakeYs[0]++
            }

            SnakeEngine.Heading.LEFT -> {
                _snakeXs[0]--
            }
        }

        if (_snakeYs[0] < 0) {
            _snakeYs[0] = numBlocksHigh - 1
        }

        if (_snakeYs[0] >= numBlocksHigh) {
            _snakeYs[0] = 0
        }

        if (_snakeXs[0] < 0) {
            _snakeXs[0] = NUM_BLOCK_WIDE - 1
        }

        if (_snakeXs[0] >= NUM_BLOCK_WIDE) {
            _snakeXs[0] = 0
        }
    }

    fun setDirection(dir: Int) {
        when (dir) {
            1 -> {  //up
                _heading = SnakeEngine.Heading.UP
            }

            2 -> {  //left
                _heading = SnakeEngine.Heading.LEFT
            }

            3 -> {  //down
                _heading = SnakeEngine.Heading.DOWN
            }

            4 -> {  //right
                _heading = SnakeEngine.Heading.RIGHT
            }
        }
    }

    fun detectDeath(): Boolean {
        var dead = false

//        if (snakeXs[0] == -1) {
//            //dead = true
//            snakeXs[0] =
//            //Log.i("SNAKEINFO", "dead")
//        }
//
//        if (snakeXs[0] >= NUM_BLOCK_WIDE) {
//            dead = true
//            //Log.i("SNAKEINFO", "dead")
//        }
//
//        if (snakeYs[0] == -1) {
//            dead = true
//            //Log.i("SNAKEINFO", "dead")
//        }
//
//        if (snakeYs[0] >= numBlocksHigh) {
//            dead = true
//            //Log.i("SNAKEINFO", "dead")
//        }

        for (i in snakeLength - 1 downTo 1) {
            if ((i > 0) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true
                break
            }
        }

        return dead
    }
}