// FIXME some of these imports should be available by setup, not repeated here? i can't get it working though
import React from 'react'

import {
    render,
    screen
} from '@testing-library/react'

import CurrentYear from '../CurrentYear'

describe('Current Year', () => {

    it('renders the current year', () => {
        const expected = '' + new Date().getFullYear()
        render(<CurrentYear/>)
        expect(screen.getByText(expected)).toBeInTheDocument()
    })

})
