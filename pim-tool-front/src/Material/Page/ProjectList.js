import React from 'react';
import {Button, ButtonGroup, Col, Container, DropdownButton, Form, Row, Table} from "react-bootstrap";
import axios from 'axios';
import TotalSelectedProject from "./NavItem/TotalSelectedProject";
import {Link} from "react-router-dom";
import DropdownItem from "react-bootstrap/DropdownItem";
import Translate from "react-translate-component";
import counterpart from "counterpart";
import en from "../lang/en";

counterpart.registerTranslations('en', en);

export default class ProjectList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            checkItems: new Map(),
            selectedRows: 0,
            filterBar: '',
            // filterStatus: <Translate content="projectList.status_new"/>,
            filterStatus: 'All',
            submit: false
        }
    }

    componentDidMount() {
        if (sessionStorage.getItem('filterBar') === null)
            sessionStorage.setItem('filterBar', '');

        if (sessionStorage.getItem('filterStatus') === null)
            // sessionStorage.setItem('filterStatus', 'New');
            sessionStorage.setItem('filterStatus', '');


        let url = 'http://localhost:8080/projects/load';
        axios.get(url)
            .then((response) => {
                this.setState({projects: response.data});
            })
            .catch(error => {
                console.log(error);
                window.location = '/error';
            });

        const prevFilter = sessionStorage.getItem('filterBar');
        const prevStatusTitle = sessionStorage.getItem('filterStatus');
        if (sessionStorage.getItem('filterStatus') !== '')
            this.setState({
                filterBar: prevFilter,
                filterStatus: prevStatusTitle
            })
        if (sessionStorage.getItem('filterBar') !== '' && sessionStorage.getItem('filterStatus') === '')
            this.setState({
                filterBar: prevFilter,
                filterStatus: <Translate content="projectList.status_new"/>
            })
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
    }

    deleteHandler = (event, projectNumber) => {
        console.log(projectNumber)

        let url = 'http://localhost:8080/projects/delete';
        axios.post(url, {
            project_num: projectNumber,
            project_name: '',
            customer: '',
            group: '',
            member: [],
            status: '',
            start_date: '',
            end_date: ''
        }, null)
            .then(
                response => {
                    console.log(response.data);
                    window.location = "/";
                })
            .catch(error => {
                console.log(Object.assign({}, error.response.data))
                window.location = "/error";
            });
    }

    deleteLayout(project) {
        let newProject = 'New';
        if (project.status === newProject) {
            return (
                <div>
                    <Button as="input" type="reset" value="Delete" className="footer-button-a"
                            onClick={(event) => {
                                this.deleteHandler(event, project.projectNumber)
                            }}/>
                </div>
            );
        }

    }

    selectHandler = event => {
        console.log('called')
        let isChecked = event.target.checked;
        let item = event.target.value;
        let operand; //+1 if checked and -1 if unchecked
        if (isChecked === true) {
            operand = 1;
        } else {
            operand = -1;
        }

        if (this.state.selectedRows === 0 && operand === -1) {
            operand = 0;
            isChecked = false;
        }

        this.setState({
            checkItems: this.state.checkItems.set(item, isChecked),
            selectedRows: this.state.selectedRows + operand
        });
    }

    disableControl(status) {
        return status !== 'New';
    }

    projectMapper(project) {
        return (
            <tr key={project.id}>
                <td>
                    <Form.Check onChange={this.selectHandler}
                                value={project.projectNumber}
                                disabled={this.disableControl(project.status)}/>
                </td>
                <td>
                    <Link to={{
                        pathname: '/edit',
                        state: {
                            proNum: project.projectNumber
                        }
                    }}>
                        {project.projectNumber}
                    </Link>
                </td>
                <td>{project.name}</td>
                <td>{project.status}</td>
                <td>{project.customer}</td>
                <td>{project.startDate}</td>
                <td>
                    {this.deleteLayout(project)}
                </td>
            </tr>
        )
    }

    handleChangeSearchBar = event => {
        this.setState({
            filterBar: event.target.value
        })
    }

    handleChangeStatusBar = event => {
        this.setState({
            filterStatus: event
        })
    }

    handleSubmit = event => {
        event.preventDefault();
        sessionStorage.setItem('filterBar', this.state.filterBar);
        sessionStorage.setItem('filterStatus', this.state.filterStatus);
        this.setState({
            filterBar: sessionStorage.getItem('filterBar'),
            filterStatus: sessionStorage.getItem('filterStatus')
        })
    }

    handleReset = event => {
        sessionStorage.setItem('filterBar', '');
        sessionStorage.setItem('filterStatus', '');
        this.setState({
            filterBar: '',
            filterStatus: 'All',
            checkItems: new Map(),
            selectedRows: 0
        })
    }

    render() {
        const filteredProjects = this.state.projects.filter(project => {
            let filterBarVar = sessionStorage.getItem('filterBar');
            let filterStatusVar = sessionStorage.getItem('filterStatus');
            if (filterStatusVar === 'All') {
                filterStatusVar = '';
            }

            return ((project.projectNumber.toString().toLowerCase().includes(filterBarVar.toLowerCase()) ||
                project.name.toString().toLowerCase().includes(filterBarVar.toLowerCase()) ||
                project.customer.toString().toLowerCase().includes(filterBarVar.toLowerCase())) &&
                project.status.toString().toLowerCase().includes(filterStatusVar.toLowerCase()));
        })


        return (
            <div className="main">
                <Container fluid>
                    <Form className="form" onSubmit={this.handleSubmit}>
                        <Row>
                            <Col xl={12}>
                                <Form.Group as={Row} controlId="formPlaintextEmail">
                                    <Col sm="5">
                                        <Form.Control
                                            name="project_num"
                                            type="text"
                                            value={this.state.filterBar}
                                            onChange={event => this.handleChangeSearchBar(event)}
                                        />
                                    </Col>
                                    <Col sm="2">
                                        <DropdownButton
                                            as={ButtonGroup}
                                            key="down"
                                            onSelect={event => this.handleChangeStatusBar(event)}
                                            id="dropdown-button-drop-down"
                                            drop="down"
                                            title={this.state.filterStatus}>
                                            <DropdownItem eventKey="All"><Translate
                                                content="projectList.status_all"/></DropdownItem>
                                            <DropdownItem eventKey="New"><Translate
                                                content="projectList.status_new"/></DropdownItem>
                                            <DropdownItem eventKey="Planned"><Translate
                                                content="projectList.status_planned"/></DropdownItem>
                                            <DropdownItem eventKey="In Progress"><Translate
                                                content="projectList.status_in_progress"/></DropdownItem>
                                            <DropdownItem eventKey="Finished"><Translate
                                                content="projectList.status_finish"/></DropdownItem>
                                        </DropdownButton>
                                    </Col>
                                    <Col sm="2">
                                        <Translate component="button" as="input" type="submit"
                                                   content={"projectList.search"}/>
                                    </Col>
                                    <Col sm="1">
                                        <Translate component="button" as="input" type="reset"
                                                   content={"projectList.reset"} onClick={this.handleReset}/>
                                    </Col>
                                </Form.Group>
                            </Col>
                        </Row>
                    </Form>
                    <Table bordered responsive>
                        <thead>
                        <tr>
                            <th></th>
                            <th><Translate content="projectList.number"/></th>
                            <th><Translate content="projectList.name"/></th>
                            <th><Translate content="projectList.status"/></th>
                            <th><Translate content="projectList.customer"/></th>
                            <th><Translate content="projectList.start_date"/></th>
                            <th><Translate content="projectList.delete"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        {/*{this.state.projects.map(project => this.projectMapper(project))}*/}

                        {/*{this.state.projects.filter(project => project.projectNumber*/}
                        {/*    .toString()*/}
                        {/*    .includes(this.state.filter))*/}
                        {/*    .map(project => this.projectMapper(project))}*/}

                        {filteredProjects
                            .sort((project_a, project_b) => (project_a.projectNumber > project_b.projectNumber) ? 1 : -1)
                            .map(project => this.projectMapper(project))}
                        </tbody>
                    </Table>
                    <TotalSelectedProject total={this.state.selectedRows} checkItems={this.state.checkItems}/>
                </Container>
            </div>
        );
    }
}