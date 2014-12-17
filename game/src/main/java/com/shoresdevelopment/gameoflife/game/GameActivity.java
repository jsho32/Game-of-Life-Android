package com.shoresdevelopment.gameoflife.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends Activity{
    private int boardSize = 400;
    private int[] boardArray;
    private GridView lifeGrid;
    private TextView gameStart;
    private TextView gameStop;
    private boolean isRunning;
    GameGridAdapter gridAdapter;
    LifeManager lifeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeButtons();
        fillGridArray();
        initializeGrid();
        lifeManager = new LifeManager(lifeGrid, gridAdapter);
    }

    /** runs on the thread */
    private void gameRun() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    lifeManager.setLife();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(runnable);
    }

    private void initializeGrid() {
        lifeGrid = (GridView) findViewById(R.id.lifeGrid);
        gridAdapter = new GameGridAdapter(GameActivity.this, boardArray);
        lifeGrid.setAdapter(gridAdapter);
        lifeGrid.setBackgroundColor(Color.BLACK);
        lifeGrid.setVerticalSpacing(1);
        lifeGrid.setHorizontalSpacing(1);
    }

    private void fillGridArray() {
        boardArray = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            boardArray[i] = 0;
        }
    }

    private void initializeButtons() {
        gameStart = (TextView) findViewById(R.id.game_start);
        gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = true;
                gameRun();
                gameStart.setBackgroundColor(Color.GREEN);
                gameStart.setTextColor(Color.BLACK);
                gameStart.setClickable(false);
                gameStop.setClickable(true);
                gameStop.setBackgroundColor(Color.BLACK);
                gameStop.setTextColor(Color.WHITE);
                setGridClickable(false);
            }
        });
        gameStop = (TextView) findViewById(R.id.game_stop);
        gameStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                gameStop.setBackgroundColor(Color.RED);
                gameStop.setTextColor(Color.BLACK);
                gameStop.setClickable(false);
                gameStart.setClickable(true);
                gameStart.setBackgroundColor(Color.BLACK);
                gameStart.setTextColor(Color.WHITE);
                setGridClickable(true);
            }
        });
    }

    private void setGridClickable(boolean clickable) {
        for (int i = 0; i < lifeGrid.getChildCount(); i++) {
            lifeGrid.getChildAt(i).setClickable(clickable);
        }
    }
}
