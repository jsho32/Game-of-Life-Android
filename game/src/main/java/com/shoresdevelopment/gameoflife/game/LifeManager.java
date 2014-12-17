package com.shoresdevelopment.gameoflife.game;

import android.util.Log;
import android.widget.GridView;

import java.util.HashMap;
import java.util.Map;

public class LifeManager {
    private GridView gridView;
    private GameGridAdapter gridAdapter;
    private int columnCount = 20;
    private int boardsize = 400;
    private int position = 0;
    private Map<Integer, Integer> cells;

    public LifeManager(GridView gridView, GameGridAdapter gridAdapter) {
        this.gridView = gridView;
        this.gridAdapter = gridAdapter;
        initializeCellsMap();
    }

    private void initializeCellsMap() {
        cells = new HashMap<Integer, Integer>();
        for (int i = 0; i < boardsize; i++) {
            cells.put(i, gridAdapter.getPositionValue(i));
        }
    }

    public void setLife() {
        initializeCellsMap();
        getNewLife();
        for (int i = 0; i < boardsize; i++) {
            gridAdapter.setPositionValue(i, cells.get(i));
            gridAdapter.getView(i, gridView.getChildAt(i), gridView);
        }
    }

    public void getNewLife() {
        for(int i = 0; i < boardsize; i++) {
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
            } else if (i == boardsize - columnCount) {
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
            } else if (i > boardsize - columnCount && i < boardsize - 1) {
                if (gridAdapter.getPositionValue(i - columnCount - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount) == 1) count++;
                if (gridAdapter.getPositionValue(i - columnCount + 1) == 1) count++;
                if (gridAdapter.getPositionValue(i - 1) == 1) count++;
                if (gridAdapter.getPositionValue(i + 1) == 1) count++;
            } else if (i == boardsize - 1) {
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
                    Log.e(String.valueOf(i) + " is ", String.valueOf(cells.get(i)));
                }
            } else {
                if (count == 3) {
                    cells.put(i, 1);
                    Log.e(String.valueOf(i) + " is ", String.valueOf(cells.get(i)));
                }
            }
        }
    }
}
