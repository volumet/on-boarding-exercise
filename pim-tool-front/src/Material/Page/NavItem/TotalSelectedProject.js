import React from 'react';
import Translate from "react-translate-component";
import counterpart from "counterpart";
import vi from "../../lang/vi";
import {Form} from "react-bootstrap";
import axios from "axios";
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import {classes} from "istanbul-lib-coverage";
import "../../Style/NavItem/TotalSelectedProject.css"

counterpart.registerTranslations('vi', vi);
export default class TotalSelectedProject extends React.Component {
    handleSubmit = event => {
        event.preventDefault();
        let url = 'http://localhost:8080/projects/multipleDelete';
        let object = Object.fromEntries(this.props.checkItems);
        axios.delete(url, {
            data: {
                project_num: Object.keys(object).filter(key => object[key] === true)
            }
        })
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
                            {this.props.total} <Translate content={"totalSelectedProject.selected"} />
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