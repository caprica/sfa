import React from 'react'

import { Link as RouterLink } from 'react-router-dom'

import clsx from 'clsx'

import { makeStyles, Container, Typography } from '@material-ui/core'

import { grey } from '@material-ui/core/colors'

import CurrentUser from '../lib/current-user'

const useStyles = makeStyles(theme => ({
    root: {
        backgroundColor: grey[800],
    },
    container: {
        padding: theme.spacing(1, 4),
        display: 'grid',
        gridTemplateColumns: '1fr auto',
        minHeight: 60
    },
    title: {
        fontSize: 20,
        color: 'white',
        alignSelf: 'center',
        textDecoration: 'none'
    },
    currentUser: {
        alignSelf: 'end'
    }
}))

const Header = ({ className }) => {
    
    const classes = useStyles()

    return (
        <header className={clsx(className, classes.root)}>
            <Container className={classes.container}>
                <Typography component={RouterLink} to="/home" variant="body1" className={classes.title}>Some Fine Application</Typography>
                <CurrentUser className={classes.currentUser}/>
            </Container>
        </header>
    )
}

export default Header
