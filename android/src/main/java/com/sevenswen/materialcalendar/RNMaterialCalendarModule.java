package com.sevenswen.materialcalendar;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNMaterialCalendarModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNMaterialCalendarModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNMaterialCalendar";
  }
}