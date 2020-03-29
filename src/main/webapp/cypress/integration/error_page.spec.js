describe('The Error Page', () => {

    it('Shows the Error page if there is a status code', () => {
        cy.visit('/system-error/500')

        cy.url()
            .should('include', '/system-error/500')

        cy.contains('System error')

        cy.contains('(status code 500)')
    })

    it('Shows the Home page when the Home link is clicked', () => {
        cy.visit('/system-error/500')

        cy.url()
            .should('include', '/system-error/500')

        cy.contains('Home')
            .closest('a')
            .click()

        cy.url()
            .should('include', '/home')
    })

    it('Shows the Not Found page if there is no status code', () => {
        cy.visit('/system-error')

        cy.url()
            .should('include', '/system-error')

        cy.contains('Page not found')
    })
})
