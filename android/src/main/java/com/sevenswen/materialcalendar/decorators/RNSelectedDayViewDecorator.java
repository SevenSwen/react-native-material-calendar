package com.sevenswen.materialcalendar.decorators;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class RNSelectedDayViewDecorator implements DayViewDecorator {

    private static final int color = Color.parseColor("#7f000000");

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                int oldColor = paint.getColor();
                if (color != 0) {
                    paint.setColor(color);
                }
                canvas.drawRect((float)canvas.getWidth() * (float)0.1,
                        (float)canvas.getHeight() * (float)0.1,
                        (float)canvas.getWidth() * (float)0.9,
                        (float)canvas.getHeight() * (float)0.9,
                        paint);
                paint.setColor(oldColor);
            }
        }));
    }
}