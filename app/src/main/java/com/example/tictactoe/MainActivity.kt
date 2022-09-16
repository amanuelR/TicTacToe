package com.example.tictactoe

import android.graphics.Color.RED
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding



open class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    //var turn : Int = 1
    val SIDE: Int = 3

    // private var tttGame: TicTacToe? = null
    private lateinit var tttGame: TicTacToe
    private lateinit var game: Array<IntArray>
    lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        game = Array(TicTacToe.SIDE) { IntArray(TicTacToe.SIDE) }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildGuiByCode()
    }



    fun buildGuiByCode() {
        val metrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        //  val w: Int = size.x / TicTacToe.SIDE
        val w: Int = width / TicTacToe.SIDE

        // Create the layout manager as a GridLayout
        val gridLayout = GridLayout(this)
        gridLayout.setColumnCount(TicTacToe.SIDE)
        gridLayout.setRowCount(TicTacToe.SIDE + 1)

        // Create the buttons and add them to gridLayout
        var row: Int
        var col: Int
        buttons = Array(TicTacToe.SIDE) { row ->
            Array(TicTacToe.SIDE) { col ->
                Button(this)
            }
        }

        for (row in 0 until TicTacToe.SIDE) {
            for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setTextSize(w * 0.2f)
                gridLayout.addView(buttons.get(row).get(col), w, w)

                // Set gridLayout as the View of this Activity
                setContentView(gridLayout)
                tttGame = TicTacToe(buttons, applicationContext)
                //      for (row in 0 until TicTacToe.SIDE) {
                //        for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setOnClickListener { view: View ->

                    tttGame.update(row, col)
                }
            }
        }
    }


}