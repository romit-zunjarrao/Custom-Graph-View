package com.example.customviewassignment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class LineGraphView extends View {

    Paint mPaint;
    int margin,width,height;
    ArrayList<DataObject> list;
    Vector<Coordinates> coordinates;
    Boolean drawLine= false;
    int maxColor, pointColor, lineColor, radius;
    public LineGraphView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.graph);
        pointColor = array.getColor(R.styleable.graph_pointColor,Color.RED);
        lineColor = array.getColor(R.styleable.graph_lineColor,Color.GREEN);
        maxColor = array.getColor(R.styleable.graph_maxColor, Color.MAGENTA);
        array.recycle();
        initPaints();
    }


    public void initPaints()
    {
        mPaint = new Paint();
        margin = dpToPx(30);
        mPaint.setStrokeWidth(dpToPx(5));
        radius = dpToPx(5);
        coordinates = new Vector<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        drawGraph(canvas);
    }

    public Boolean getDrawLine() {
        return drawLine;
    }

    public void setDrawLine(Boolean drawLine) {
        this.drawLine = drawLine;
    }

    public void drawGraph(Canvas canvas)
    {
        width = getWidth();
        height = getHeight();
        canvas.drawLine(margin,margin,margin,height-margin,mPaint);
        canvas.drawLine(margin,height-margin,width-margin,height-margin,mPaint);
        if(list !=null  && list.size()>0) {

            mPaint.setTextSize(dpToPx(15));
            canvas.drawText("0",dpToPx(10),height-margin,mPaint);

            int max = getMaxFromList();
            canvas.drawText(""+max,margin-dpToPx(20),margin,mPaint);

            int totalElement = 1;
            int pointX = (width-margin)/list.size()/2 +margin;
            for(int i=0; i<list.size();i++)
            {
                if(list.get(i).getStudentCount() == max)
                    mPaint.setColor(maxColor);
                else
                    mPaint.setColor(pointColor);
                canvas.drawCircle(pointX  , height-(height *list.get(i).getStudentCount()/max)+margin+dpToPx(5), radius, mPaint);

                coordinates.add(new Coordinates(pointX,height-(height *list.get(i).getStudentCount()/max)+margin+dpToPx(5)));

                canvas.drawText(""+list.get(i).getDate().toString(),pointX, height ,mPaint);
                pointX+= (width-margin)/list.size();
                if(drawLine)
                drawLine(canvas);
            }
        }
    }

    public void drawLine(Canvas canvas)
    {
       for(int i =0; i<coordinates.size()-1;i++)
       {
           this.invalidate();
           canvas.drawLine(coordinates.get(i).x, coordinates.get(i).y,coordinates.get(i+1).x,coordinates.get(i+1).y, mPaint);

       }
    }

    public void addList(ArrayList<DataObject> list)
    {
        this.list = list;
    }

    public int getMaxFromList()
    {
        int max = list.get(0).getStudentCount();
        for(int i= 1; i<list.size();i++)
        {
            if(max<list.get(i).getStudentCount())
            {
                max = list.get(i).getStudentCount();
            }
        }
        return max;
    }

    private int dpToPx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
    }

    public void setRadius(int radius)
    {
        this.radius = dpToPx(radius);
    }
}
