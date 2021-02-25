import React from 'react';
import {Col, Container, Form, Row} from 'react-bootstrap';
import Title from "./Title";
import NewWorkSpace from "./WorkSpace/NewWorkSpace";
import Footer from "./Footer";
import axios from 'axios';
import Translate from "react-translate-component";
import FormError from "./FormError";

class NewProject extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            project_num: '',
            project_name: '',
            customer: '',
            group: '',
            group_title: <Translate content="newWorkSpace.default_group_title" /> ,
            member: [],
            status: 'New',
            status_title: <Translate content="newWorkSpace.status_new" /> ,
            start_date: '',
            end_date: '',
            form_valid: true,
            proNumEmptyErr: false,
            proNameEmptyErr: false,
            customerEmptyErr: false,
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
            this.setState({
                project_num: this.props.location.state.proNum
            })
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        if (this.state.project_num === '' ||
            this.state.project_name === '' ||
            this.state.customer === '' ||
            this.state.group === '' ||
            this.state.member.length === 0 ||
            this.state.group === '' ||
            this.state.status === '' ||
            this.state.start_date === '' ||
            this.state.end_date === '') {

            let proNumEmpErr, proNameEmpErr, cusEmpErr, memberEmpErr, startDateEmpErr, endDateEmpErr = false;


            proNumEmpErr = this.state.project_num === '';
            proNameEmpErr = this.state.project_name === '';
            cusEmpErr = this.state.customer === '';
            memberEmpErr = this.state.member.length === 0;
            startDateEmpErr = this.state.start_date === '';
            endDateEmpErr = this.state.end_date === '';

            this.setState({
                form_valid: false,
                proNumEmptyErr: proNumEmpErr,
                proNameEmptyErr: proNameEmpErr,
                customerEmptyErr: cusEmpErr,
                memberEmptyErr: memberEmpErr,
                startDateEmptyErr: startDateEmpErr,
                endDateEmptyErr: endDateEmpErr
            })
        } else {
        let postUrl = 'new'
        if (this.props.title === "editProject") {
            postUrl = 'edit';
        }
        let url = 'http://localhost:8080/projects/' + postUrl;
        axios.post(url, {
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
            group: event,
            group_title: event
        });
    }

    handleStatusSelect = (event) => {
        this.setState({
            status: event,
            status_title: event
        });
    }


    handleAutoChange = (event, value) => {
        this.setState({member: value});
    }

    render() {
        let proNum;

        // if (this.state.errorCode !== 200) {
        //
        // }

        if (this.props.location) {
            proNum = this.props.location.state.proNum;
        }
        return (
            <div className="new-project">
                <Container fluid>
                    <FormError valid={this.state.form_valid}/>
                    <Row>
                        <Col>
                            <Title name=<Translate content={"newProject." + this.props.title}/>/>
                        </Col>
                    </Row>

                    <Form className="form" onSubmit={this.handleSubmit}>
                        <Row>
                            <Col xl={12}>
                                <NewWorkSpace
                                    projectNum={this.state.project_num}
                                    changeHandler={this.handleChange}
                                    selectGroupHandler={this.handleGroupSelect}
                                    autoHandler={this.handleAutoChange}
                                    groupTitle={this.state.group_title}
                                    selectStatusHandler={this.handleStatusSelect}
                                    statusTitle={this.state.status_title}
                                    workTitle={this.props.title}
                                    proNum={proNum}
                                    proNumEmptyErr={this.state.proNumEmptyErr}
                                    proNameEmptyErr={this.state.proNameEmptyErr}
                                    customerEmptyErr={this.state.customerEmptyErr}
                                    memberEmptyErr={this.state.memberEmptyErr}
                                    startDateEmptyErr={this.state.startDateEmptyErr}
                                    endDateEmptyErr={this.state.endDateEmptyErr}
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