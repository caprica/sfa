import React from 'react'
import { Link } from 'react-router-dom'
import { makeStyles, Divider } from '@material-ui/core'
import { Container,Paper, Typography } from '@material-ui/core'

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(12, 0)
    },
    content: {
        display: 'grid',
        gridGap: theme.spacing(2, 0),
        padding: theme.spacing(2, 3),
        marginTop: theme.spacing(4),
    },
    link: {
        color: theme.palette.primary.light
    }
}))

const NotFoundPage = () => {

    const classes = useStyles()

    return (
        <section className={classes.root}>
            <Container maxWidth="sm">
                <Typography variant="h4">Page not found</Typography>
                <Divider/>
                <Paper className={classes.content} elevation={2} square>
                    <Typography variant="body1">
                        You tried to access a page that does not exist.
                    </Typography>
                    <Typography variant="body1">
                        Return to the <span><Link className={classes.link} to="/">Home</Link></span> page.
                    </Typography>
                </Paper>
            </Container>
        </section>
    )
}

export default NotFoundPage
