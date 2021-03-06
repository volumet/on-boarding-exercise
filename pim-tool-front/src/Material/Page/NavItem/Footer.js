import React from 'react';
import '../../Style/NavItem/Footer.css';
import Translate from "react-translate-component";

class Footer extends React.Component {
    cancelHandler() {
        window.location = "/";
    }
    render() {
        return (
            <React.Fragment>
                <div className="button-group">
                    <Translate component="button" as="input" type="reset" onClick={this.cancelHandler} className="footer-button-a" content={"footer.cancel"}/>
                    <Translate component="button" as="input" type="submit" className="footer-button-b" content={"footer.submit"}/>
                </div>
            </React.Fragment>
        );
    }
}

export default Footer;