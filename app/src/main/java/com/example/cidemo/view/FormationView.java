package com.example.cidemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.cidemo.R;
import com.example.cidemo.model.FormationPositions;

import java.util.ArrayList;
import java.util.List;

class Point
{
    float x, y;
}

public class FormationView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private ArrayList<FormationPositions> positions;

    private float mX, mY;
    List<Point> pointsDown = new ArrayList<Point>();
    List<Point> pointsUp = new ArrayList<Point>();
    int currentContact = -1;

    private Resources mResources;

    public FormationView(Context context) {
        this(context, null);
    }

    public FormationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        mResources = getResources();
    }

    public void init(DisplayMetrics metrics, ArrayList<FormationPositions> formationPositions) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        positions = formationPositions;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        // draw footer
        for (int i = 0; i < positions.size(); i++) {
            Bitmap imageBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.ic_soccer_jersey)).getBitmap();
            canvas.drawBitmap(imageBitmap, positions.get(i).getX(), positions.get(i).getY(), mPaint);
        }

        mCanvas = canvas;

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentContact++;
                pointsDown.add(new Point());
                pointsDown.get(currentContact).x = x;
                pointsDown.get(currentContact).y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                pointsUp.add(new Point());
                pointsUp.get(currentContact).x = x;
                pointsUp.get(currentContact).y = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mX = x;
                mY = y;
                pointsUp.add(new Point());
                pointsUp.get(currentContact).x = x;
                pointsUp.get(currentContact).y = y;
                invalidate();
                break;
        }

        return true;
    }
}
