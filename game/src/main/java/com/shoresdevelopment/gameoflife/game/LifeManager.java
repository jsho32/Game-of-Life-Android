package com.shoresdevelopment.gameoflife.game;

import android.widget.GridView;

import java.util.HashMap;
import java.util.Map;

public class LifeManager {
    private GridView gridView;
    private GameGridAdapter gridAdapter;
    private int columnCount;
    private int boardSize;
    private Map<Integer, Integer> nextGeneration;

    /** Constructor */
    public LifeManager(GridView gridView, GameGridAdapter gridAdapter, int boardSize, int columnCount) {
        this.gridView = gridView;
        this.gridAdapter = gridAdapter;
        this.columnCount = columnCount;
        this.boardSize = boardSize;
    }

    /** Sets next generation map to current grid generation */
    private void initializeNextGenerationMap() {
        nextGeneration = new HashMap<Integer, Integer>();
        for (int i = 0; i < boardSize; i++) {
            nextGeneration.put(i, gridAdapter.getPositionValue(i));
        }
    }

    /** Sets next generation to grid, from next generation map */
    public void setLife() {
        initializeNextGenerationMap();
        getNewLife();
        for (int i = 0; i < boardSize; i++) {
            gridAdapter.setPositionValue(i, nextGeneration.get(i));
            gridAdapter.getView(i, gridView.getChildAt(i), gridView);
        }
    }

    /** Evaluates each cell and sets life accordingly, to next generation map */
    public void getNewLife() {
        for(int i = 0; i < boardSize; i++) {
            int count = 0;
            if (i == 0) {
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount + 1) == 1) count++;
            } else if (i < columnCount - 1) {
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount + 1) == 1) count++;
            } else if (i == columnCount - 1) {
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
            } else if (i == boardSize - columnCount) {
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
            } else if (i > boardSize - columnCount && i < boardSize - 1) {
                if (gridAdapter.getPositionValue(i - columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
            } else if (i == boardSize - 1) {
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
            } else if (i % columnCount == 0) {
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount + 1) == 1) count++;
            } else if (i % columnCount == columnCount - 1) {
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
            } else {
                if (gridAdapter.getPositionValue(i - columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i + columnCount + 1) == 1) count++;
            }

            if (nextGeneration.get(i) == 1) {
                if (count != 2 && count != 3) {
                    nextGeneration.put(i, 0);
                }
            } else {
                if (count == 3) {
                    nextGeneration.put(i, 1);
                }
            }
        }
    }
}
