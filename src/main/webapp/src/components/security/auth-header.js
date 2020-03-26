import { authenticationService } from './authentication-service'

const authHeader = () => {
    const currentUser = authenticationService.currentUserValue
    if (currentUser && currentUser.token) {
        return currentUser.token
    } else {
        return null
    }
}

export { authHeader }
