import React from 'react'
import { Route, Redirect, useLocation } from 'react-router-dom'

import { authenticationService } from './authentication-service'

const LOGIN_ROUTE = '/login'

const SecureRoute = ({ component: Component, ...rest }) => {

    const location = useLocation()

    return (
        <Route {...rest} render={props => {
            if (authenticationService.currentUserValue) {
                return <Component {...props}/>
            } else {
                return <Redirect to={{ pathname: LOGIN_ROUTE, state: { from: location } }}/>
            }
        }}/>
    )
}

export default SecureRoute
