package com.shoresdevelopment.gameoflife.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends Activity {
    private Map<Integer, Integer> boardSizeMap;
    private Map<Integer, Integer> speedMap;
    private Map<Integer, Integer> cellColorsMap;
    private Map<Integer, Integer> bgColorsMap;
    private int buttonSize, buttonPadding;
    private int boardSize;
    private int boardKey;
    private int evolutionSpeed;
    private int evolutionKey;
    private int cellColorKey;
    private int bgColorKey;
    private int[] boardArray;
    private GridView lifeGrid;
    private ImageView gameStart;
    private ImageView gameStop;
    private boolean isRunning;
    private List<String> sizeList;
    private List<String> speedList;
    private List<String> shapeList;
    private List<String> aboutList;
    private List<String> settingsList;
    private List<String> cellColorsList;
    private List<String> bgColorsList;
    private GameGridAdapter gridAdapter;
    private LifeManager lifeManager;
    private LinearLayout.LayoutParams params;
    private MediaPlayer mp;

    /**
     * Called when activity is first created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setLists();
        setMaps();
        setButtonSize();
        initializeHeader();
        initializeGameButtons();

        setBoardSize(boardSizeMap.get(1));
        setSpeed(speedMap.get(2));
        setCellColor(cellColorsMap.get(5));

        initializeFeaturesButtons();
        fillGridArray();
        initializeGrid();

        lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));
        mp = MediaPlayer.create(getApplicationContext(), R.raw.iteration);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
        gameStop.setClickable(false);
        gameStart.setClickable(true);
    }

    /**
     * runs a thread that manages generation iterations
     */
    private void gameRun() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (isRunning) {

                    if (mp.getCurrentPosition() > 0) {
                        mp.pause();
                        mp.seekTo(0);
                    }

                    lifeManager.setLife();
                    mp.start();
                    handler.postDelayed(this, evolutionSpeed);
                }

            }
        };
        handler.post(runnable);
    }

    /**
     * Initializes the size of button images
     */
    private void setButtonSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        buttonSize = screenSize.x / 7;
        buttonPadding = buttonSize / 12;

        params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
        params.setMargins(buttonPadding, buttonPadding * 2, buttonPadding, buttonPadding * 2);
    }

    /**
     * Set the values of features lists (board sizes, generation speeds)
     */
    private void setLists() {
        sizeList = new ArrayList<>();
        sizeList.add("Select a board size:");
        sizeList.add("15 X 15");
        sizeList.add("25 X 25");
        sizeList.add("35 X 35");
        sizeList.add("45 X 45");
        sizeList.add("55 X 55");
        sizeList.add("65 X 65");
        sizeList.add("75 X 75");
        sizeList.add("85 X 85");

        speedList = new ArrayList<>();
        speedList.add("Select generation speed:");
        speedList.add("Really Slow");
        speedList.add("Slow");
        speedList.add("Medium");
        speedList.add("Fast");
        speedList.add("Really Fast");

        shapeList = new ArrayList<>();
        shapeList.add("Select a preset shape:");
        shapeList.add("Cross");
        shapeList.add("Big Square");
        shapeList.add("Small Square");
        shapeList.add("X");
        shapeList.add("Border");

        aboutList = new ArrayList<>();
        aboutList.add("About The Game of Life:");
        aboutList.add("Game Information");
        aboutList.add("More from Developer!");

        settingsList = new ArrayList<>();
        settingsList.add("Select a settings group:");
        settingsList.add("Cell color");
        settingsList.add("Background color");

        cellColorsList = new ArrayList<>();
        cellColorsList.add("Select a cell color:");
        cellColorsList.add("Black");
        cellColorsList.add("Blue");
        cellColorsList.add("Gold");
        cellColorsList.add("Green");
        cellColorsList.add("Purple");
        cellColorsList.add("Red");
        cellColorsList.add("White");

        bgColorsList = new ArrayList<>();
        bgColorsList.add("Select a background color:");
        bgColorsList.add("Black");
        bgColorsList.add("Blue");
        bgColorsList.add("Cyan");
        bgColorsList.add("Gray");
        bgColorsList.add("Green");
        bgColorsList.add("Red");
        bgColorsList.add("White");
        bgColorsList.add("Yellow");
    }

    /**
     * Set the values of features maps (board sizes, generation speeds)
     */
    private void setMaps() {
        boardSizeMap = new HashMap<>();
        boardSizeMap.put(0, 15);
        boardSizeMap.put(1, 25);
        boardSizeMap.put(2, 35);
        boardSizeMap.put(3, 45);
        boardSizeMap.put(4, 55);
        boardSizeMap.put(5, 65);
        boardSizeMap.put(6, 75);
        boardSizeMap.put(7, 85);

        speedMap = new HashMap<>();
        speedMap.put(0, 3000);
        speedMap.put(1, 2000);
        speedMap.put(2, 1000);
        speedMap.put(3, 500);
        speedMap.put(4, 200);

        cellColorsMap = new HashMap<>();
        cellColorsMap.put(0, R.drawable.black_circle);
        cellColorsMap.put(1, R.drawable.blue_circle);
        cellColorsMap.put(2, R.drawable.gold_circle);
        cellColorsMap.put(3, R.drawable.green_circle);
        cellColorsMap.put(4, R.drawable.purple_circle);
        cellColorsMap.put(5, R.drawable.red_circle);
        cellColorsMap.put(6, R.drawable.white_circle);

        bgColorsMap = new HashMap<>();
        bgColorsMap.put(0, Color.BLACK);
        bgColorsMap.put(1, Color.BLUE);
        bgColorsMap.put(2, Color.CYAN);
        bgColorsMap.put(3, Color.GRAY);
        bgColorsMap.put(4, Color.GREEN);
        bgColorsMap.put(5, Color.RED);
        bgColorsMap.put(6, Color.WHITE);
        bgColorsMap.put(7, Color.YELLOW);
    }

    /**
     * Sets the board size and board key
     */
    private void setBoardSize(int size) {
        boardSize = size * size;

        for (Map.Entry<Integer, Integer> entry : boardSizeMap.entrySet()) {

            if (size == entry.getValue()) {
                boardKey = entry.getKey();
            }

        }

    }

    /**
     * Sets the speed for generation iterations
     */
    private void setSpeed(int speed) {
        evolutionSpeed = speed;

        for (Map.Entry<Integer, Integer> entry : speedMap.entrySet()) {

            if (speed == entry.getValue()) {
                evolutionKey = entry.getKey();
            }

        }

    }

    /**
     * Sets the image for the cells
     */
    private void setCellColor(int image) {
        for (Map.Entry<Integer, Integer> entry : cellColorsMap.entrySet()) {

            if (image == entry.getValue()) {
                cellColorKey = entry.getKey();
            }

        }

    }

    /**
     * Sets initial settings for the grid that contains life cells
     */
    private void initializeGrid() {
        lifeGrid = (GridView) findViewById(R.id.lifeGrid);

        WindowManager windowManager = (WindowManager) GameActivity.this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        double cellDimension = (double) metrics.widthPixels / (double) boardSizeMap.get(boardKey);

        gridAdapter = new GameGridAdapter(GameActivity.this, boardArray, (int) cellDimension, cellColorsMap.get(cellColorKey));
        lifeGrid.setColumnWidth((int) cellDimension);

        double padding = ((cellDimension - Math.floor(cellDimension)) * boardSizeMap.get(boardKey)) / 2;

        Log.e("PADDING", String.valueOf(padding));
        lifeGrid.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        lifeGrid.setAdapter(gridAdapter);
    }

    /**
     * Fills array containing life values with all dead cells (0 is dead)
     */
    private void fillGridArray() {
        boardArray = new int[boardSize];

        for (int i = 0; i < boardSize; i++) {
            boardArray[i] = 0;
        }

    }

    /**
     * Initializes the header components
     */
    private void initializeHeader() {
        final DropDownManager aboutDropDown = new DropDownManager(GameActivity.this, aboutList, aboutList.size());

        ImageView about = (ImageView) findViewById(R.id.about);
        RelativeLayout.LayoutParams headerParams = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        headerParams.setMargins(buttonPadding, buttonPadding * 2, buttonPadding, buttonPadding * 2);
        headerParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        about.setLayoutParams(headerParams);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                aboutDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                        if (aboutDropDown.getSelected() == 1) {
                            Uri uri = Uri.parse("http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else if (aboutDropDown.getSelected() == 2) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=J.+Shores+Development");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                        aboutDropDown.setCurrentSelection(aboutList.size());

                    }
                });
            }
        });

        ImageView header = (ImageView) findViewById(R.id.header_banner);
        headerParams = new RelativeLayout.LayoutParams(buttonSize * 4, buttonSize);
        headerParams.setMargins(buttonPadding, buttonPadding * 2, buttonPadding, buttonPadding * 2);
        headerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        header.setLayoutParams(headerParams);

        final DropDownManager settingsDropDown = new DropDownManager(GameActivity.this, settingsList, settingsList.size());

        ImageView settings = (ImageView) findViewById(R.id.settings);
        headerParams = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        headerParams.setMargins(buttonPadding, buttonPadding * 2, buttonPadding, buttonPadding * 2);
        headerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        settings.setLayoutParams(headerParams);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                settingsDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                settingsDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        isRunning = false;
                        gameStop.setClickable(false);
                        gameStart.setClickable(true);

                        if (settingsDropDown.getSelected() == 1) {
                            final DropDownManager cellColorDropDown = new DropDownManager(GameActivity.this, cellColorsList, cellColorsList.size());

                            cellColorDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                            cellColorDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    cellColorKey = cellColorDropDown.getSelected();
                                    setCellColor(cellColorsMap.get(cellColorKey - 1));

                                    initializeGrid();
                                    lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));

                                    try {
                                        Toast.makeText(GameActivity.this, "Cell color set to: " + cellColorsList.get(cellColorDropDown.getSelected()), Toast.LENGTH_SHORT).show();
                                    } catch (IndexOutOfBoundsException ignored) {
                                    }
                                }
                            });

                        } else if (settingsDropDown.getSelected() == 2) {
                            final DropDownManager bgColorDropDown = new DropDownManager(GameActivity.this, bgColorsList, bgColorsList.size());

                            bgColorDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                            bgColorDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    bgColorKey = bgColorDropDown.getSelected();
                                    View root = findViewById(R.id.game_root);
                                    root.setBackgroundColor(bgColorsMap.get(bgColorKey - 1));

                                    try {
                                        Toast.makeText(GameActivity.this, "Background color set to: " + bgColorsList.get(bgColorDropDown.getSelected()), Toast.LENGTH_SHORT).show();
                                    } catch (IndexOutOfBoundsException ignored) {
                                    }
                                }
                            });
                        }

                        settingsDropDown.setCurrentSelection(aboutList.size());

                    }
                });
            }

        });
    }

    /**
     * Initializes game buttons and sets on click listeners
     */
    private void initializeGameButtons() {
        gameStart = (ImageView) findViewById(R.id.game_start);
        gameStart.setLayoutParams(params);
        gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = true;
                gameRun();
                gameStart.setClickable(false);
                gameStop.setClickable(true);
                Toast.makeText(GameActivity.this, "Life Begins!", Toast.LENGTH_SHORT).show();
            }
        });

        gameStop = (ImageView) findViewById(R.id.game_stop);
        gameStop.setLayoutParams(params);
        gameStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                gameStop.setClickable(false);
                gameStart.setClickable(true);
                Toast.makeText(GameActivity.this, "Life Stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView gameClear = (ImageView) findViewById(R.id.game_clear);
        gameClear.setLayoutParams(params);
        gameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                fillGridArray();
                initializeGrid();
                lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));
                gameStop.setClickable(false);
                gameStart.setClickable(true);
                Toast.makeText(GameActivity.this, "Board Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Initializes features buttons and sets on click listeners
     */
    private void initializeFeaturesButtons() {
        final DropDownManager sizesDropDown = new DropDownManager(GameActivity.this, sizeList, boardKey + 1);

        ImageView size = (ImageView) findViewById(R.id.board_size);
        size.setLayoutParams(params);
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizesDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                sizesDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (boardKey + 1 != sizesDropDown.getSelected()) {
                            boardKey = sizesDropDown.getSelected();
                            setBoardSize(boardSizeMap.get(boardKey - 1));
                            isRunning = false;
                            fillGridArray();
                            initializeGrid();
                            lifeManager = new LifeManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));
                            gameStop.setClickable(false);
                            gameStart.setClickable(true);

                            try {
                                Toast.makeText(GameActivity.this, "Board sized to: " + sizeList.get(sizesDropDown.getSelected()), Toast.LENGTH_SHORT).show();
                            } catch (IndexOutOfBoundsException ignored) {
                            }
                        }
                    }
                });
            }
        });

        final DropDownManager speedDropDown = new DropDownManager(GameActivity.this, speedList, evolutionKey + 1);
        ImageView speed = (ImageView) findViewById(R.id.generation_speed);
        speed.setLayoutParams(params);
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                speedDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        evolutionKey = speedDropDown.getSelected();
                        setSpeed(speedMap.get(evolutionKey - 1));

                        try {
                            Toast.makeText(GameActivity.this, "Speed set at: " + speedList.get(speedDropDown.getSelected()), Toast.LENGTH_SHORT).show();
                        } catch (IndexOutOfBoundsException ignored) {
                        }

                    }
                });
            }
        });

        final DropDownManager shapesDropDown = new DropDownManager(GameActivity.this, shapeList, shapeList.size());
        ImageView shapes = (ImageView) findViewById(R.id.shapes);
        shapes.setLayoutParams(params);
        shapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shapesDropDown.popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                shapesDropDown.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ShapesManager shapesManager = new ShapesManager(lifeGrid, gridAdapter, boardSize, boardSizeMap.get(boardKey));

                        try {
                            shapesManager.getShapeToAdd(shapeList.get(shapesDropDown.getSelected()));
                            Toast.makeText(GameActivity.this, "Added " + shapeList.get(shapesDropDown.getSelected()), Toast.LENGTH_SHORT).show();
                        } catch (IndexOutOfBoundsException ignored) {
                        }

                        shapesDropDown.setCurrentSelection(shapeList.size());
                    }
                });
            }
        });
    }
}


