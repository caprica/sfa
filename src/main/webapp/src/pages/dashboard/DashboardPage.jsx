import React from 'react'

import {
    makeStyles,
    Container,
    Typography,
    Divider
} from '@material-ui/core'

import ProfileCard from './ProfileCard'

import Gibberish from './Gibberish'

import { myProfileService } from 'services/profile/my-profile-service'

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(12, 0)
    },
    content: {
        display: 'grid',
        gridGap: theme.spacing(4),
        gridTemplateColumns: '1fr 2fr',
        marginTop: theme.spacing(4)
    },
    profile: {
        paddingTop: theme.spacing(1)
    },
}))

const DashboardPage = () => {

    const classes = useStyles()

    const [profile, setProfile] = React.useState()
    const [error, setError] = React.useState()

    React.useEffect(() => {
        myProfileService.getMyProfile()
            .then(profile => setProfile(profile))
            .catch(error => error.status !== 401 && setError(`Failed to get profile: ${error.statusText}`))
    }, [])

    return (
        <section className={classes.root}>
            <Container>
                <Typography variant="h4">My Dashboard</Typography>
                <Divider/>
                <div className={classes.content}>
                    <div>
                        <Typography as="h5">My Profile</Typography>
                        <Divider/>
                        <div className={classes.profile}>
                            { profile?
                                <ProfileCard profile={profile}/>:
                                error?
                                    <>
                                        <Typography variant="body1">Failed to load profile.</Typography>
                                        <Typography variant="body1">{ error }</Typography>
                                    </>:
                                    <Typography variant="body1">Please wait, loading profile...</Typography>
                            }
                        </div>
                    </div>
                    <div>
                        <Typography as="h5">My Very Fine Things</Typography>
                        <Divider/>
                        <Gibberish/>
                    </div>
                </div>
            </Container>
        </section>
    )
}

export default DashboardPage
