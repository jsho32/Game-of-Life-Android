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

    /** Gets the shape to add by comparing shape name strings */
    public void getShapeToAdd(String shape) {
        if (shape.equals("Cross")) {
            addCross();
        } else if (shape.equals("Big Square")) {
            addBigSquare();
        } else if (shape.equals("Small Square")) {
            addSmallSquare();
        } else if (shape.equals("X")) {
            addX();
        } else if (shape.equals("Border")) {
            addBorder();
        }
    }

    /** Adds a cross shape to game board */
    private void addCross() {
        int position = columnCount / 2;
        while (position < boardSize) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += columnCount;
        }
        position = (boardSize / 2) - (columnCount / 2);
        while (position < (boardSize / 2) - (columnCount / 2) + columnCount) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += 1;
        }
    }

    /** Adds a large square shape by excluding the border */
    private void addBigSquare() {
        for (int position = 0; position < boardSize; position++) {
            if ((position % columnCount) != 0 && (position % columnCount) != (columnCount -1) &&
                    position >= columnCount && position < (boardSize - columnCount)) {
                gridAdapter.setPositionValue(position, 1);
                gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            }
        }
    }

    /** Adds a smaller square in the center of the board */
    private void addSmallSquare() {
        int squareWidth = (int) ((columnCount / 4) + 0.5);
        if (squareWidth % 2 == 0) {
            squareWidth += 1;
        }
        int startPosition = (boardSize / 2) - (columnCount * (squareWidth / 2)) - (squareWidth / 2);
        for (int horizontal = startPosition; horizontal < (startPosition + squareWidth); horizontal++) {
            for (int vertical = horizontal; vertical < (startPosition + (squareWidth * columnCount)); vertical+=columnCount) {
                gridAdapter.setPositionValue(vertical, 1);
                gridAdapter.getView(vertical, gridView.getChildAt(vertical), gridView);
            }
        }
    }

    /** Adds an X like shape across board */
    private void addX() {
        int position = 0;
        while (position < boardSize) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += columnCount + 1;
        }
        position = columnCount - 1;
        while (position < boardSize) {
            gridAdapter.setPositionValue(position, 1);
            gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            position += columnCount - 1;
        }
    }

    /** Adds life to the boarder of the board, by excluding all center cells */
    private void addBorder() {
        for (int position = 0; position < boardSize; position++) {
            if ((position % columnCount) == 0 || (position % columnCount) == (columnCount -1) ||
                    position < columnCount || position >= (boardSize - columnCount)) {
                gridAdapter.setPositionValue(position, 1);
                gridAdapter.getView(position, gridView.getChildAt(position), gridView);
            }
        }
    }
}
