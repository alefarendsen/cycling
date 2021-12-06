import './ActivityStats.css'

export function ActivityStats(props) {
    return (
        <div className="activitystats">
            <div className="activitystats-item">
                <div className="activitystats-item__label">Elevation</div>
                <div className="activitystats-item__value">{props.item.elevationGain.toLocaleString()}m</div>
            </div>
            <div className="activitystats-item">
                <div className="activitystats-item__label">Distance</div>
                <div className="activitystats-item__value">{props.item.distance}km</div>
            </div>
            <div className="activitystats-item">
                <div className="activitystats-item__label">Avg. power</div>
                <div className="activitystats-item__value">{props.item.averagePower}W</div>
            </div>
        </div>
    );
}

export default ActivityStats;