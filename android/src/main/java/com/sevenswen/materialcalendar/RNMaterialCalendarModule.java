package com.sevenswen.materialcalendar;

import android.graphics.Color;
import android.os.SystemClock;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;

import com.facebook.infer.annotation.Assertions;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;

import com.sevenswen.materialcalendar.events.DateChangeEvent;
import com.sevenswen.materialcalendar.events.MonthChangeEvent;

import com.sevenswen.materialcalendar.decorators.RNDayViewDecorator;
import com.sevenswen.materialcalendar.decorators.RNEventDecorator;
import com.sevenswen.materialcalendar.decorators.RNOtherDayViewDecorator;
import com.sevenswen.materialcalendar.decorators.RNSelectedDayViewDecorator;
import com.sevenswen.materialcalendar.decorators.RNTodayViewDecorator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashSet;
import java.util.Calendar;

import javax.annotation.Nullable;

public class RNMaterialCalendarModule extends SimpleViewManager<RNMaterialCalendarView> {

  public static final String EVENT_PROP_DATE_CHANGES = "orientation";

  private static final String MODULE_NAME = "RNMaterialCalendar";

  public static final int COMMAND_GO_TO_NEXT = 1;
  public static final int COMMAND_GO_TO_PREVIOUS = 2;
  public static final int COMMAND_SET_CURRENT_DATE = 3;

  @Override
  public String getName() { return MODULE_NAME; }

  // -------------------------------------------------------------------- //

  private static final String DATE_FORMAT = "yyyy/MM/dd";
  private static final DateFormat dateFormat;

  public static boolean TopbarVisible;

  private RNMaterialCalendarView widget;

  private  static HashSet<CalendarDay> eventsDatesSet = new HashSet<>();

  static {
    dateFormat = new SimpleDateFormat(DATE_FORMAT);
  }

  private static final String COLOR_REGEX = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$";


  @Override
  protected RNMaterialCalendarView createViewInstance(ThemedReactContext context) {
    widget = new RNMaterialCalendarView(context);
    return widget;
  }

  @Nullable
  @Override
  public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.<String, Object>builder()
            .put(
                    "topDateChange",
                    MapBuilder.of(
                            "phasedRegistrationNames",
                            MapBuilder.of(
                                    "bubbled", "onDateChange", "captured", "onDateChangeCapture")))
            .put(
                    "topMonthChange",
                    MapBuilder.of(
                            "phasedRegistrationNames",
                            MapBuilder.of(
                                    "bubbled", "onMonthChange", "captured", "onMonthChangeCapture")))
            .build();
  }

  @Override
  protected void addEventEmitters(final ThemedReactContext reactContext, final RNMaterialCalendarView view) {
    view.setOnDateChangedListener(new OnDateSelectedListener() {
      @Override
      public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher()
                .dispatchEvent(new DateChangeEvent(
                        view.getId(),
                        date,
                        selected));
      }
    });
    view.setOnMonthChangedListener(new OnMonthChangedListener() {
      @Override
      public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher()
                .dispatchEvent(new MonthChangeEvent(
                        view.getId(),
                        date));

        widget.removeDecorators();
        widget.addDecorators(
                new RNDayViewDecorator(date.getMonth()),
                new RNOtherDayViewDecorator(date.getMonth()),
                new RNSelectedDayViewDecorator(),
                new RNTodayViewDecorator(),
                new RNEventDecorator(eventsDatesSet)
        );
      }
    });
  }

  @Override
  public Map<String,Integer> getCommandsMap() {
    return MapBuilder.of(
            "goToNext",
            COMMAND_GO_TO_NEXT,
            "goToPrevious",
            COMMAND_GO_TO_PREVIOUS,
            "setCurrentDate",
            COMMAND_SET_CURRENT_DATE);
  }

  @Override
  public void receiveCommand(
          RNMaterialCalendarView widget,
          int commandType,
          @Nullable ReadableArray args) {
    Assertions.assertNotNull(widget);
    Assertions.assertNotNull(args);
    switch (commandType) {
      case COMMAND_GO_TO_NEXT: {
        widget.goToNext();
        return;
      }
      case COMMAND_GO_TO_PREVIOUS: {
        widget.goToPrevious();
        return;
      }
      case COMMAND_SET_CURRENT_DATE: {
        widget.setCurrentDate(
                new CalendarDay(
                        args.getArray(0).getInt(0),
                        args.getArray(0).getInt(1),
                        args.getArray(0).getInt(2)));
        return;
      }
      default:
        throw new IllegalArgumentException(String.format(
                "Unsupported command %d received by %s.",
                commandType,
                getClass().getSimpleName()));
    }
  }

  @ReactProp(name = "weekDayFormatter")
  public void setInitWeekDayFormatter(RNMaterialCalendarView view, ReadableArray weekDayFormatter) {
    if (weekDayFormatter != null) {
      List<String> weekDayFormattersList = new ArrayList<String>();
      for (int i = 0; i < weekDayFormatter.size(); i++) {
        weekDayFormattersList.add(weekDayFormatter.getString(i));
      }
      final CharSequence[] cs = weekDayFormattersList.toArray(new CharSequence[weekDayFormattersList.size()]);
      view.setWeekDayFormatter(new ArrayWeekDayFormatter(cs));
    }
  }

  @ReactProp(name = "initDecorator")
  public void setInitDecorator(RNMaterialCalendarView view, boolean initDecorator) {
    if (initDecorator) {
      CalendarDay day = view.getCurrentDate();

      view.addDecorators(
              new RNDayViewDecorator(day.getMonth()),
              new RNOtherDayViewDecorator(day.getMonth()),
              new RNSelectedDayViewDecorator(),
              new RNTodayViewDecorator()
      );
    }
  }

  @ReactProp(name = "eventsDates")
  public void setEventsDates(RNMaterialCalendarView view, ReadableArray eventsDates) {
    if (eventsDates != null) {
      CalendarDay day = view.getCurrentDate();

      eventsDatesSet = new HashSet<>();
      for (int i = 0; i < eventsDates.size(); i++) {
        eventsDatesSet.add(
                new CalendarDay(
                        eventsDates.getArray(i).getInt(0),   // year
                        eventsDates.getArray(i).getInt(1),   // month
                        eventsDates.getArray(i).getInt(2))); // day
      }

      widget.removeDecorators();
      view.addDecorators(
              new RNDayViewDecorator(day.getMonth()),
              new RNOtherDayViewDecorator(day.getMonth()),
              new RNSelectedDayViewDecorator(),
              new RNTodayViewDecorator(),
              new RNEventDecorator(eventsDatesSet)
      );
    }
  }

  @ReactProp(name = "topbarVisible")
  public void setTopbarVisible(RNMaterialCalendarView view, boolean topbarVisible) {
    TopbarVisible = topbarVisible;
    view.setTopbarVisible(topbarVisible);
  }

  @ReactProp(name = "arrowColor")
  public void setArrowColor(RNMaterialCalendarView view, String color) {
    if (color != null) {
      if (color.matches(COLOR_REGEX)) {
        view.setArrowColor(Color.parseColor(color));
      } else {
        throw new JSApplicationIllegalArgumentException("Invalid arrowColor property: " + color);
      }
    }
  }

  @ReactProp(name = "firstDayOfWeek")
  public void setFirstDayOfWeek(RNMaterialCalendarView view, String firstDayOfWeek) {
    if (firstDayOfWeek != null) {
      view.state().edit()
              .setFirstDayOfWeek(getFirstDayOfWeekFromString(firstDayOfWeek))
              .commit();
    }
  }

  @ReactProp(name = "showDate")
  public void setShowDate(RNMaterialCalendarView view, String showDate) {
    if (showDate != null) {
      if (showDate.equals("all")) {
        view.setShowOtherDates(MaterialCalendarView.SHOW_OTHER_MONTHS);
      } else if (showDate.equals("current")) {
        view.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
      } else {
        throw new JSApplicationIllegalArgumentException("Unknown showDate property: " + showDate);
      }
    }
  }

  @ReactProp(name = "currentDate")
  public void setCurrentDate(RNMaterialCalendarView view, ReadableArray data) {
    String type = data.getType(0).name();
    if ("String".equals(type)) {
      try {
        Date date = dateFormat.parse(data.getString(0));
        view.setCurrentDate(date);
      } catch (ParseException e) {
        throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
      }
    } else if ("Number".equals(type)) {
      Double value = data.getDouble(0);
      Date date = new Date(value.longValue());
      view.setCurrentDate(date);
    } else {
      throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
    }
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(RNMaterialCalendarView view, ReadableArray data) {
    String type = data.getType(0).name();
    if ("String".equals(type)) {
      try {
        Date date = dateFormat.parse(data.getString(0));
        if (shouldUpdateMinMaxDate(view.getMinimumDate(), date)) {
          view.state().edit()
                  .setMinimumDate(date)
                  .commit();
        }
      } catch (ParseException e) {
        throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
      }
    } else if ("Number".equals(type)) {
      Double value = data.getDouble(0);
      Date date = new Date(value.longValue());
      if (shouldUpdateMinMaxDate(view.getMinimumDate(), date)) {
        view.state().edit()
                .setMinimumDate(date)
                .commit();
      }
    } else {
      throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
    }
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(RNMaterialCalendarView view, ReadableArray data) {
    String type = data.getType(0).name();

    if ("String".equals(type)) {
      try {
        Date date = dateFormat.parse(data.getString(0));
        if (shouldUpdateMinMaxDate(view.getMaximumDate(), date)) {
          view.state().edit()
                  .setMaximumDate(date)
                  .commit();
        }
      } catch (ParseException e) {
        throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
      }
    } else if ("Number".equals(type)) {
      Double value = data.getDouble(0);
      Date date = new Date(value.longValue());

      if (shouldUpdateMinMaxDate(view.getMaximumDate(), date)) {
        view.state().edit()
                .setMaximumDate(date)
                .commit();
      }
    } else {
      throw new JSApplicationIllegalArgumentException("Invalid date format: " + data.getString(0));
    }
  }

  @ReactProp(name = "selectionMode")
  public void setSelectionMode(RNMaterialCalendarView view, String mode) {
    if (mode != null) {
      if (mode.equals("none")) {
        view.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
      } else if (mode.equals("single")) {
        view.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
      } else if (mode.equals("multiple")) {
        view.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
      } else {
        throw new JSApplicationIllegalArgumentException("Unknown selectionMode property: " + mode);
      }
    }
  }

  @ReactProp(name = "selectionColor")
  public void setSelectionColor(RNMaterialCalendarView view, String color) {
    if (color != null) {
      if (color.matches(COLOR_REGEX)) {
        view.setSelectionColor(Color.parseColor(color));
      } else {
        throw new JSApplicationIllegalArgumentException("Invalid selectionColor property: " + color);
      }
    }
  }

  @ReactProp(name = "selectedDates")
  public void setSelectedDates(RNMaterialCalendarView view, ReadableArray dates) {
    ArrayList<Date> selectedDates = new ArrayList<Date>();
    for (int i = 0; i < dates.size(); i++) {
      String type = dates.getType(i).name();
      if ("String".equals(type)) {
        try {
          Date date = dateFormat.parse(dates.getString(i));
          selectedDates.add(date);
        } catch (ParseException e) {
          throw new JSApplicationIllegalArgumentException("Invalid date format: " + dates.getString(i));
        }
      } else if ("Number".equals(type)) {
        Double value = dates.getDouble(i);
        Date date = new Date(value.longValue());
        selectedDates.add(date);
      } else {
        throw new JSApplicationIllegalArgumentException("Invalid date format: " + dates.getString(i));
      }
    }
    for (Date date : selectedDates) {
      view.setDateSelected(date, true);
    }
  }

  private int getFirstDayOfWeekFromString(String firstDayOfWeek) {
    if (firstDayOfWeek.equals("monday")) {
      return Calendar.MONDAY;
    } else if (firstDayOfWeek.equals("tuesday")) {
      return Calendar.TUESDAY;
    } else if (firstDayOfWeek.equals("wednesday")) {
      return Calendar.WEDNESDAY;
    } else if (firstDayOfWeek.equals("thursday")) {
      return Calendar.THURSDAY;
    } else if (firstDayOfWeek.equals("friday")) {
      return Calendar.FRIDAY;
    } else if (firstDayOfWeek.equals("saturday")) {
      return Calendar.SATURDAY;
    } else if (firstDayOfWeek.equals("sunday")) {
      return Calendar.SUNDAY;
    } else {
      throw new JSApplicationIllegalArgumentException("Unknown firstDayOfWeek property: " + firstDayOfWeek);
    }
  }

  /**
   * Should update new value of minimum or maximum date
   *
   * Check if the new min or max date is different from the previous one, if yes we update otherwise we don't.
   *
   * @param minMaxDate
   * @param newDate
   * @return boolean
   */
  private boolean shouldUpdateMinMaxDate(CalendarDay minMaxDate, Date newDate) {
    if (minMaxDate == null) {
      return true;
    }

    Calendar newDateCalendar = Calendar.getInstance();
    newDateCalendar.setTimeInMillis(newDate.getTime());

    return (minMaxDate.getYear() != newDateCalendar.get(Calendar.YEAR) &&
            minMaxDate.getMonth() != newDateCalendar.get(Calendar.MONTH) &&
            minMaxDate.getDay() != newDateCalendar.get(Calendar.DAY_OF_MONTH));
  }
}