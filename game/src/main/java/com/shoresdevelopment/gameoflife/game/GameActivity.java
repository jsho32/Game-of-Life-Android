package com.shoresdevelopment.gameoflife.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends Activity{
    private Map<Integer, Integer> boardSizeMap;
    private Map<Integer, Integer> speedMap;
    private int boardSize;
    private int boardKey;
    private int evolutionSpeed;
    private int evolutionKey;
    private int[] boardArray;
    private GridView lifeGrid;
    private TextView gameStart;
    private TextView gameStop;
    private TextView size;
    private TextView speed;
    private TextView shapes;
    private boolean isRunning;
    private List<String> sizeList;
    private List<String> speedList;
    private GameGridAdapter gridAdapter;
    private LifeManager lifeManager;

    /** Called when activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeGameButtons();
        setLists();
        setMaps();
        setBoardSize(boardSizeMap.get(1));
        setSpeed(speedMap.get(2));
        initializeFeaturesButtons();
        fillGridArray();
        initializeGrid();
        lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));
    }

    /** runs on the thread */
    private void gameRun() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    lifeManager.setLife();
                    handler.postDelayed(this, evolutionSpeed);
                }
            }
        };
        handler.post(runnable);
    }

    /** Set the values of features lists (board sizes, generation speeds) */
    private void setLists() {
        sizeList = new ArrayList<String>();
        sizeList.add("10 X 10");
        sizeList.add("20 X 20");
        sizeList.add("30 X 30");
        sizeList.add("40 X 40");
        sizeList.add("50 X 50");
        sizeList.add("60 X 60");

        speedList = new ArrayList<String>();
        speedList.add("Really Slow");
        speedList.add("Slow");
        speedList.add("Medium");
        speedList.add("Fast");
        speedList.add("Really Fast");
    }

    /** Set the values of features maps (board sizes, generation speeds) */
    private void setMaps() {
        boardSizeMap = new HashMap<Integer, Integer>();
        boardSizeMap.put(0, 10);
        boardSizeMap.put(1, 20);
        boardSizeMap.put(2, 30);
        boardSizeMap.put(3, 40);
        boardSizeMap.put(4, 50);
        boardSizeMap.put(5, 60);

        speedMap = new HashMap<Integer, Integer>();
        speedMap.put(0, 3000);
        speedMap.put(1, 2000);
        speedMap.put(2, 1000);
        speedMap.put(3, 500);
        speedMap.put(4, 200);
    }

    /** Sets the board size and board key */
    private void setBoardSize(int size) {
        boardSize = (int) Math.pow(size, 2);
        for (Map.Entry<Integer, Integer> entry : boardSizeMap.entrySet()) {
            if (size == entry.getValue()) {
                boardKey = entry.getKey();
            }
        }
    }

    /** Sets the speed for generation iterations */
    private void setSpeed(int speed) {
        evolutionSpeed = speed;
        for (Map.Entry<Integer, Integer> entry : speedMap.entrySet()) {
            if (speed == entry.getValue()) {
                evolutionKey = entry.getKey();
            }
        }
    }

    /** Sets initial settings for the grid that contains life cells */
    private void initializeGrid() {
        lifeGrid = (GridView) findViewById(R.id.lifeGrid);
        gridAdapter = new GameGridAdapter(GameActivity.this, boardArray, boardSizeMap.get(boardKey));
        lifeGrid.setAdapter(gridAdapter);
        lifeGrid.setBackgroundColor(Color.TRANSPARENT);
        lifeGrid.setNumColumns(boardSizeMap.get(boardKey));
    }

    /** Fills array containing life values with all dead cells (0 is dead) */
    private void fillGridArray() {
        boardArray = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            boardArray[i] = 0;
        }
    }

    /** Initializes game buttons and sets on click listeners */
    private void initializeGameButtons() {
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

    /** Initializes features buttons and sets on click listeners */
    private void initializeFeaturesButtons() {
        final DropDownManager sizesDropDown = new DropDownManager(GameActivity.this, sizeList, boardKey);
        size = (TextView) findViewById(R.id.board_size);
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.setBackgroundColor(Color.GRAY);
                sizesDropDown.popupWindow.showAsDropDown(v, -5, 0);
                sizesDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        size.setBackgroundColor(Color.BLACK);
                        boardKey = sizesDropDown.getSelected();
                        setBoardSize(boardSizeMap.get(boardKey));
                        fillGridArray();
                        initializeGrid();
                        gameStop.performClick();
                        lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));
                    }
                });
            }
        });

        final DropDownManager speedDropDown = new DropDownManager(GameActivity.this, speedList, evolutionKey);
        speed = (TextView) findViewById(R.id.generation_speed);
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed.setBackgroundColor(Color.GRAY);
                speedDropDown.popupWindow.showAsDropDown(v, -5, 0);
                speedDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        speed.setBackgroundColor(Color.BLACK);
                        evolutionKey = speedDropDown.getSelected();
                        setSpeed(speedMap.get(evolutionKey));
                    }
                });
            }
        });
    }

    /** Sets each view within grid to clickable state true or false */
    private void setGridClickable(boolean clickable) {
        for (int i = 0; i < lifeGrid.getChildCount(); i++) {
            lifeGrid.getChildAt(i).setClickable(clickable);
        }
    }
}
