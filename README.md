
# react-native-material-calendar

## Getting started

`$ npm install react-native-material-calendar --save`

## Demonstration

[So smooth, such cool, very fast, wow](https://raw.githubusercontent.com/SevenSwen/react-native-material-calendar/development/Example.gif)

### Mostly automatic installation

`$ react-native link react-native-material-calendar`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.sevenswen.materialcalendar.RNMaterialCalendarPackage;` to the imports at the top of the file
  - Add `new RNMaterialCalendarPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-material-calendar'
  	project(':react-native-material-calendar').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-material-calendar/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-material-calendar')
  	```


## Usage
```javascript
import RNMaterialCalendar from 'react-native-material-calendar';

...
<RNMaterialCalendar
              ref={calendar => this.calendar = calendar}
              width={400}
              showDate="all"
              selectionMode="single"
              initDecorator={true}
              eventsDates={this.state.eventsDates}
              weekDayFormatter={["S","M","T","W","T","F","S"]}
              topbarVisible={false}
              onDateChange={(event) => {
                this.setState({selectedDate: new Date(event.date)})}
              }
              onMonthChange={(event) => {
                this.setState({currentMonth: new Date(event.date)})}
              }
          />
...

RNMaterialCalendar;
```
  