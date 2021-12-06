import DateBox from "./DateBox";
import {ActivitySummary} from "./ActivitySummary";

import './Activity.css'
import {ActivityStats} from "./ActivityStats";
import {useState} from "react";
import ActivityPerformanceData from "./ActivityPerformanceData";

function Activity(props) {

    const [performanceDataVisible, setPerformanceDataVisible] = useState(false);

    function performanceVisibleHandler() {
        setPerformanceDataVisible((prevState) => {
            return !prevState;
        });
    }

    return (
        <div className="activity">
            <div className="activity-header">
                <DateBox item={props.item.date}/>
                <ActivitySummary item={props.item}/>
                <ActivityStats item={props.item}/>
            </div>
            <div className="activity-performance">
                {
                    performanceDataVisible ? <ActivityPerformanceData item={props.item}/>
                        : <div className="activity-performance__toggle" onClick={performanceVisibleHandler}>More...</div>
                }
            </div>
        </div>
    );
}

export default Activity;