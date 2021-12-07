import './ActivityPerformanceData.css';
import {useState, useEffect} from "react";
import {
    ResponsiveContainer,
    Line,
    LineChart,
    CartesianGrid,
    XAxis,
    YAxis,
    Tooltip,
    Brush,
} from "recharts";

function ActivityPerformanceData(props) {

    const [graphData, setGraphData] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/activity/' + props.item.id + '/records')
            .then(res => res.json())
            .then(
                (data) => {
                    // transforming the data

                    // 1. first select every 5th element, to reduce data size
                    data = data.filter(line => {
                        return line.time % 5 === 0;
                    });

                    // 2. now change the time to a sensible format, i.e.
                    data = data.map(line => {
                        let hours = Math.floor(line.time / 3600);
                        let remainder = line.time % 3600;
                        let minutes = Math.floor(remainder / 60);
                        if (minutes < 10) {
                            minutes = "0" + minutes;
                        }
                        let seconds = remainder % 60;
                        if (seconds < 10) {
                            seconds = "0" + seconds;
                        }
                        let time = "";
                        if (hours !== 0) {
                            time = time + hours + ":";
                        }
                        time = time + minutes + ":" + seconds;
                        return {
                            time: time,
                            heartRate: line.heartRate,
                            power: line.watts
                        }
                    });
                    setGraphData(
                        data.map(line => {
                            return {
                                time: line.time,
                                heartRate: line.heartRate,
                                power: line.power
                            };
                    }));
                }
            )
        }, [props.item.id]);

    return (
        <div className="activityperformancedata">
            <ResponsiveContainer width="100%" height={200}>
                <LineChart
                    width={500} height={150} data={graphData} syncId="anyId"
                    margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="time" />
                    <YAxis domain={['dataMin -10', 'dataMax + 10']}/>
                    <Tooltip  />
                    <Line isAnimationActive={false} type="monotone" dot={false} name=" Heart Rate" dataKey="heartRate" stroke="#8884d8" fill="#8884d8" />
                </LineChart>
            </ResponsiveContainer>

            <ResponsiveContainer width="100%" height={200}>
                <LineChart
                    width={500}
                    height={150}
                    data={graphData}
                    syncId="anyId"
                    margin={{
                        top: 10,
                        right: 30,
                        left: 0,
                        bottom: 0,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="time" />
                    <YAxis />
                    <Tooltip />
                    <Line isAnimationActive={false} type="monotone" dot={false} dataKey="power" stroke="#82ca9d" fill="#82ca9d" />
                    <Brush />
                </LineChart>
            </ResponsiveContainer>

        </div>);


}

export default ActivityPerformanceData;