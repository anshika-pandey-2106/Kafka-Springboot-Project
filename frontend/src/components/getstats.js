import { useEffect, useState } from 'react';

function StatsViewer() {
  const [stats, setStats] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/kafka/home/stats') // Replace with your actual endpoint
      .then(response => response.json())
      .then(data => setStats(data))
      .catch(error => console.error('Error fetching stats:', error));
  }, []);

  return (
    <div>
      <h2>Date-wise Message Stats</h2>
      <ul>
        {stats.map((entry, index) => {
        console.log(entry);
        return (
          <li key={index}>
            <strong>{entry.recordDate}:</strong> Sent {entry.pulledCount}, Received {entry.pushedCount}
          </li>
        );})}
      </ul>
    </div>
  );
}
export default StatsViewer;
