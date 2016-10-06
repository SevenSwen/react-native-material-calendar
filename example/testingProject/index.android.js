import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    TouchableOpacity
} from 'react-native';

import RNMaterialCalendar from 'react-native-material-calendar'

const getRandomInt = () =>  Math.floor(Math.random() * 27 + 1);

const generateRandomEvents = date => new Array(getRandomInt())
    .fill(1)
    .map(() => new Date(date.getFullYear(), date.getMonth(), getRandomInt()));

class testingProject extends Component {

  state = {
    currentMonth: new Date(),
    selectedDate: null,
    eventsDates: []
  };

  componentDidMount() {
    this.calendar && this.calendar.setCurrentDate(this.state.currentMonth);
  }

  render() {
    return (
        <View style={styles.container}>
          <View style={{height:160,
                        backgroundColor: "gray",
                        alignItems: "center"}}>
            <View style={{flex: 1,
                        justifyContent: "center"}}>
              <Text style={styles.Text}>
                JS Elements:
              </Text>
            </View>
            <View style={{flex: 1,
                        flexDirection: "row"}}>
              <View style={styles.titleContainer}>
                <Text style={styles.Text}>
                  Current Month:
                </Text>
                <Text style={styles.titleValue}>
                  {this.state.currentMonth.toDateString()}
                </Text>
              </View>
              <View style={styles.titleContainer}>
                <Text style={styles.Text}>
                  Selected Date:
                </Text>
                <Text style={styles.titleValue}>
                  {this.state.selectedDate && this.state.selectedDate.toDateString()}
                </Text>
              </View>
            </View>
            <View style={{flex: 1,
                        flexDirection: "row"}}>
              <TouchableOpacity
                  style={styles.buttonContainer}
                  onPress={() => {
                  this.setState({currentMonth: new Date(
                    this.state.currentMonth.getFullYear(),
                    this.state.currentMonth.getMonth() - 1
                  )});
                  this.calendar && this.calendar.goToPrevious();
                }}>
                <Text style={styles.Text}>
                  Prev
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                  style={styles.buttonContainer}
                  onPress={() => {
                  this.setState({currentMonth: new Date()});
                  this.calendar && this.calendar.setCurrentDate(new Date());
                }}>
                <Text style={styles.Text}>
                  Today
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                  style={styles.buttonContainer}
                  onPress={() => {
                  this.setState({currentMonth: new Date(2016, 11)});
                  this.calendar && this.calendar.setCurrentDate(new Date(2016, 11));
                }}>
                <Text style={styles.Text}>
                  go to December
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                  style={styles.buttonContainer}
                  onPress={() => {
                  this.setState({currentMonth: new Date(
                    this.state.currentMonth.getFullYear(),
                    this.state.currentMonth.getMonth() + 1
                  )});
                  this.calendar && this.calendar.goToNext();
                }}>
                <Text style={styles.Text}>
                  Next
                </Text>
              </TouchableOpacity>
            </View>
            <View style={{flex: 1,
                        flexDirection: "row"}}>
              <TouchableOpacity
                  style={styles.buttonContainer}
                  onPress={() => this.setState({eventsDates: generateRandomEvents(this.state.currentMonth)})}>
                <Text style={styles.Text}>
                  Generate random events
                </Text>
              </TouchableOpacity>
              <View style={{flex: 2, justifyContent: "center"}}>
                <Text style={styles.Text}>
                  Native Calendar:
                </Text>
              </View>
            </View>
          </View>
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
        </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },
  titleContainer: {
    flex: 1,
    alignItems: "center"
  },
  Text: {
    fontWeight: "bold",
    textAlign: "center",
    color: "white"
  },
  titleValue: {
    flex: 1,
    color: "white"
  },
  buttonContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    margin: 3
  }
});

AppRegistry.registerComponent('testingProject', () => testingProject);
