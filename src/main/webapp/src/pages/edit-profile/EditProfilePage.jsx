import React from 'react'

import { useHistory } from 'react-router-dom'

import ProfileForm from './ProfileForm'

import {
    makeStyles,
    Container,
    Divider,
    Paper,
    Slide,
    Snackbar,
    Typography
} from '@material-ui/core'

import {
    Alert
} from '@material-ui/lab'

import { myProfileService } from '../../services/profile/my-profile-service'

const SNACKBAR_ORIGIN = { vertical: 'top', horizontal: 'center'}

const SnackbarTransition = (props) => <Slide { ...props } direction="down"/>

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(12, 0)
    },
    content: {
        marginTop: theme.spacing(4),
        padding: theme.spacing(2)
    },
}))

const EMPTY_PROFILE = {
    firstName: '',
    lastName: '',
    position: '',
    aboutMe: ''
}

const EditProfilePage = ({ className }) => {

    const classes = useStyles()

    const [profile, setProfile] = React.useState(EMPTY_PROFILE)
    const [error, setError] = React.useState(null)
    const [working, setWorking] = React.useState(false)

    const history = useHistory()

    React.useEffect(() => {
        setWorking(true)
        myProfileService.getMyProfile()
            .then(profile => setProfile(profile))
            .catch(error => setError(`Failed to get profile: ${error}`))
            .finally(() => setWorking(false))
    }, [])

    const handleProfileChange = (field, value) => setProfile({ ...profile, [field]: value })

    const handleSubmit = () => {
        myProfileService.updateMyProfile(profile)
            .then(profile => {
                setWorking(true)
                setProfile(profile)
                history.goBack() // FIXME or should it redirect/push a new route?
            })
            .catch(error => setError(`Failed to update profile: ${error}`))
            .finally(() => setWorking(false))
    }

    const handleCloseError = () => setError(null)

    // FIXME use the "working" state to show a spinner or whatever
    console.log(`working: ${working}`)

    return (
        <section className={classes.root}>
            <Container maxWidth="sm">
                <Typography variant="h4">Edit My Profile</Typography>
                <Divider/>
                <Paper className={classes.content} elevation={2} square>
                    <Typography variant="subtitle1">Profile</Typography>
                    <Typography variant="caption" gutterBottom>Tell us some very fine things about your very fine self.</Typography>
                    <ProfileForm
                        firstName={profile.firstName}
                        lastName={profile.lastName}
                        position={profile.position}
                        aboutMe={profile.aboutMe}
                        onChange={handleProfileChange}
                        onSubmit={handleSubmit}
                    />
                </Paper>
            </Container>
            <Snackbar
                open={error !== null}
                TransitionComponent={SnackbarTransition}
                anchorOrigin={SNACKBAR_ORIGIN}
                autoHideDuration={6000}
                onClose={handleCloseError}
            >
                <Alert onClose={handleCloseError} severity="error">
                    {error}
                </Alert>
            </Snackbar>
        </section>
    )
}

export default EditProfilePage
