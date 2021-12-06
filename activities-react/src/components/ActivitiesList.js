import './ActivitiesList.css'
import Card from "../UI/Card";
import Activity from "./Activity";

function ActivitiesList(props) {

    return (
        <div>
            {props.items.map(item => {
                return (
                    <Card className="total">
                        <Activity item={item}/>
                    </Card>
                );
            })}
        </div>);
}

export default ActivitiesList;
