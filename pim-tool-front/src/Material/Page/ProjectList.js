import React from 'react';
import {ButtonGroup, Col, Container, DropdownButton, Form, Row, Table} from "react-bootstrap";
import axios from 'axios';
import TotalSelectedProject from "./NavItem/TotalSelectedProject";
import {Link} from "react-router-dom";
import DropdownItem from "react-bootstrap/DropdownItem";
import Translate from "react-translate-component";
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import "../Style/ProjectList.css"
import {classes} from "istanbul-lib-coverage";

export default class ProjectList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            checkItems: new Map(),
            selectedRows: 0,
            filterBar: '',
            filterStatus: 'ALL',
            submit: false
        }
    }

    componentDidMount() {
        if (sessionStorage.getItem('filterBar') === null)
            sessionStorage.setItem('filterBar', '');

        if (sessionStorage.getItem('filterStatus') === null)
            sessionStorage.setItem('filterStatus', 'ALL');


        let url = 'http://localhost:8080/projects/search';
        axios.post(url, {
            searchValue: sessionStorage.getItem('filterBar'),
            statusValue: sessionStorage.getItem('filterStatus')
        }, null)
            .then((response) => {
                this.setState({projects: response.data});
            })
            .catch(error => {
                window.location = '/error';
            });

        const prevFilter = sessionStorage.getItem('filterBar');
        const prevStatusTitle = sessionStorage.getItem('filterStatus');
        if (sessionStorage.getItem('filterStatus') !== 'ALL') {
            this.setState({
                filterBar: prevFilter,
                filterStatus: prevStatusTitle
            })
        }
        if (sessionStorage.getItem('filterBar') !== '' && sessionStorage.getItem('filterStatus') === 'ALL') {
            this.setState({
                filterBar: prevFilter,
                filterStatus: <Translate content="projectList.status_new"/>
            })
        }
    }


    deleteHandler = (event, projectNumber) => {
        let url = `http://localhost:8080/projects/delete/${projectNumber}`;
        axios.delete(url)
            .then(
                response => {
                    window.location = "/";
                })
            .catch(error => {
                window.location = "/error";
            });
    }

    deleteLayout(project) {
        let newProject = 'NEW';
        if (project.status === newProject) {
            return (
                    <IconButton aria-label="delete" className={classes.margin}
                                onClick={(event) => {
                                    this.deleteHandler(event, project.projectNumber)
                                }}>
                        <DeleteIcon fontSize="inherit" className="delete-button"/>
                    </IconButton>
            );
        }

    }

    selectHandler = event => {
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
        return status !== 'NEW';
    }

    checkControl(projectNumber) {
        return this.state.checkItems.get(projectNumber.toString()) === true;
    }

    projectMapper(project) {
        return (
            <tr key={project.id} className="table-row">
                <td className="table-button-right check-col">
                    <Form.Check onChange={this.selectHandler}
                                value={project.projectNumber}
                                checked={this.checkControl(project.projectNumber)}
                                disabled={this.disableControl(project.status)}
                                className="check-box"/>
                </td>
                <td className="table-text-right">
                    <Link to={{
                        pathname: '/edit',
                        state: {
                            proNum: project.projectNumber
                        }
                    }}>
                        {project.projectNumber}
                    </Link>
                </td>
                <td className="table-text-left text-font">{project.name}</td>
                <td className="table-text-left text-font"><Translate content={`projectList.projectStatus.${project.status}`} /></td>
                <td className="table-text-left text-font">{project.customer}</td>
                <td className="table-text-center">{project.startDate}</td>
                <td className="table-button-right">
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

        let url = 'http://localhost:8080/projects/search';
        axios.post(url, {
            search_value : sessionStorage.getItem('filterBar'),
            status_value : sessionStorage.getItem('filterStatus')
        }, null)
            .then((response) => {
                this.setState({
                    filterBar: sessionStorage.getItem('filterBar'),
                    filterStatus: sessionStorage.getItem('filterStatus'),
                    checkItems: new Map(),
                    selectedRows: 0,
                    projects: response.data
                })
            })
            .catch(error => {
                window.location = "/error";
            });
    }

    handleReset = event => {
        sessionStorage.setItem('filterBar', '');
        sessionStorage.setItem('filterStatus', 'ALL');

        let url = 'http://localhost:8080/projects/search';
        axios.post(url, {
            search_value : sessionStorage.getItem('filterBar'),
            status_value : sessionStorage.getItem('filterStatus')
        }, null)
            .then((response) => {
                this.setState({
                    filterBar: '',
                    filterStatus: 'ALL',
                    checkItems: new Map(),
                    selectedRows: 0,
                    projects: response.data
                })
            })
            .catch(error => {
                window.location = "/error";
            });
    }

    render() {


        return (
            <div className="main">
                <Container fluid>

                    <Form className="form form-me" onSubmit={this.handleSubmit}>
                        <Row>
                            <Col xl={12}>
                                <Form.Group as={Row} controlId="formPlaintextEmail">
                                    <Col sm="5">
                                        <Form.Control
                                            className="search-bar"
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
                                            className="dropdown-button"
                                            title={<Translate content={`projectList.projectStatus.${this.state.filterStatus}`}/>}>
                                            <DropdownItem eventKey="ALL"><Translate
                                                content="projectList.projectStatus.ALL"/></DropdownItem>
                                            <DropdownItem eventKey="NEW"><Translate
                                                content="projectList.projectStatus.NEW"/></DropdownItem>
                                            <DropdownItem eventKey="PLA"><Translate
                                                content="projectList.projectStatus.PLA"/></DropdownItem>
                                            <DropdownItem eventKey="INP"><Translate
                                                content="projectList.projectStatus.INP"/></DropdownItem>
                                            <DropdownItem eventKey="FIN"><Translate
                                                content="projectList.projectStatus.FIN"/></DropdownItem>
                                        </DropdownButton>
                                    </Col>
                                    <Col sm="2">
                                        <Translate component="button" as="input" type="submit"
                                                   content={"projectList.search"}
                                                   className="search"/>
                                    </Col>
                                    <Col sm="1">
                                        <Translate component="button" as="input" type="reset"
                                                   content={"projectList.reset"} onClick={this.handleReset}
                                                   className="reset"/>
                                    </Col>
                                </Form.Group>
                            </Col>
                        </Row>
                    </Form>
                    <Table responsive bordered>
                        <thead>
                        <tr>
                            <th className="table-check"></th>
                            <th className="table-text-right table-number"><Translate content="projectList.number"/></th>
                            <th className="table-text-left table-name"><Translate content="projectList.name"/></th>
                            <th className="table-text-left table-status"><Translate content="projectList.status"/></th>
                            <th className="table-text-left table-customer"><Translate content="projectList.customer"/></th>
                            <th className="table-text-center table-date"><Translate content="projectList.start_date"/></th>
                            <th className="table-text-center table-delete"><Translate content="projectList.delete"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        {/*{filteredProjects*/}
                        {/*    .sort((project_a, project_b) => (project_a.projectNumber > project_b.projectNumber) ? 1 : -1)*/}
                        {/*    .slice(0, 300)*/}
                        {/*    .map(project => this.projectMapper(project))}*/}
                        {this.state.projects.map(project => this.projectMapper(project))}
                        </tbody>
                    </Table>
                    <TotalSelectedProject total={this.state.selectedRows} checkItems={this.state.checkItems}/>
                </Container>
            </div>
        );
    }
}