package com.shoresdevelopment.gameoflife.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

public class GameActivity extends Activity{
    private int boardSize = 400;
    private int[] boardArray;
    private GridView lifeGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        fillGridArray();
        initializeGrid();
    }

    private void initializeGrid() {
        lifeGrid = (GridView) findViewById(R.id.lifeGrid);
        GameGridAdapter gridAdapter = new GameGridAdapter(GameActivity.this, boardArray);
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
}
