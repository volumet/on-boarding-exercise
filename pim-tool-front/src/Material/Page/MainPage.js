import React from 'react';
import {Container, Row, Col} from 'react-bootstrap';
import {BrowserRouter, Route} from 'react-router-dom';
import Header from './Header';
import Navigation from './Navigation';
import ErrorScreen from './ErrorScreen';

class MainPage extends React.Component {
    render() {
        return (
            <div className="main">
                <Container fluid>
                    <Row>
                        <Col>
                            <Header/>
                        </Col>
                    </Row>
                    <BrowserRouter>
                        <Row>
                            <Col xl={1}/>
                            <Col xl={2}>
                                <Route exact path="/" component={Navigation}/>
                            </Col>
                            <Col xl={9}>
                            </Col>
                            <Col>
                                <Route path="/error" component={ErrorScreen}/>
                            </Col>
                        </Row>
                    </BrowserRouter>
                </Container>
            </div>
        )
    }
}

export default MainPage;