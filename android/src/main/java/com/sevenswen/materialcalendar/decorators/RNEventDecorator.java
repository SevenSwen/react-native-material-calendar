package com.sevenswen.materialcalendar.decorators;

import com.sevenswen.materialcalendar.spans.RNLineSpan;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class RNEventDecorator implements DayViewDecorator {

    private HashSet<CalendarDay> dates;
    private static final int color = Color.parseColor("#55b14b");

    public RNEventDecorator(Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return (dates != null) && dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new RNLineSpan(color));
    }
}