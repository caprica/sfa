// import { handleResponse } from './response-handler'
import { BehaviorSubject } from 'rxjs'
import axios from 'axios'

const LOCAL_STORAGE_KEY = 'currentUser'
const AUTHENTICATION_PATH = '/login'

// Could be replaced by Redux etc
const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY)))

const login = (username, password) => {
    return axios.post(
        AUTHENTICATION_PATH,
        JSON.stringify({ username, password })
    ).then(response => {
        return { username, token: response.headers.authorization }
    }).then(user => {
        localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(user))
        currentUserSubject.next(user)
        return user
    })
}

const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY)
    currentUserSubject.next(null)
}

export const authenticationService = {
    login,
    logout,
    authenticationPath : AUTHENTICATION_PATH,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue() { return currentUserSubject.value }
}
