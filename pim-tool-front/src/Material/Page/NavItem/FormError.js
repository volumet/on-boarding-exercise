import React from "react";
import Translate from "react-translate-component";

export default class FormError extends React.Component {
    render() {
        if (!this.props.valid) {
            return <h3><Translate content="formError.mandatory" /> </h3>
        }
        return null;
    }
}