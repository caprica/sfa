import React from 'react'

import { Switch, Route, Redirect } from 'react-router-dom'

import SecureRoute from 'components/security/SecureRoute'

import ErrorPage from 'pages/error'
import HomePage from 'pages/home'
import LoginPage from 'pages/login/LoginPage'
import NotFoundPage from 'pages/not-found'

import EditProfilePage from 'pages/edit-profile'
import DashboardPage from 'pages/dashboard'
import Page from 'page'

import 'App.css'

// FIXME we may like to load the user record on each route change

const App = () => {

    return (
        <div>

            <Route exact path='/index.html'>
                <Redirect to="/home"/>
            </Route>
            <Route exact path='/'>
                <Redirect to="/home"/>
            </Route>

            <Page>
                <Switch>
                    <Route exact path="/home" component={HomePage}/>
                    <Route exact path="/login" component={LoginPage}/>

                    <SecureRoute exact path="/dashboard" component={DashboardPage}/>
                    <SecureRoute exact path="/my-profile/edit" component={EditProfilePage}/>

                    <Route exect path="/system-error/:statusCode" component={ErrorPage}/>
                    <Route component={NotFoundPage}/>
                </Switch>
            </Page>

        </div>
    )
}

export default App
