import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import '../../Style/NavItem/Title.css';

class Title extends React.Component {
    render() {
        return (
            <div className="content">
                <Container fluid>
                    <Row>
                        <Col xl={12}>
                            <p className="title">{this.props.name}</p>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default Title;