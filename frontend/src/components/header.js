import CalendarDropdown from './calendar.js';
import React, { useState } from 'react';

function MainHeader({selectedDate, setSelectedDate}) {

return (
<>
<header class="header">
  <h1 class="title">Kafka Metrics Dashboard</h1>
  <CalendarDropdown
  selectedDate={selectedDate} 
  setSelectedDate={setSelectedDate} />
</header>

</>
)

}
export default MainHeader;