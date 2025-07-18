import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './CalendarDropdown.css';


const CalendarDropdown = ({selectedDate, setSelectedDate}) => {
  //const [selectedDate, setSelectedDate] = useState(null);
  const [showCalendar, setShowCalendar] = useState(false);

  const toggleCalendar = () => setShowCalendar(!showCalendar);

  return (
    <div className="calendar-container">
      <button className="calendar-button" onClick={toggleCalendar}>
        {selectedDate ? selectedDate.toLocaleDateString() : 'Select Date'}
      </button>
      {showCalendar && (
        <div className="calendar-dropdown">
          <DatePicker
            selected={selectedDate}
            onChange={(date) => {
              setSelectedDate(date);
              setShowCalendar(false);
             
            }}
            inline
          />
        </div>
      )}
    </div>
  );
};

export default CalendarDropdown;
