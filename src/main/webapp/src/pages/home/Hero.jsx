import React from 'react'

import { Link as RouterLink } from 'react-router-dom'

import { makeStyles, Button, Container, Typography } from '@material-ui/core'

import { grey } from '@material-ui/core/colors'

const useStyles = makeStyles(theme => ({
    root: {
        backgroundColor: grey[900]
    },
    content: {
        padding: theme.spacing(10, 0),
        display: 'grid',
        justifyItems: 'center',
        gridGap: theme.spacing(2),
        textAlign: 'center',
        color: 'white',
        fontWeight: 'bold'
    },
    button: {
        marginTop: theme.spacing(4),
    }
}))

const Hero = () => {

    const classes = useStyles()

    return (
        <section className={classes.root}>
            <Container className={classes.content}>
                <Typography variant="h3">Some Fine Application</Typography>
                <Typography variant="h5">Doing Fine Things in a Very Fine Way</Typography>
                <Button className={classes.button} component={RouterLink} to="/dashboard" variant="contained" size="large" color="primary">Get Started</Button>
            </Container>
        </section>
    )
}

export default Hero
