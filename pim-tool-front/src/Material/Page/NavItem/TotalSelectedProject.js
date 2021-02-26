import React from 'react';
import Translate from "react-translate-component";
import counterpart from "counterpart";
import en from "../../lang/en";
import {Form} from "react-bootstrap";
import axios from "axios";
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import {classes} from "istanbul-lib-coverage";
import "../../Style/NavItem/TotalSelectedProject.css"

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
        if (this.props.total > 0) {
            return (
                <div className="flex-box">
                    <Form className="form" onSubmit={this.handleSubmit}>
                        <div className="selected-announce">
                            {this.props.total} project(s) selected!
                        </div>
                        <div className="delete-group">
                            <IconButton aria-label="delete" type="submit" className={classes.margin}
                                        className="delete-group-smaller">
                                <Translate component="button" as="input" className="delete-line"
                                           content={"totalSelectedProject.deleteLine"}/>
                                <DeleteIcon fontSize="large" className="delete-button"/>
                            </IconButton>
                        </div>

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