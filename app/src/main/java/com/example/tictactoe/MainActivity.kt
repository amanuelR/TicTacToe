package com.example.tictactoe

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        gridLayout.setRowCount(TicTacToe.SIDE + 1)
        gridLayout.setColumnCount(TicTacToe.SIDE)

        // Create the buttons and add them to gridLayout
        var row: Int
        var col: Int
        buttons = Array(TicTacToe.SIDE) { row ->
            Array(TicTacToe.SIDE) { col ->
                Button(this)
            }
        }

        // set up layout parameters of 4th row of gridLayout
        var status = TextView(this)
        val rowSpec = GridLayout.spec(TicTacToe.SIDE,1)
        val columnSpec = GridLayout.spec(0, TicTacToe.SIDE)
        val lpStatus = GridLayout.LayoutParams(rowSpec, columnSpec)
        status.setLayoutParams(lpStatus)
        // set up status' characteristics
        status.setWidth(TicTacToe.SIDE * w)
        status.setHeight(w)
        status.setGravity(Gravity.CENTER)
        status.setBackgroundColor(GREEN)
        status.setTextSize((w * .15).toInt().toFloat())
        status.setText("PLAY !!")

        tttGame = TicTacToe(buttons, applicationContext)

        for (row in 0 until TicTacToe.SIDE) {
            for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setTextSize(w * 0.2f)
                gridLayout.addView(buttons.get(row).get(col), w, w)

                // Set gridLayout as the View of this Activity
                //setContentView(gridLayout)

                //      for (row in 0 until TicTacToe.SIDE) {
                //        for (col in 0 until TicTacToe.SIDE) {
                buttons[row][col].setOnClickListener { view: View ->
                    tttGame.update(row, col)
                    //status.setText(tttGame.result())
                    if(tttGame.isGameOver())
                    {
                        status.setBackgroundColor(Color.RED)
                        tttGame.enableButtons(false)
                        status.setText(tttGame.result().toString())
                        showNewGameDialog()
                    }

                }
            }
        }
        gridLayout.addView(status)
        // Set gridLayout as the View of this Activity
        setContentView(gridLayout)
    }

    fun showNewGameDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("This is fun")
        alert.setMessage("Play again?")
        val playAgain = PlayDialog()
        alert.setPositiveButton("YES", playAgain)
        alert.setNegativeButton("NO", playAgain)
        alert.show()
    }
    private inner class PlayDialog : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, id: Int) {
            //lateinit var activity: MainActivity
            if (id == -1) /* YES button */ {
                this@MainActivity.buildGuiByCode()
                //activity.resetGame()
                //activity.enableButtons(true) activity.resetButtons()
                //activity.status.setBackgroundColor(Color.GREEN)
                //activity.status.setText(activity.result())
            } else if (id == -2) // NO button
                this@MainActivity.finish()
        }
    }
}