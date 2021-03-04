import React from "react";
import Translate from "react-translate-component";
import "../../Style/NavItem/FormError.css"
import {Alert} from "react-bootstrap";

export default class FormError extends React.Component {
    constructor() {
        super();
    }

    render() {
        if (!this.props.valid) {
            console.log(this.props.show)
            return (
                <Alert variant="danger" dismissible onClose={this.props.handleClose} show={this.props.show}>
                    <Alert.Heading>
                        <Translate content="formError.mandatory"/>
                    </Alert.Heading>
                </Alert>
            );
        }
        return null;
    }
}