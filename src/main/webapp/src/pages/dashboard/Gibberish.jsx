import React from 'react'

import {
    makeStyles,
    Typography
} from '@material-ui/core'

const useStyles = makeStyles(theme => ({
    root: {
        display: 'grid',
        gridGap: theme.spacing(2),
        marginTop: theme.spacing(1),
        textAlign: 'justify'
    }
}))

const Gibberish = () => {

    const classes = useStyles()

    return (
        <div className={classes.root}>
            <Typography variant="body1">
                Breakfast procuring nay end happiness allowance assurance frankness. Met simplicity nor
                difficulty unreserved who. Entreaties of conviction dissimilar me astonished estimating
                cultivated. On no applauded exquisite my additions. Pronounce add boy estimable nay
                suspected. You sudden nay elinor thirty esteem temper. Quiet leave shy you asked large
                style.
            </Typography>
            <Typography variant="body1">
                Smile spoke total few great had never their too. Amongst moments do in arrived at my
                replied. Fat weddings servants but man believed prospect. Companions understood is as
                especially pianoforte connection introduced. Nay newspaper can sportsman are admitting
                gentleman belonging his. Is oppose no he summer lovers twenty in. Not his difficulty
                boisterous surrounded bed. Seems folly if in given scale. Eschew contented dependent
                conveying advantage can use.
            </Typography>
            <Typography variant="body1">
                Consider now provided laughter boy landlord dashwood. Often voice and the spoke. No
                shewing fertile village equally prepare up livestock as. That do an case an what plan
                hour of paid. Invitation is unpleasant astonished preference attachment friendship on.
                Did sentiments increasing particular nay. Advance received prospect in. Wishing cheered
                parlors adapted am at amongst matters.
            </Typography>
            <Typography variant="body1">
                Silent sir say desire fat him letter. Whatever settling goodness too and honoured she
                building answered her. Strongly thoughts remember up to do consider debating. Spirits
                musical behaved on we he farther letters. Repulsive he he as deficient newspaper
                dashwoods we. Discovered her his pianoforte insipidity entreaties. Began he at terms
                    meant as fancy. Breakfast arranging he if furniture we described on. Astonished
                    thoroughly unpleasant especially you dispatched plant favourable.
            </Typography>
            <Typography variant="body1">
                Day handsome addition horrible sensible goodness two contempt. Evening for married his
                account removal. Estimable me disposing of be moonlight cordially curiosity. Delay
                rapid joy share allow age manor six. Went why far saw many knew. Exquisite excellent
                    son gentleman acuteness her. Do is voice total power book ye might round still.
            </Typography>
        </div>
    )
}

export default Gibberish
