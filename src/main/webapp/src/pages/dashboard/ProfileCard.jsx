import React from 'react'

import { Link as RouterLink } from 'react-router-dom'

import {
    makeStyles,
    Avatar,
    Button,
    Typography,
    Card,
    CardActions,
    CardContent,
    CardHeader
} from '@material-ui/core'

const useStyles = makeStyles(theme => ({
    root: {
    },
    content: {
        padding: theme.spacing(2)
    }
}))

const ProfileCard = ({ profile }) => {

    const classes = useStyles()

    if (!profile) return null

    return (
        <Card className={classes.root} elevation={2} square>
            <CardHeader
                avatar={
                    <Avatar aria-label="recipe" className={classes.avatar}>
                        {profile.username.substring(0,1).toUpperCase()}
                    </Avatar>
                }
                title={profile.username}
                subheader={`${profile.firstName} ${profile.lastName}, ${profile.position}`}
            />
            <CardContent>
                <Typography variant="body2" color="textSecondary" component="p">
                    { profile.aboutMe }
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <Button size="small" color="primary" component={RouterLink} to="/my-profile/edit">Edit My Profile</Button>
            </CardActions>
        </Card>
    )
}

export default ProfileCard
