'use strict';

import React, { Component, PropTypes } from 'react';
import {
    NativeModules,
    View
} from 'react-native';

const { RNMaterialCalendar } = NativeModules;

const FIRST_DAY_OF_WEEK = [
    'monday',
    'tuesday',
    'wednesday',
    'thursday',
    'friday',
    'saturday',
    'sunday'
];

const SHOWING_DATE = [
    'all',
    'current'
];

const SELECTION_MODES = [
    'none',
    'single',
    'multiple'
];

function colorType(props, propName, componentName) {
    var checker = function() {
        var color = props[propName];
        var regex = /^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$/;
        if (!regex.test(color)) {
            return new Error('Only accept color formats: #RRGGBB and #AARRGGBB');
        }
    };

    return PropTypes.string(props, propName, componentName) || checker();
}

class Calendar extends Component {
    constructor() {
        super();
        this._onDateChange = this._onDateChange.bind(this);
    }

    _onDateChange(event) {
        this.props.onDateChange && this.props.onDateChange(event.nativeEvent);
    }

    render() {
        var { style, ...rest } = this.props,
            width = rest.width,
            height = rest.topbarVisible ? width / 7 * 8 : width;

        style = {
            ...style,
            width,
            height
        };

        return (
            <RNMaterialCalendar
                {...rest}
                style={style}
                onDateChange={this._onDateChange} />
        );
    }
}

Calendar.propTypes = {
    ...View.propTypes,
    width: PropTypes.number.isRequired,
    topbarVisible: PropTypes.bool,
    arrowColor: colorType,
    firstDayOfWeek: PropTypes.oneOf(FIRST_DAY_OF_WEEK),
    showDate: PropTypes.oneOf(SHOWING_DATE),
    currentDate: PropTypes.arrayOf(PropTypes.oneOfType([ PropTypes.string, PropTypes.number ])),
    selectionMode: PropTypes.oneOf(SELECTION_MODES),
    selectionColor: colorType,
    selectedDates: PropTypes.arrayOf(PropTypes.oneOfType([ PropTypes.string, PropTypes.number ]))
};

Calendar.defaultProps = {
    topbarVisible: true
}

export default Calendar;
