import './DateBox.css';

function DateBox(props) {

    return (
        <div className="datebox">
            <div className="datebox-month">
                {props.item.toLocaleString('en-US', {month:"short"}).toUpperCase()}
            </div>
            <div className="datebox-day">
                {props.item.getUTCDate().toString()}
            </div>
        </div>
    )
}

export default DateBox;