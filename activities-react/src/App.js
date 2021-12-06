import './App.css';

import {useEffect, useState} from "react";
import ActivitiesList from "./components/ActivitiesList";

const INTIAL_ACTIVITIES = []

function convertFromStringToDate(responseDate) {
    let dateComponents = responseDate.split('T');
    let datePieces = dateComponents[0].split("-");
    let timePieces = dateComponents[1].split(":");
    return (new Date(datePieces[2], (datePieces[1] - 1), datePieces[0],
        timePieces[0], timePieces[1], timePieces[2].split("+")[0]))
}

convertFromStringToDate("21-03-2020T11:20:30")

function App() {

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [activities, setActivities] = useState(INTIAL_ACTIVITIES);

    useEffect(() => {
        fetch("http://localhost:8080/activity")
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    let enrichedData = data.map(item => {
                        return {
                            ...item,
                            date: convertFromStringToDate(item.date),
                            elevationGain: 1234,
                            distance: 34.5,
                            averagePower: 235,
                            duration: "2h12m",
                            place: "Carolles, France",
                            averageSpeed: 31.5
                        }
                    });

                    setActivities(enrichedData);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <ActivitiesList items={activities}/>
        );
    }
}

export default App;
