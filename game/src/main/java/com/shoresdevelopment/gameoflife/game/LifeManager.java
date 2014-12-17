package com.shoresdevelopment.gameoflife.game;

import android.widget.GridView;

import java.util.HashMap;
import java.util.Map;

public class LifeManager {
    private GridView gridView;
    private GameGridAdapter gridAdapter;
    private final int columnCount = 20;
    private final int boardSize = 400;
    private Map<Integer, Integer> cells;

    public LifeManager(GridView gridView, GameGridAdapter gridAdapter) {
        this.gridView = gridView;
        this.gridAdapter = gridAdapter;
    }

    private void initializeCellsMap() {
        cells = new HashMap<Integer, Integer>();
        for (int i = 0; i < boardSize; i++) {
            cells.put(i, gridAdapter.getPositionValue(i));
        }
    }

    public void setLife() {
        initializeCellsMap();
        getNewLife();
        for (int i = 0; i < boardSize; i++) {
            gridAdapter.setPositionValue(i, cells.get(i));
            gridAdapter.getView(i, gridView.getChildAt(i), gridView);
        }
    }

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

            if (cells.get(i) == 1) {
                if (count != 2 && count != 3) {
                    cells.put(i, 0);
                }
            } else {
                if (count == 3) {
                    cells.put(i, 1);
                }
            }
        }
    }
}
