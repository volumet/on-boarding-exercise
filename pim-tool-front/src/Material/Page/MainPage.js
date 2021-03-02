import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Header from './Header';
import Navigation from './Navigation';
import ErrorScreen from './ErrorScreen';
import NewProject from "./NavItem/NewProject";
import ProjectList from "./ProjectList";

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
                        <Switch>
                            <Route exact path="/error" component={ErrorScreen}/>
                            <Route path="/">
                                <Row>
                                    <Col xl={1}/>
                                    <Col xl={2}>
                                        <Navigation/>
                                    </Col>
                                    <Col xl={9}>
                                        <Route exact path="/" component={ProjectList}/>
                                        <Route exact path="/new" component={() => <NewProject title={"newProject"}/>}/>
                                        <Route exact path="/edit"
                                               component={(props) => <NewProject {...props} title={"editProject"}/>}/>
                                    </Col>
                                </Row>
                            </Route>
                        </Switch>
                    </BrowserRouter>
                </Container>
            </div>
        )
    }
}

export default MainPage;