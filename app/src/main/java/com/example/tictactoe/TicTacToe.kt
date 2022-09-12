package com.example.tictactoe

import android.content.Context
import android.widget.Toast


class TicTacToe(context: Context){
    var turn = 0
    var game: Array<IntArray>
    val applicationContext = context
    companion object {
        const val SIDE = 3
    }

    init {
        game = Array(SIDE) {IntArray(SIDE)}
        resetGame()
    }

    fun play(row: Int, col: Int): Int {
        val currentTurn = turn

        if (row >= 0 && col >= 0 && row < SIDE && col < SIDE && game[row][col] == 0)
        {
            game[row][col] = turn
            turn = if (turn == 1) 2 else 1
            return currentTurn
        }
        else
            return 0
    }

    fun whoWon(): Int {
        val rows = checkRows()
        val columns = checkColumns()
        val diagonals = checkDiagonals()

        if (rows > 0)
            return rows

        if (columns > 0)
            return columns

        if (diagonals > 0)
            return diagonals

        return 0
    }

    protected fun checkRows(): Int {
        for (row in 0 until SIDE)
            if (game[row][0] != 0 && game[row][0] == game[row][1] && game[row][1] == game[row][2])
                return game[row][0]
        return 0
    }

    protected fun checkColumns(): Int {
        for (col in 0 until SIDE)
            if (game[0][col] != 0 && game[0][col] == game[1][col] && game[1][col] == game[2][col])
                return game[0][col]
        return 0
    }

    protected fun checkDiagonals(): Int {
        if (game[0][0] != 0 && game[0][0] == game[1][1] && game[1][1] == game[2][2])
            return game[0][0]
        if (game[0][2] != 0 && game[0][2] == game[1][1] && game[1][1] == game[2][0])
            return game[2][0]
        return 0
    }

    fun canNotPlay(): Boolean {
        var result = true
        for (row in 0 until SIDE)
            for (col in 0 until SIDE)
                if (game[row][col] == 0)
                    result = false
        return result
    }

    fun isGameOver():Boolean
    {  //return true
        return canNotPlay() || (whoWon() > 0)
    }
    fun resetGame() {
        for (row in 0 until SIDE)
            for (col in 0 until SIDE)
                game[row][col] = 0
        turn = 1
    }
    fun result(): String {
        if (whoWon() > 0)
            return "Player " + whoWon() + " won"
        else if (canNotPlay())
            return "Tie Game"
        else
            return "PLAY !!"
    }
    fun enableButtons( enabled: Boolean ) {
        for (row in 0 until TicTacToe.SIDE) {
            for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setEnabled(enabled);
            }
        }
    }

    fun update(row: Int, col:Int) {
        var turn= play(row,col)
        var play=turn

        if(play == 1)
            buttons[row][col].text = "O"
        else if (play==2)
            buttons[row][col].text = "X"
        if(isGameOver())
        {
            enableButtons(false)
            test(result().toString())
            test("DONE")
        }
    }

    fun test(str: String): Unit {
        val toast = Toast.makeText(applicationContext, str,
            Toast.LENGTH_SHORT)
        toast.show()
    }
}