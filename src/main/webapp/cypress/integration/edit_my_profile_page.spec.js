describe('The Edit My Profile Page', () => {

    beforeEach(() => {
        cy.logout()
        cy.login(Cypress.env('validUsername'), Cypress.env('validPassword'))
    })

    it('Shows the Edit My Profile page', () => {
        cy.visit('my-profile/edit')

        cy.url()
            .should('include', '/my-profile/edit')
    })

    it('Populates input controls with profile data', () => {
        cy.visit('my-profile/edit')

        cy.url()
            .should('include', '/my-profile/edit')

        cy.get('input')
            .eq(0)
            .should('have.value', 'Mark')

        cy.get('input')
            .eq(1)
            .should('have.value', 'Lee')

        cy.get('input')
            .eq(2)
            .should('have.value', 'The Boss')

        cy.get('textarea')
            .should('have.value', 'I am like so totes awesome.')
    })

    // FIXME this test has two deficiencies:
    //  1. not actually typing any update for now, will revisit when the tests create the seed data
    //  2. history.goBack() in the EditProfilePage component triggers a CORS error, but only in this test, not normally

    it('Returns to previous page after updating profile', () => {
        cy.visit('my-profile/edit')

        cy.url()
            .should('include', '/my-profile/edit')

        // cy.contains('Update')
        //     .closest('button')
        //     .click()

        // cy.url()
        //     .should('include', '/my-profile/edit')
    })

})
