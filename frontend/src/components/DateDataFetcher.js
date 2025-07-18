import { ca } from "date-fns/locale";
import dayjs from "dayjs";
import { useEffect } from "react";
import React, { useState } from "react";
import {
    Chart as ChartJS,
    CategoryScale,  
    LinearScale,
    BarElement,
    Tooltip,
    Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
ChartJS.register(
    CategoryScale, BarElement, LinearScale, Tooltip, Legend
);

const DatedataFetcher = ({ selectedDate }) => {
    const [data, setData] = useState(null);
    const [recordDate, setRecordDate] = useState(null);
    const[pushedCount, setPushedCount] = useState(null);
    const[pulledCount, setPulledCount] = useState(null);
    const[deffered, setDeffered] = useState(null);

    //creating chart component
    const ChartData ={
        labels: ['Pulled', 'Pushed', 'Deferred'],
        datasets: [
            {
                label: 'Counts',
                data: [pulledCount, pushedCount, deffered],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)',
                ],
                borderWidth: 1,
            },
        ],
    }

    useEffect(() => {

        const fetchData = () => {
            if (!selectedDate) return;
            const formattedDate = dayjs(selectedDate).format('YYYY-MM-DD'); // Format date as YYYY-MM-DD
            const encodedDate = encodeURIComponent(formattedDate);
            console.log("Fetching data for date:", formattedDate);
            fetch(`http://localhost:8080/kafka/home/stats?date=${encodedDate}`) // Replace with your actual endpoint
                .then(response => response.json())
                .then((data) => {
                    setData(data);
                    console.log("Data fetched:", data);

                    //set all fields
                    setRecordDate(data.recordDate);
                    setPushedCount(data.pushedCount);   
                    setPulledCount(data.pulledCount);
                    setDeffered(data.deffered);
                })
                .catch((error) => {
                    console.error('Error fetching stats:', error);
                    setData(null);
                    setRecordDate(null);
                    setPushedCount(null);
                    setPulledCount(null);
                    setDeffered(null);

                });

        };
        fetchData();
    }

        , [selectedDate]);


    if (!selectedDate) {
        return (<div>Please select a date.</div>);
    }

    if (data == null) {
        return (
            <div>No data exists for this date. Please select another date.</div>
        )
    }

    return (
        <div>
            <h3> Statistics for chosen date: </h3>
            <ul>
                <li>Pulled Count: {pulledCount}</li>
                <li>Pushed Count: {pushedCount}</li>
                <li>Deferred : {deffered}</li>
                
                <li>Record Date: {recordDate}</li>
            </ul>
            <div style= {{ width: '600px', height: '400px' }}>
                <Bar data={ChartData} options={{ responsive: true }} />
            </div>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
    );
}
export default DatedataFetcher;