import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Header from './Header';
import Navigation from './Navigation';
import ErrorScreen from './ErrorScreen';
import NewProject from "./NavItem/NewProject";
import ProjectList from "./ProjectList";
import counterpart from "counterpart";
import en from "../lang/en";


counterpart.registerTranslations('en', en);

class MainPage extends React.Component {

    componentDidMount() {
        if (sessionStorage.getItem('lang') !== null) {
            counterpart.setLocale(sessionStorage.getItem('lang'))
        }
    }

    handleClickVI = event => {
        sessionStorage.setItem('lang', 'vi');
        counterpart.setLocale('vi')
    }
    handleClickEN = event => {
        sessionStorage.setItem('lang', 'en');
        counterpart.setLocale('en')
    }

    render() {
        return (
            <div className="main">
                <Container fluid>
                    <Row>
                        <Col xl={12}>
                            <Header langVI={this.handleClickVI} langEN={this.handleClickEN}/>
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