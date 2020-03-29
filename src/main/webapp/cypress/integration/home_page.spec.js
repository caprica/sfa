describe('The Home Page', () => {

    it('Shows the Home page', () => {
        cy.visit('')

        cy.contains('Get Started')
    })

    it('Redirects index.html to the Home page', () => {
        cy.visit('index.html')

        cy.contains('Get Started')
    })
})
