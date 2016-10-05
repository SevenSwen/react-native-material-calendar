package com.sevenswen.materialcalendar;

import android.view.ViewGroup;

import com.facebook.react.uimanager.ThemedReactContext;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class RNMaterialCalendarView extends MaterialCalendarView {

    public RNMaterialCalendarView(ThemedReactContext context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
    }

    private final Runnable mLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(mLayoutRunnable);
    }
}
