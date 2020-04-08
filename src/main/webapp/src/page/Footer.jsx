import React from 'react'

import clsx from 'clsx'

import { makeStyles, Container, Typography } from '@material-ui/core'
import { grey } from '@material-ui/core/colors'

import CurrentYear from 'lib/current-year'

import FooterLogo from 'footer-logo.png'

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(4),
        backgroundColor: grey[900],
        color: 'white'
    },
    content: {
        display: 'grid',
        gridGap: theme.spacing(2),
        gridTemplateColumns: '3fr 2fr 2fr',
        marginTop: theme.spacing(4)
    },
    heading: {
        opacity: 0.4
    },
    copyright: {
        opacity: 0.4
    }
}))

const Footer = ({ className }) => {

    const classes = useStyles()

    return (
        <footer className={clsx(className, classes.root)}>
            <Container className={classes.content}>
                <div>
                    <img src={FooterLogo} alt="VeryFineCompany logo" /><br/>
                    <Typography className={classes.copyright}>&copy; <CurrentYear /> VeryFineCompany Limited</Typography>
                </div>
                <div>
                    <Typography className={classes.heading} variant="h6">Very Fine Things</Typography>
                    <p>
                        This Thing
                    </p>
                    <p>
                        That Other Thing
                    </p>
                    <p>
                        Something Else
                    </p>
                    <p>
                        This As Well
                    </p>
                    <p>
                        That Too
                    </p>
                </div>
                <div>
                    <Typography className={classes.heading} variant="h6">About Us</Typography>
                    <p>
                        How Very Fine We Are
                    </p>
                    <p>
                        Very Fine Jobs
                    </p>
                    <p>
                        Contact Us
                    </p>
                </div>
            </Container>
        </footer>
    )
}

export default Footer
