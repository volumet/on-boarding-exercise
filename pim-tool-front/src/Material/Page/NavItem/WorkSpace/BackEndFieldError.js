import React from "react";
import Translate from "react-translate-component";
import '../../../Style/NavItem/WorkSpace/BackEndFieldError.css';

export default class BackEndFieldError extends React.Component {
    render() {
        if (this.props.errorMessageKey === this.props.preferedKey) {
            return (
                <p id="error"><Translate
                    content={"backEndFieldError." +
                    this.props.preferedKey.substr(this.props.preferedKey.lastIndexOf(".") + 1)}/>
                </p>
            );
        }
        return null;
    }
}