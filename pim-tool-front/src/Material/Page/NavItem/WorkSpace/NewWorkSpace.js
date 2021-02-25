import React from 'react';
import {ButtonGroup, Col, DropdownButton, Form, Row} from "react-bootstrap";
import '../../../Style/NavItem/WorkSpace/NewWorkSpace.css';
import Autocomplete from "@material-ui/lab/Autocomplete";
import Chip from "@material-ui/core/Chip";
import TextField from "@material-ui/core/TextField";
import axios from 'axios';
import DropdownItem from "react-bootstrap/DropdownItem";
import en from '../../../lang/en';
import Translate from 'react-translate-component';
import counterpart from "counterpart";
import BackEndFieldError from "./BackEndFieldError";
import FieldError from "./FieldError";

counterpart.registerTranslations('en', en);

class NewWorkSpace extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            employees: [],
            group: [],
            // project_num_title: '',
            // disable: false
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8080/employees/load')
            .then((response) => {
                this.setState({
                    employees: response.data
                })
            })
            .catch(error => {
                window.location = '/error';
            });

        axios.get('http://localhost:8080/groups/load')
            .then((response) => {
                this.setState({
                    group: response.data
                })
            })
            .catch(error => {
                window.location = '/error';
            });

        if (this.props.workTitle === 'editProject') {
            this.setState({
                project_num_title: this.props.proNum,
                disable: true
            })
        } else {
            this.setState({
                project_num_title: this.props.project_num,
                disable: false
            })
        }
    }

    render() {
        return (
            <React.Fragment>
                <Form.Group as={Row} controlId="formPlaintextEmail">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.projectNum"/>
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control
                            name="project_num"
                            className={this.props.proNumEmptyErr
                            || this.props.errorMessageKey === 'CreateProject.Error.Msg.projectNumberMustNotBeDuplicate' ? "error" : ""}
                            type="number"
                            maxLength="4"
                            value={this.state.project_num_title}
                            disabled={this.state.disable}
                            onChange={event => this.props.changeHandler(event)}/>
                        <FieldError error={this.props.proNumEmptyErr} field="Project number"/>
                        <BackEndFieldError errorMessageKey={this.props.errorMessageKey}
                                           preferedKey='CreateProject.Error.Msg.projectNumberMustNotBeDuplicate'/>
                        <BackEndFieldError errorMessageKey={this.props.errorMessageKey}
                                           preferedKey='CreateProject.Error.Msg.projectNumberMustBeExist'/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.projectName"/>
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control name="project_name"
                                      className={this.props.proNameEmptyErr ? "error" : ""}
                                      type="text"
                                      onChange={event => this.props.changeHandler(event)}/>
                        <FieldError error={this.props.proNameEmptyErr} field="Project name"/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.customer"/>
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control name="customer"
                                      className={this.props.customerEmptyErr ? "error" : ""}
                                      type="text"
                                      onChange={event => this.props.changeHandler(event)}/>
                        <FieldError error={this.props.customerEmptyErr} field="Customer"/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.group"/>
                    </Form.Label>
                    <Col sm="10">
                        <DropdownButton
                            as={ButtonGroup}
                            key="down"
                            id="dropdown-button-drop-down"
                            drop="down"
                            title={this.props.groupTitle}
                            name="group"
                            onSelect={this.props.selectGroupHandler}>
                            {this.state.group.map((group) => {
                                return (
                                    <DropdownItem
                                        eventKey={group.name}
                                        key={group.name}>
                                        {group.name}
                                    </DropdownItem>
                                );
                            })}
                        </DropdownButton>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.member"/>
                    </Form.Label>
                    <Col sm="10">
                        <Autocomplete
                            multiple
                            id="tags-filled"
                            onChange={this.props.autoHandler}
                            // options={this.state.employees.map((employee) => employee.visa + ': ' + employee.fullName)}
                            options={this.state.employees.map((employee) => employee.visa)}
                            className={this.props.memberEmptyErr ||
                            this.props.errorMessageKey === 'CreateProject.Error.Msg.memberMustBeExist' ? "error" : ""}
                            freeSolo
                            renderTags={(value, getTagProps) =>
                                value.map((option, index) => (
                                    <Chip
                                        variant="outlined"
                                        label={option}
                                        {...getTagProps({index})}
                                    />
                                ))
                            }
                            renderInput={(params) => (
                                <TextField {...params} variant="outlined" placeholder="Members"/>
                            )}
                        />
                        <FieldError error={this.props.memberEmptyErr} field="Member"/>
                        <BackEndFieldError errorMessageKey={this.props.errorMessageKey}
                                           preferedKey='CreateProject.Error.Msg.memberMustBeExist'/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.status"/>
                    </Form.Label>
                    <Col sm="10">
                        <DropdownButton
                            as={ButtonGroup}
                            key="down"
                            onSelect={this.props.selectStatusHandler}
                            id="dropdown-button-drop-down"
                            drop="down"
                            title={this.props.statusTitle}>
                            <DropdownItem eventKey="New"><Translate content="newWorkSpace.status_new"/></DropdownItem>
                            <DropdownItem eventKey="Planned"><Translate
                                content="newWorkSpace.status_planned"/></DropdownItem>
                            <DropdownItem eventKey="In Progress"><Translate content="newWorkSpace.status_in_progress"/></DropdownItem>
                            <DropdownItem eventKey="Finished"><Translate
                                content="newWorkSpace.status_finish"/></DropdownItem>
                        </DropdownButton>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formPlaintextPassword">
                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.startDate"/>
                    </Form.Label>
                    <Col sm="4">
                        <Form.Control name="start_date"
                                      className={this.props.startDateEmptyErr ||
                                      this.props.errorMessageKey === 'CreateProject.Error.Msg.deadlineMustNotBeGreaterThanProjectFinishingDate' ? "error" : ""}
                                      type="date"
                                      onChange={event => this.props.changeHandler(event)}/>
                        <FieldError error={this.props.startDateEmptyErr} field="Start date"/>
                    </Col>

                    <Form.Label column sm="2">
                        <Translate content="newWorkSpace.endDate"/>
                    </Form.Label>
                    <Col sm="4">
                        <Form.Control name="end_date"
                                      className={this.props.endDateEmptyErr ||
                                      this.props.errorMessageKey === 'CreateProject.Error.Msg.deadlineMustNotBeGreaterThanProjectFinishingDate' ? "error" : ""}
                                      type="date"
                                      onChange={event => this.props.changeHandler(event)}/>
                        <FieldError error={this.props.endDateEmptyErr} field="End date"/>
                    </Col>
                    <BackEndFieldError errorMessageKey={this.props.errorMessageKey}
                                       preferedKey='CreateProject.Error.Msg.deadlineMustNotBeGreaterThanProjectFinishingDate'/>
                </Form.Group>

            </React.Fragment>
        );
    }
}

export default NewWorkSpace;