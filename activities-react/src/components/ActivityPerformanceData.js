import './ActivityPerformanceData.css';
import {useState, useEffect} from "react";
import {ResponsiveContainer, Line, LineChart, CartesianGrid, XAxis, YAxis, Tooltip, Legend} from "recharts";

function ActivityPerformanceData(props) {

    const [graphData, setGraphData] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/activity/' + props.item.id + '/records')
            .then(res => res.json())
            .then(
                (data) => {
                    console.log("Found " + data.length + " entries");
                    setGraphData(
                    data.map(line => {
                        return {
                            time: line.time,
                            heartRate: line.heartRate,
                            power: line.watts
                        };
                    }));
                }
            )
        }, [props.item.id]);

    return (
        <div className="activityperformancedata">
            <ResponsiveContainer width="100%" height="100%">
                <LineChart
                    width={500}
                    height={300}
                    data={graphData}
                    margin={{
                        top: 5,
                        right: 30,
                        left: 20,
                        bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="time" />
                    <YAxis yAxisId="left" />
                    <YAxis yAxisId="right" orientation="right" />
                    <Tooltip />
                    <Legend />
                    <Line yAxisId="left" type="monotone" dot={false} dataKey="heartRate" name="Heart Rate (BPM)" stroke="#8884d8"/>
                    <Line yAxisId="right" type="monotone" dot={false} dataKey="power" name="Power (W)" stroke="#82ca9d"/>
                </LineChart>
            </ResponsiveContainer>

        </div>);


}

export default ActivityPerformanceData;