import './ActivitySummary.css'

export function ActivitySummary(props) {
    return (
        <div className="activitysummary">
            <div className="activitysummary-name">{props.item.name}</div>
            <div className="activitysummary-timeplace">
                {props.item.place} - {props.item.date.getHours().toString()}:{props.item.date.getMinutes().toString()}
                {/*{props.item.distance}km @ {props.item.averageSpeed} km/h*/}
            </div>
        </div>
    );
}

export default ActivitySummary;