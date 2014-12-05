package com.shoresdevelopment.gameoflife.game;

import android.widget.GridView;

public class LifeManager {
    private GridView gridView;
    private GameGridAdapter gridAdapter;
    private int position = 0;

    public LifeManager(GridView gridView, GameGridAdapter gridAdapter) {
        this.gridView = gridView;
        this.gridAdapter = gridAdapter;
    }

    public void setLife() {
        position += 1;
        gridAdapter.setPositionValue(position, 1);
        gridAdapter.getView(position, gridView.getChildAt(position), gridView);
    }

    public GridView getGridView() {
        return this.gridView;
    }
}
