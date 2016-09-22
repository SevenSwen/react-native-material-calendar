
# react-native-material-calendar

## Getting started

`$ npm install react-native-material-calendar --save`

### Mostly automatic installation

`$ react-native link react-native-material-calendar`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.sevenswen.RNMaterialCalendarPackage;` to the imports at the top of the file
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

// TODO: What do with the module?
RNMaterialCalendar;
```
  