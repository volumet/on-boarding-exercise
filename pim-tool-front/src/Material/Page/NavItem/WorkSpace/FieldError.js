import React from 'react';
import Translate from "react-translate-component";
import "../../../Style/NavItem/WorkSpace/FieldError.css"

class FieldError extends React.Component {

    render() {
        if (this.props.error) {
            return (
                <p className="error-message">
                    <Translate content={"fieldError." + this.props.field} />
                    <Translate content="fieldError.error_line" />
                </p>
            );
        }
        else
            return null;
    }
}

export default FieldError;