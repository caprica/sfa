describe('The Home Page', () => {

    it('Shows the home page', () => {
        cy.visit("")

        cy.contains('Get Started')
    })

    it('Shows the home page for index.html', () => {
        cy.visit("index.html")

        cy.contains('Get Started')
    })
})
