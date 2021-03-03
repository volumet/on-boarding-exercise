import React from 'react';
import {Col, Container, Form, Row} from 'react-bootstrap';
import Title from "./Title";
import NewWorkSpace from "./WorkSpace/NewWorkSpace";
import Footer from "./Footer";
import axios from 'axios';
import Translate from "react-translate-component";
import FormError from "./FormError";
import "../../Style/NavItem/NewProject.css"

class NewProject extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            project_num: '',
            project_name: '',
            customer: '',
            group: <Translate content="newWorkSpace.default_group_title"/>,
            member: [], //This will contain only visa in order to send to backend
            employee: [], //This will contain full information of an employee to send to NewWorkSpace
            status: 'NEW',
            start_date: '',
            end_date: '',
            form_valid: true,
            proNumEmptyErr: false,
            proNameEmptyErr: false,
            customerEmptyErr: false,
            groupEmptyErr: false,
            memberEmptyErr: false,
            startDateEmptyErr: false,
            endDateEmptyErr: false,
            errorCode: 200,
            errorMessageKey: ''
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        if (this.props.location) {
            axios.post('http://localhost:8080/projects/singleLoad', {
                project_num: this.props.location.state.proNum
            }, null)
                .then((response) => {
                    this.setState({
                        project_num: this.props.location.state.proNum,
                        project_name: response.data.name,
                        customer: response.data.customer,
                        group: response.data.group.id,
                        member: response.data.employees.map(person => person.visa),
                        employee: response.data.employees.map(person => person.visa + ": " + person.firstName + " " + person.lastName),
                        status: response.data.status,
                        start_date: response.data.startDate,
                        end_date: response.data.endDate
                    })
                })
                .catch(error => {
                    window.location = '/error';
                });
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        if (this.state.project_num === '' ||
            this.state.project_name === '' ||
            this.state.customer === '' ||
            this.state.group === '' ||
            this.state.group === '' ||
            this.state.status === '' ||
            this.state.start_date === '') {

            let proNumEmpErr, proNameEmpErr, cusEmpErr, groupEmpErr, startDateEmpErr = false;


            proNumEmpErr = this.state.project_num === '';
            proNameEmpErr = this.state.project_name === '';
            cusEmpErr = this.state.customer === '';
            groupEmpErr = this.state.group === '';
            startDateEmpErr = this.state.start_date === '';

            this.setState({
                form_valid: false,
                proNumEmptyErr: proNumEmpErr,
                proNameEmptyErr: proNameEmpErr,
                customerEmptyErr: cusEmpErr,
                groupEmptyErr: groupEmpErr,
                startDateEmptyErr: startDateEmpErr,
            })
        } else {
            let postUrl = 'new'
            if (this.props.title === "editProject") {
                postUrl = 'edit';
            }
            this.setState({
                form_valid: true,
                proNumEmptyErr: '',
                proNameEmptyErr: '',
                customerEmptyErr: '',
                groupEmptyErr: '',
                startDateEmptyErr: '',
            })
            let url = 'http://localhost:8080/projects/' + postUrl;
            axios.put(url, {
                project_num: this.state.project_num,
                project_name: this.state.project_name,
                customer: this.state.customer,
                group: this.state.group,
                member: this.state.member,
                status: this.state.status,
                start_date: this.state.start_date,
                end_date: this.state.end_date
            }, null)
                .then(
                    response => {
                        window.location = "/";
                    })
                .catch(error => {
                    let result = Object.assign({}, error.response.data);
                    this.setState({
                        errorCode: result.errorCode,
                        errorMessageKey: result.errorMessageKey
                    })
                });

            this.setState({
                form_valid: true
            })
        }
    }

    handleChange = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
    }

    handleGroupSelect = (event) => {
        this.setState({
            group: event
        });
    }

    handleStatusSelect = (event) => {
        this.setState({
            status: event
        });
    }


    handleAutoChange = (event, value) => {
        let memList = value.map(person => person.substring(0, person.indexOf(":", 1)))
        this.setState({member: memList, employee: value});
    }

    render() {
        let proNum;

        if (this.props.location) {
            proNum = this.props.location.state.proNum;
        }
        return (
            <div className="new-project">
                <Container fluid>
                    <FormError valid={this.state.form_valid}/>
                    <Row>
                        <Col>
                            <Title name=<Translate content={"newProject." + this.props.title} className="label-large"/>/>
                        </Col>
                    </Row>

                    <Form className="form" onSubmit={this.handleSubmit}>
                        <Row>
                            <Col xl={12}>
                                <NewWorkSpace
                                    projectNum={this.state.project_num}
                                    proName={this.state.project_name}
                                    customer={this.state.customer}
                                    member={this.state.member}
                                    employee={this.state.employee}
                                    startDate={this.state.start_date}
                                    endDate={this.state.end_date}
                                    changeHandler={this.handleChange}
                                    selectGroupHandler={this.handleGroupSelect}
                                    autoHandler={this.handleAutoChange}
                                    groupTitle={this.state.group}
                                    selectStatusHandler={this.handleStatusSelect}
                                    workTitle={this.props.title}
                                    status={this.state.status}
                                    proNum={proNum}
                                    proNumEmptyErr={this.state.proNumEmptyErr}
                                    proNameEmptyErr={this.state.proNameEmptyErr}
                                    customerEmptyErr={this.state.customerEmptyErr}
                                    groupEmptyErr={this.state.groupEmptyErr}
                                    memberEmptyErr={this.state.memberEmptyErr}
                                    startDateEmptyErr={this.state.startDateEmptyErr}
                                    errorMessageKey={this.state.errorMessageKey}
                                />
                            </Col>
                        </Row>
                        <Row>
                            <Col xl={12}>
                                <Footer/>
                            </Col>
                        </Row>
                    </Form>

                </Container>
            </div>
        );
    }
}

export default NewProject;