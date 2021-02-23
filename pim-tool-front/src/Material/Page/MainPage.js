import React from 'react';
import {Container, Row, Col} from 'react-bootstrap';
import {BrowserRouter, Route} from 'react-router-dom';
import Header from './Header';
import Navigation from './Navigation';
import ErrorScreen from './ErrorScreen';
import ProjectTest from './NavItem/ProjectTest';
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
                        <Row>
                            <Col xl={1}/>
                            <Col xl={2}>
                                <Route path="/" component={Navigation}/>
                            </Col>
                            <Col xl={9}>
                                <Route exact path="/" component={ProjectList}/>
                                {/*<Route path="/project_list" component={} />*/}
                                <Route path="/new" component={() => <NewProject title={"newProject"}/>} />
                                <Route path="/edit" component={(props) => <NewProject {...props} title={"editProject"} />} />
                                <Route path="/project" component={ProjectTest} />
                                {/*<Route path="/customer" component={} />*/}
                                {/*<Route path="/supplier" component={} />*/}
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