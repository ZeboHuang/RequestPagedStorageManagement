package com.lemondev.requestpagedstoragemanagementdemo.graphic;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

/**
 * 2022/3/4
 * Created by vibrantBobo
 */

public class TableChart extends TableLayout {
    public TableChart(Context context) {
        super(context);
        initView();
    }

    public TableChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    //方便获取位置


    private int rows;
    private int cols;

    private List<List<String>> elements;

    private int gridWidth;
    private int gridHeight;

    private int gridMargin;
    private int gridPadding;

    private void initView() {
        gridHeight = 100;
        gridWidth = 100;
        gridMargin = 5;

//        rows = 3;
//        cols = 25;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private TextGridView drawGrid() {
        TextGridView t = new TextGridView(getContext());
        t.setMinimumWidth(gridWidth);
        t.setMinimumHeight(gridHeight);
        return t;
    }

    private TextGridView drawGrid(String text) {
        TextGridView t = drawGrid();
        t.setText(text);
        return t;
    }

    private TableRow drawTableRow() {
        TableRow tableRow = new TableRow(getContext());
        for (int i = 0; i < cols; i++) {
            TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(gridMargin, gridMargin, gridMargin, gridMargin);
            tableRow.addView(drawGrid(), trParams);
        }
        return tableRow;
    }

    private TableRow drawTableRow(List<String> eleList) {
        TableRow tableRow = new TableRow(getContext());
        for (int i = 0; i < cols; i++) {
            TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(gridMargin, gridMargin, gridMargin, gridMargin);
            if (i >= eleList.size()) {
                tableRow.addView(drawGrid(), trParams);
            } else {
                tableRow.addView(drawGrid(eleList.get(i)), trParams);
            }
        }
        return tableRow;
    }

    private void drawGrids() {
        for (int i = 0; i < rows; i++) {
            this.addView(drawTableRow(),
                    new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    private void drawGrids(List<List<String>> elements) {
        for (int i = 0; i < rows; i++) {
            if (i >= elements.size()) {
                this.addView(drawTableRow(),
                        new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            } else {
                this.addView(drawTableRow(elements.get(i)),
                        new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            }
        }
    }


    public void drawElements() {
        clearAll();
        if (elements == null || elements.isEmpty()) {
            drawGrids();
        } else {
            drawGrids(elements);
        }
        invalidate();
    }

    public void clearAll() {
        int size = this.getChildCount();
        for (int i = 0; i < size; i++) {
            TableRow tr = (TableRow) this.getChildAt(i);
            tr.removeAllViews();
        }
        this.removeAllViews();
    }

    /**
     * 根据索引值，注意从 0 开始
     *
     * @param row
     * @param col
     * @return
     */
    public TextGridView getElementAt(int row, int col) {
        return (TextGridView) ((TableRow) this.getChildAt(row)).getChildAt(col);
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public List<List<String>> getElements() {
        return elements;
    }

    public void setElements(List<List<String>> elements) {
        this.elements = elements;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getGridMargin() {
        return gridMargin;
    }

    public void setGridMargin(int gridMargin) {
        this.gridMargin = gridMargin;
    }

    public int getGridPadding() {
        return gridPadding;
    }

    public void setGridPadding(int gridPadding) {
        this.gridPadding = gridPadding;
    }
}
