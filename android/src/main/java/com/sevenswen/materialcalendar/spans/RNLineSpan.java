package com.sevenswen.materialcalendar.spans;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.text.style.LineBackgroundSpan;

public class RNLineSpan implements LineBackgroundSpan {
    private final int color;

    public RNLineSpan() {
        this.color = 0;
    }

    public RNLineSpan(int color) {
        this.color = color;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        canvas.drawRect(
                ((right + left) / 2 - canvas.getWidth() / 2) + canvas.getWidth() * 0.1f,
                ((top + bottom) / 2 - canvas.getHeight() / 2) + canvas.getHeight() * 0.8f,
                ((right + left) / 2 - canvas.getWidth() / 2) + canvas.getWidth() * 0.9f,
                ((top + bottom) / 2 - canvas.getHeight() / 2) + canvas.getHeight() * 0.9f,
                paint);
        paint.setColor(oldColor);
    }
}