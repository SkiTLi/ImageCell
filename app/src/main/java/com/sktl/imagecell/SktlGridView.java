package com.sktl.imagecell;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by USER-PC on 01.08.2017.
 */

public class SktlGridView extends View {
    private Paint gridPaint = new Paint(); //типа кисти или карандаш (по идее должна быть однаб но не факт)

    private List<Integer> arrayColumns;
    private List<Integer> arrayRows;

    private int lastVerticalLine;
    private int lastHorizontalLine;

    private int gridSpacing = 100;

//
//    private int gridWidth= getWidth();
//    private int gridHeight= getHeight();
    private int gridWidth = 3;
    private int gridHeight = 3;

    public List<Integer> getArrayColumns() {
        return arrayColumns;
    }

    public List<Integer> getArrayRows() {
        return arrayRows;
    }

    public int getGridSpacing() {
        return gridSpacing;
    }

    public void setGridSpacing(int gridSpacing) {
        this.gridSpacing = gridSpacing * 100;
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


    public int getLastVerticalLine() {
        return lastVerticalLine;
    }

    public int getLastHorizontalLine() {
        return lastHorizontalLine;
    }
    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //определяет начальные размеры вашего вью
//        //нужен он когда wrapperent н задать конкретные размеры
//    }


    //    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {


        gridPaint.setAntiAlias(true); // для сглаживния
        int color = ContextCompat.getColor(getContext(), R.color.colorAccent);
        gridPaint.setColor(color);



//        gridPaint.setStyle(Paint.Style.FILL); ///для заполнениия
        gridPaint.setStyle(Paint.Style.STROKE); ///для обводки
        gridPaint.setStrokeWidth(5);//толщина линии



    }

    //вызывается часто поэтому не создавть тяжелые объекты.
    // лучше в   initialize() а здесь просто использовать
    @Override
    protected void onDraw(Canvas canvas) { //канвас-лист бумаги

        super.onDraw(canvas);

//        gridWidth = getWidth();
//        gridHeight = getHeight();

         arrayColumns = new ArrayList<>();

        for (int i = 0; i < gridWidth; i += this.getGridSpacing()) {
            arrayColumns.add(i);
            lastVerticalLine=i;
        }


         arrayRows = new ArrayList<>();

        for (int j = 0; j < gridHeight; j += this.getGridSpacing()) {
            arrayRows.add(j);
            lastHorizontalLine=j;
        }


        for (Integer ax : arrayColumns) {

            canvas.drawLine(ax, 0, ax, lastHorizontalLine, gridPaint);
        }


        for (Integer ay : arrayRows) {

            canvas.drawLine(0, ay, lastVerticalLine, ay, gridPaint);
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SktlGridView(Context context) {
        super(context);
        initialize();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SktlGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public SktlGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SktlGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();

    }
}
