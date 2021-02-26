import React from "react";
import Translate from "react-translate-component";
import "../../Style/NavItem/FormError.css"

export default class FormError extends React.Component {
    render() {
        if (!this.props.valid) {
            return <h4 className="mandatory"><Translate content="formError.mandatory" /> </h4>
        }
        return null;
    }
}