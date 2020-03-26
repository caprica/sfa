import React from 'react'

import {
    makeStyles,
    Button,
    TextField
} from '@material-ui/core'

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(1),
        display: 'grid',
        gridGap: theme.spacing(4),
        gridTemplateColumns: 'auto',
        marginTop: theme.spacing(3)
    },
    button: {
        alignSelf: 'start'
    }
}))

const ProfileForm = ({ firstName, lastName, position, aboutMe, onChange, onSubmit }) => {

    const classes = useStyles()

    const handleChange = (field) => (evt) => onChange(field, evt.target.value)

    return (
        <div className={classes.root} >
            <TextField
                autoFocus
                variant="outlined"
                label="First name"
                value={firstName}
                onChange={handleChange('firstName')}
            />
            <TextField
                variant="outlined"
                label="Last name"
                value={lastName}
                onChange={handleChange('lastName')}
            />
            <TextField
                variant="outlined"
                label="Position"
                value={position}
                onChange={handleChange('position')}
            />
            <TextField
                variant="outlined"
                label="About me"
                value={aboutMe}
                multiline
                rows={5}
                onChange={handleChange('aboutMe')}
            />
            <Button className={classes.button} variant="contained" color="primary" onClick={onSubmit}>Update</Button>
        </div>
    )
}

export default ProfileForm
