package com.shoresdevelopment.gameoflife.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.*;
import android.widget.*;

import java.util.List;

/** Handles all game board sizes in drop down list view */
public class DropDownManager {
    private Context context;
    private String popUpContents[];
    public PopupWindow popupWindow;
    private int currentSelection;

    public DropDownManager(Context context, List<String> childList, int currentSelection) {
        this.context = context;
        this.currentSelection = currentSelection;
        popUpContents = new String[childList.size()];
        childList.toArray(popUpContents);
        popupWindow = popupWindow();
    }

    /** Sets the list view as pop up window */
    public PopupWindow popupWindow() {
        PopupWindow popupWindow = new PopupWindow(context);

        ListView listViewSizes = new ListView(context);
        listViewSizes.setAdapter(sizesAdapter(popUpContents));

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(screenSize.x / 2);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(listViewSizes);

        return popupWindow;
    }

    /** adapter where the list values will be set */
    private ArrayAdapter<String> sizesAdapter(String sizeArray[]) {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.drop_down_item, sizeArray) {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.drop_down_item, null);
                }

                final TextView listItem = (TextView) convertView.findViewById(R.id.list_item);

                if (position == 0) {
                    listItem.setBackgroundColor(Color.RED);
                } else if (position == currentSelection) {
                    listItem.setBackgroundColor(Color.GRAY);
                } else {
                    listItem.setBackgroundColor(Color.BLACK);
                }

                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            currentSelection = position;
                            popupWindow.dismiss();
                        }
                    }
                });

                String item = getItem(position);
                listItem.setText(item);
                listItem.setTag(String.valueOf(position));

                return convertView;
            }
        };

        return adapter;
    }

    /** Get selected position */
    public int getSelected() {
        return currentSelection;
    }

    public void setCurrentSelection(int currentSelection) {
        this.currentSelection = currentSelection;
    }
}
