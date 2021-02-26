import React from 'react';
import Translate from 'react-translate-component';
import {Row, Col, Nav, Navbar} from 'react-bootstrap';
import '../Style/Navigation.css';
import counterpart from 'counterpart';
import en from '../lang/en';

counterpart.registerTranslations('en', en);

class Navigation extends React.Component {
    render() {
        return (
            <Navbar collapseOnSelect expand="lg">
                <Navbar.Brand/>
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="navigation">
                        <Row>
                            <Col xl={12}>
                                <Nav.Link href="/" disabled="true">
                                    <p className="text-semi-bold first-element second-element">
                                        <Translate content="navigation.title"/>
                                    </p>
                                </Nav.Link>
                                <Nav.Link disabled="true">
                                    <p className="text-semi-bold second-element">
                                        <Translate content="navigation.new"/>
                                    </p>
                                </Nav.Link>
                                <Nav.Link href="/new"><Translate content="navigation.project" className="normal-color" /></Nav.Link>
                                <Nav.Link disabled="true"><Translate content="navigation.customer" className="normal-color" /></Nav.Link>
                                <Nav.Link disabled="true"><Translate content="navigation.supplier" className="normal-color" /></Nav.Link>
                            </Col>
                        </Row>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default Navigation;