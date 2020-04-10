/*
 * Setup the Axios HTTP client.
 */

import axios from 'axios'

import { authHeader } from './auth-header'
import { authenticationService } from './authentication-service'

const AUTHORIZATION_HEADER = 'Authorization'

const initialiseAxios = (history) => {

    /**
     * Add an interceptor for every request that automatically adds an Authorization header containing the current user's
     * JSON Web Token if there is one available.
     *
     * If the request already contains an Authorization header, that header value will not be changed.
     */
    axios.interceptors.request.use(config => {
        const authorizationHeader = config.headers[AUTHORIZATION_HEADER]
        if (!authorizationHeader) {
            const authorization = authHeader()
            if (authorization) config.headers[AUTHORIZATION_HEADER] = authorization
        }
        return config
    }, error => {
        return Promise.reject(error)
    })

    /**
     * Add an interceptor for request response that checks for an authentication error and if possible automatically tries
     * to retrieve a new JSON Web Token for the current user before amd repeat the request.
     *
     * FIXME this is basically a skeleton/placeholder right now, maybe see https://github.com/Flyrell/axios-auth-refresh
     */
    axios.interceptors.response.use(response => {
        return response
    }, error => {
        const status = error.response.status

        /**
         * If a "Not Authorized" response is returned, this means that the current request did not contain a valid
         * Authorization header.
         *
         * This can happen if:
         *  - the user is not logged in
         *  - the JSON Web Token in the header has expired
         *
         * If this happens, we could attempt to automatically refresh the authentication token.
         */
        if (status === 401) {
            authenticationService.logout()
            // Instead of this, we may elect to set some state (e.g. in Redux) so that the user gets warned on the
            // page they're currently on rather than just redirecting (which may cause the user to lose work)
            //
            // With this approach, care must be taken in React components so as to not update any state (e.g. in
            // catch clause on a Promise because after this push that component will have been unomunted - a warning
            // would be displayed in the browser console)
            history.push(authenticationService.authenticationPath)
        }

        /**
         * If a "Forbidden" response is returned, this means that the current request did contain a valid Authorization
         * header, but the user does not have the required privileges to use the requested resource.
         *
         * In this case there is no point trying to log in again to get a refreshed authentication token.
         *
         * FIXME could we however automatically redirect to a standard error page?
         */
        if (status === 403) {
            // etc
        }

        if (status === 500) {
            // etc
        }

        // Reject with the response object so pages/components can act according to status codes if need be
        return Promise.reject(error.response)
    })

}

export { initialiseAxios }
