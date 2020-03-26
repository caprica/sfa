import React from 'react'
import { Redirect, useHistory, useLocation } from 'react-router-dom'
import { authenticationService } from '../../components/security/authentication-service';
import { makeStyles, Button, Container, Divider, Paper, TextField, Typography } from '@material-ui/core'
import { Alert, AlertTitle } from '@material-ui/lab'

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(20, 0, 16),
    },
    loginPane: {
        padding: theme.spacing(3),
    },
    inputPane: {
        display: 'grid',
        gridGap: theme.spacing(2),
        margin: theme.spacing(3 ,0)
    },
    submit: {
        display: 'grid',
        gridTemplateColumns: 'auto auto'
    },
    clearButton: {
        gridColumn: '1 / 1',
        gridRow: '1 / 1',
        justifySelf: 'start'
    },
    submitButton: {
        gridColumn: '2 / 2',
        gridRow: '1 / 1',
        justifySelf: 'end'
    },
    alert: {
        margin: theme.spacing(2, 0)
    }
}))

const LoginPage = () => {

    const classes = useStyles()

    const usernameRef = React.useRef()
    const passwordRef = React.useRef()

    const [username, setUsername] = React.useState('')
    const [password, setPassword] = React.useState('')
    const [working, setWorking] = React.useState(false)
    const [error, setError] = React.useState(null)

    const location = useLocation()

    const history = useHistory()

    if (authenticationService.currentUserValue) return <Redirect to="/"/>

    const loginEnabled = !working && username && password && username.trim().length > 0 && password.trim().length > 0

    const handleChangeUsername = (evt) => setUsername(evt.target.value)
    const handleChangePassword = (evt) => setPassword(evt.target.value)

    const handleLogin = () => {
        if (!loginEnabled) return

        setError(null)
        setWorking(true)
        authenticationService.login(username, password)
            .then(
                user => {
                    const { from } = location.state || { from: { pathname: "/" } }
                    history.push(from)
                },
                error => {
                    setError(error)
                    setWorking(false)
                    setUsername('')
                    setPassword('')
                    usernameRef.current.focus()
                }
            )
    }

    const handleClear = () => {
        setError(null)
        setUsername('')
        setPassword('')
        usernameRef.current.focus()
    }

    const handleUsernameKeyPress = (evt) => {
        if (evt.key === 'Enter') {
            passwordRef.current.focus()
            evt.preventDefault()
        }
    }

    const handlePasswordKeyPress = (evt) => {
        if (evt.key === 'Enter') {
            handleLogin()
            evt.preventDefault()
        }
    }

    return (
        <Container className={classes.root} maxWidth="xs">
            <Paper className={classes.loginPane} elevation={4} square>
                <Typography gutterBottom>Login</Typography>
                <Typography variant="caption" gutterBottom>Enter your user credentials to login.</Typography>
                <Divider light/>
                <div className={classes.inputPane}>
                    <TextField
                        autoFocus
                        label="Username"
                        value={username}
                        onChange={handleChangeUsername}
                        onKeyPress={handleUsernameKeyPress}
                        inputRef={usernameRef}
                    />
                    <TextField
                        type="password"
                        label="Password"
                        value={password}
                        onChange={handleChangePassword}
                        onKeyPress={handlePasswordKeyPress}
                        inputRef={passwordRef}
                    />
                </div>
                {error &&
                    <Alert className={classes.alert} severity="error">
                        <AlertTitle>Login Failure</AlertTitle>
                        Please check your credentials and try again.
                    </Alert>
                }
                <div className={classes.submit}>
                    <Button className={classes.submitButton} variant="contained" size="small" color="primary" onClick={handleLogin} disabled={!loginEnabled}>Login</Button>
                    <Button className={classes.clearButton} variant="contained" size="small" onClick={handleClear}>Clear</Button>
                </div>
            </Paper>
        </Container>
    )
}

export default LoginPage
