
import { set } from 'date-fns';
import './App.css';
import StatsViewer from './components/getstats';
import MainHeader from './components/header';
import { useState } from 'react';
import DatedataFetcher from './components/DateDataFetcher';

function App() {
  const [selectedDate, setSelectedDate] = useState(null);
  console.log("Selected Date in App:", selectedDate);
  return (
    <div className="main-body">
    <MainHeader 
    selectedDate={selectedDate}
    setSelectedDate={setSelectedDate}/>
    <DatedataFetcher selectedDate={selectedDate} />
    
    
    












    </div>
  );
}

export default App;
