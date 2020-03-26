import React from 'react'

import { makeStyles } from '@material-ui/core'

import Header from './Header'
import Footer from './Footer'

const useStyles = makeStyles(theme => ({
    root: {
        display: 'grid',
        gridTemplateRows: 'auto 1fr auto',
    },
    header: {
    },
    content: {
    },
    footer: {
        marginTop: theme.spacing(6)
    }
}))

const Page = ({children}) => {

    const classes = useStyles()

    return (
        <div className={classes.root}>
            <Header className={classes.header}/>
            <section className={classes.content}>
                {children}
            </section>
            <Footer className={classes.footer}/>
        </div>
    )
}

export default Page
