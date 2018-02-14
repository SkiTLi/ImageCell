package com.sktl.imagecell;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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


    private int ImageWidth;
    private int ImageHeight;

    private int lastVerticalLine;
    private int lastHorizontalLine;

    private int gridSpacing = 100;

    public int getGridSpacing() {
        return gridSpacing;
    }

    public void setGridSpacing(int gridSpacing) {
        this.gridSpacing = gridSpacing * 100;
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
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        gridPaint.setColor(color);



//        gridPaint.setStyle(Paint.Style.FILL); ///для заполнениия
        gridPaint.setStyle(Paint.Style.STROKE); ///для обводки




    }

    //вызывается часто поэтому не создавть тяжелые объекты.
    // лучше в   initialize() а здесь просто использовать
    @Override
    protected void onDraw(Canvas canvas) { //канвас-лист бумаги

        super.onDraw(canvas);

        ImageWidth = getWidth();
        ImageHeight = getHeight();

        List<Integer> arrayColumns = new ArrayList<>();

        for (int i = 0; i < ImageWidth; i += this.getGridSpacing()) {
            arrayColumns.add(i);
            lastVerticalLine=i;
        }


        List<Integer> arrayRows = new ArrayList<>();

        for (int j = 0; j < ImageHeight; j += this.getGridSpacing()) {
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
