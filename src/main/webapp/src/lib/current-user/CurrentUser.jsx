import React from 'react'

import {
    useHistory,
    Link as RouterLink
} from 'react-router-dom'

import {
    makeStyles,
    Link,
    Typography
} from '@material-ui/core'

import { authenticationService } from '../../components/security/authentication-service'

const useStyles = makeStyles(theme => ({
    root: {
        paddingTop: 3,
        textAlign: 'end',
        color: 'white'
    },
    link: {
        color: 'white'
    }
}))

const CurrentUser = () => {

    const classes = useStyles()

    const [currentUser, setCurrentUser] = React.useState(null)

    const history = useHistory()

    React.useEffect(() => {
        const currentUserSub = authenticationService.currentUser.subscribe(value => setCurrentUser(value))
        return () => currentUserSub.unsubscribe()
    }, [])

    if (!currentUser) return null

    const handleLogout = () => {
        authenticationService.logout()
        history.push('/home')
    }

    return (
        <section className={classes.root}>
            <Typography variant="body2">Logged in as <b><Link className={classes.link} component={RouterLink} to="/dashboard">{currentUser.username}</Link></b></Typography>
            <Typography variant="body2"><Link className={classes.link} onClick={handleLogout} component="button" variant="caption">Logout</Link></Typography>
        </section>
    )
}

export default CurrentUser
