package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var binding: ActivityMainBinding
    var turn : Int =1
    val SIDE: Int = 3

    //   private var tttGame: TicTacToe? = null
    //   private lateinit var tttGame: TicTacToe
    private lateinit var game: Array<IntArray>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_main)
        game = Array(TicTacToe.SIDE) { IntArray(TicTacToe.SIDE) }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildGuiByCode()
    }

    fun test(str: String): Unit {
        val toast = Toast.makeText(applicationContext, str,
            Toast.LENGTH_SHORT)
        toast.show()
    }

    fun buildGuiByCode() {
        val metrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        //  val w: Int = size.x / TicTacToe.SIDE
        val w: Int = width / TicTacToe.SIDE

        // Create the layout manager as a GridLayout
        val gridLayout = GridLayout(this)
        gridLayout.setColumnCount(TicTacToe.SIDE)
        gridLayout.setRowCount(TicTacToe.SIDE)

        // Create the buttons and add them to gridLayout
        var row: Int
        var col: Int
        buttons = Array(TicTacToe.SIDE) { row ->
            Array(TicTacToe.SIDE) { col ->
                //   Button(row, col)
                Button(this)

            }

        }

        for (row in 0 until TicTacToe.SIDE) {
            for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setTextSize(w * 0.2f)
                gridLayout.addView(buttons.get(row).get(col), w, w)

                // Set gridLayout as the View of this Activity
                setContentView(gridLayout)
                //      for (row in 0 until TicTacToe.SIDE) {
                //        for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setOnClickListener { view: View ->
                    update(row, col)
                }
            }
        }
    }
    fun play(row: Int, col: Int): Int {
        var currentTurn = turn
        8

        if (row >= 0 && col >= 0 && row < 3 && col < SIDE && game[row][col]
            == 0)
        {
            game[row][col] = turn

            if (turn == 1)
                turn = 2
            else
                turn = 1
            return currentTurn
        }
        else
            return 0
    }

    fun checkRows(): Int {
        for (row in 0 until SIDE) if (game[row][0] != 0 && game[row][0] ==
            game[row][1] && game[row][1] == game[row][2]) return game[row][0]
        return 0
    }

    fun checkColumns(): Int {
        for (col in 0 until SIDE) if (game[0][col] != 0 && game[0][col] ==
            game[1][col] && game[1][col] == game[2][col]) return game[0][col]
        return 0
    }
    fun checkDiagonals(): Int {
        if (game[0][0] != 0 && game[0][0] == game[1][1] && game[1][1] ==
            game[2][2]) return game[0][0]
        return if (game[0][2] != 0 && game[0][2] == game[1][1] && game[1][1]
            == game[2][0]
        ) game[2][0] else 0
    }
    fun whoWon(): Int
    {
        val rows = checkRows()
        if (rows > 0) return rows
        val columns = checkColumns()
        if (columns > 0) return columns
        val diagonals = checkDiagonals()
        return if (diagonals > 0) diagonals else 0
    }

    fun canNotPlay(): Boolean
    {
        var result = true
        for (row in 0 until SIDE) for (col in 0 until SIDE) if
                                                                    (game[row][col] == 0) result = false
        return result
    }

    fun isGameOver():Boolean
    {  //return true
        return canNotPlay() || (whoWon() > 0)
    }
    fun resetGame() {
        for (row in 0 until SIDE) for (col in 0 until SIDE) game[row][col] = 0
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
    fun  update(row: Int, col:Int)
    {  var turn= play(row,col)

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
}