import React from 'react'

import {
    makeStyles,
    Container,
    Divider,
    Typography
 } from '@material-ui/core'

import Hero from './Hero'

const useStyles = makeStyles(theme => ({
    root: {
    },
    content: {
        padding: theme.spacing(10, 4),
        gridGap: theme.spacing(2, 8),
        display: 'grid',
        gridTemplateColumns: '1fr 1fr 1fr'
    },
    text: {
        margin: theme.spacing(2, 0),
        textAlign: 'justify'
    }
}))

const HomePage = () => {

    const classes = useStyles()

    return (
        <section className={classes.root}>
            <Hero/>
            <Container className={classes.content}>
                <section>
                    <Typography variant="h6" color="textSecondary">We Do Fine Things</Typography>
                    <Typography className={classes.text} variant="body1">
                        Much did had call new drew that kept. Limits expect wonder law she. Now has you views woman
                        noisy match money rooms. To up remark it eldest length oh passed. Off because yet mistake
                        feeling has men. Consulted disposing to moonlight ye extremity. Engage piqued in on coming.
                        </Typography>
                    <Typography className={classes.text} variant="body1">
                        Is he staying arrival address earnest. To preference considered it themselves inquietude
                        collecting estimating.
                    </Typography>
                </section>
                <section>
                    <Typography variant="h6" color="textSecondary">In A Very Fine Way</Typography>
                    <Typography className={classes.text} variant="body1">
                        Apartments simplicity or understood do it we. Song such eyes had and off. Removed winding ask
                        explain delight out few behaved lasting. Letters old hastily ham sending not chamber because
                        present.
                    </Typography>
                    <Typography className={classes.text} variant="body1">
                        Oh is indeed twenty entire figure. Occasional diminution announcing new now literature
                        terminated.
                    </Typography>
                </section>
                <section>
                    <Typography variant="h6" color="textSecondary">Fine Things Indeed</Typography>
                    <Typography className={classes.text} variant="body1">
                        Do so written as raising parlors spirits elderly they. Made late in of high left hold. Carried
                        females of up highest calling. Limits marked led silent dining her she far. Sir but elegance
                        marriage dwelling likewise position old crooked men.
                    </Typography>
                    <Typography className={classes.text} variant="body1">
                        Dissimilar themselves simplicity no of contrasted as. Delay great day hours men. Stuff there to
                        do allow to asked he.
                    </Typography>
                </section>
                <Divider/>
                <Divider/>
                <Divider/>
            </Container>
        </section>
    )
}

export default HomePage
