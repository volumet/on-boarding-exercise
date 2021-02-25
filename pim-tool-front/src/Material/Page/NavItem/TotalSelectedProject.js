import React from 'react';
import Translate from "react-translate-component";
import counterpart from "counterpart";
import en from "../../lang/en";
import {Form} from "react-bootstrap";
import axios from "axios";

counterpart.registerTranslations('en', en);
export default class TotalSelectedProject extends React.Component {
    handleSubmit = event => {
        event.preventDefault();
        let url = 'http://localhost:8080/projects/multipleDelete';
        axios.post(url, {
            project_num: Object.fromEntries(this.props.checkItems),
        }, null)
            .then(
                response => {
                    window.location = "/";
                })
            .catch(error => {
                window.location = "/error";
            });
    }

    counting() {
        console.log(this.props.total)
        console.log(this.props.checkItems)
        if (this.props.total > 0) {
            return (
                <div>
                    <Form className="form" onSubmit={this.handleSubmit}>
                        <p>
                            {this.props.total} project(s) selected!

                            <Translate component="button" as="input" type="submit" className="footer-button-a"
                                       content={"totalSelectedProject.deleteLine"}/>

                        </p>
                    </Form>
                </div>
            );
        }
        return null;
    }

    render() {
        return (
            this.counting()
        );
    }
}