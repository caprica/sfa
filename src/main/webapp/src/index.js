import React                    from 'react'
import ReactDOM                 from 'react-dom'

import { Route, Router }        from 'react-router-dom'

import App                      from 'App'

import * as serviceWorker       from 'serviceWorker'

import { createBrowserHistory } from "history"
import { initialiseAxios }      from 'components/security/axios-setup'

import 'index.css'

// We use a custom history so we can share it between the React router and other non-React components
const history = createBrowserHistory()

initialiseAxios(history)

ReactDOM.render(
    <Router history={history}>
        <Route component={App}/>
    </Router>,
    document.getElementById('root')
)

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister()
