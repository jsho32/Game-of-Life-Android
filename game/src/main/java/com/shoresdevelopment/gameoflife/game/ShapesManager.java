package com.shoresdevelopment.gameoflife.game;

import android.widget.GridView;

public class ShapesManager {
    private GridView gridView;
    private GameGridAdapter gridAdapter;
    private int columnCount;
    private int boardSize;

    public ShapesManager(GridView gridView, GameGridAdapter gridAdapter, int boardSize, int columnCount) {
        this.gridView = gridView;
        this.gridAdapter = gridAdapter;
        this.columnCount = columnCount;
        this.boardSize = boardSize;
    }

    public void getShapeToAdd(String shape) {
        if (shape.equals("Cross")) {
            addCross();
        }
    }

    private void addCross() {
        int position = columnCount / 2;
        while (position < boardSize) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += columnCount;
        }
        position = boardSize / 2;
        while (position < ((boardSize/2) + columnCount)) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += 1;
        }
    }
}
