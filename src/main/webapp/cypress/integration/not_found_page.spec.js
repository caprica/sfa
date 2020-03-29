describe('The Not Found Page', () => {

    it('Shows the Not Found page', () => {
        cy.visit('/not-found')

        cy.url()
            .should('include', '/not-found')

        cy.contains('Page not found')
    })

    it('Shows the Home page when the Home link is clicked', () => {
        cy.visit('/not-found')

        cy.url()
            .should('include', '/not-found')

        cy.contains('Home')
            .closest('a')
            .click()

        cy.url()
            .should('include', '/home')
    })

    it('Forwards an unknown route to the Not Found page', () => {
        cy.visit('/this/route/does/not/exist')

        cy.url()
            .should('include', '/this/route/does/not/exist')

        cy.contains('Page not found')
    })
})
