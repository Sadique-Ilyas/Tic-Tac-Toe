package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView player1Score;
    private TextView player2Score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);

        for (int i = 0; i<3 ; i++)
        {
            for (int j=0; j<3 ; j++)
            {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // if the button contains X or O and we tap on it then just return without doing anything
        if (!((Button) v).getText().toString().equals(""))
        {
            return;
        }
        //if the button doesn't contain any string then perform following
        if (player1Turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }
        //incrementing round count
        roundCount++;
        if (checkForWin())
        {
            if (player1Turn)
            {
                player1Wins();
            }
            else 
            {
                player2Wins();
            }
        }
        else if (roundCount == 9)
        {
            draw();
        }
        else 
        {
            player1Turn = !player1Turn;
        }
    }

    //checking for win
    private boolean checkForWin()
    {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0;i < 3; i++)
        {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
            {
                return true;
            }
        }
        for (int i = 0;i < 3; i++)
        {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
            {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
        {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
        {
            return true;
        }
        return false;
    }

    //if player1 wins do the following
    private void player1Wins()
    {
        player1Points++;
        Toast.makeText(this, "Player 1 wins !", Toast.LENGTH_SHORT).show();
        updateScoreBoard();
        resetBoard();
    }

    //if player1 wins do the following
    private void player2Wins()
    {
        player2Points++;
        Toast.makeText(this, "Player 2 wins !", Toast.LENGTH_SHORT).show();
        updateScoreBoard();
        resetBoard();
    }

    //if it is a tie do the following
    private void draw()
    {
        Toast.makeText(this, "Draw !", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //to update the score board
    private void updateScoreBoard()
    {
        player1Score.setText("Player 1: " +player1Points);
        player2Score.setText("Player 2: " +player2Points);
    }

    //to reset the board if any player wins
    private void resetBoard()
    {
        for (int i = 0; i < 3; i++)
        {
             for ( int j = 0; j < 3; j++)
             {
                 buttons[i][j].setText("");
             }
        }

        roundCount = 0;
        player1Turn = true;
    }

    //to reset the game
    private void resetGame()
    {
        player1Points = 0;
        player2Points = 0;
        updateScoreBoard();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}