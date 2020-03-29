import { authenticationService } from '../../src/components/security/authentication-service'

describe('The Dashboard Page', () => {

    beforeEach(() => {
        cy.wrap(authenticationService.logout())
        cy.wrap(authenticationService.login('mark', 'toomanysecrets'))
    })

    it('Shows the Dashboard page', () => {
        cy.visit('dashboard')

        cy.url()
            .should('include', '/dashboard')

        cy.get('.MuiCard-root')
    })

    it('Shows the Edit My Profile page when edit profile clicked', () => {
        cy.visit('dashboard')

        cy.url()
            .should('include', '/dashboard')

        cy.contains('Edit My Profile')
            .closest('a')
            .click()

        cy.url()
            .should('include', '/my-profile/edit')
    })

})
