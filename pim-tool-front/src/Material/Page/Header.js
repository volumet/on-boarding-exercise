import React from 'react';
import Translate from 'react-translate-component';
import {Button, Col, Container, Row} from 'react-bootstrap';
import '../Style/Header.css';
import logo from '../Images/logo_elca.png';


class Header extends React.Component {

    render() {
        return (
            <div className="content">
                <Container fluid>
                    <Row>
                        <Col xl={1}>
                        </Col>
                        <Col xl={1}>
                            <img className="logo" src={logo} alt="logo"/>
                        </Col>
                        <Col xl={6}>
                            <p className="name">
                                <Translate content="header.name"/>
                            </p>
                        </Col>
                        <Col xl={4}>
                            <Button bsPrefix className="languageButton" onClick={this.props.langVI}>VI</Button>
                            <Button bsPrefix className="languageButton" onClick={this.props.langEN}>EN</Button>
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }
}

export default Header