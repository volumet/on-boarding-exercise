import React from 'react';

class FieldError extends React.Component {

    render() {
        if (this.props.error) {
            return (
                <p>{this.props.field} must be full filled!</p>
            );
        }
        else
            return null;
    }
}

export default FieldError;