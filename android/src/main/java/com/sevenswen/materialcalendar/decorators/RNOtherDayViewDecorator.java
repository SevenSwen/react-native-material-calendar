package com.sevenswen.materialcalendar.decorators;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class RNOtherDayViewDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    private final int currentMonth;
    private static final int color = Color.parseColor("#f2f2f2");

    public RNOtherDayViewDecorator(int month) {
        currentMonth = month;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int month = calendar.get(Calendar.MONTH);
        return currentMonth != month;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ShapeDrawable(new Shape() {
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