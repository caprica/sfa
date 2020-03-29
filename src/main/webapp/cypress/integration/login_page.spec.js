// FIXME https://docs.cypress.io/guides/getting-started/testing-your-app.html#Logging-in

// FIXME it might be better to use data-attributes for selectors, not sure
//  i will still have some materialui issues with finding the real element

describe('The Login Page', () => {

    it('Shows the Login page', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')
    })

    it('Enables login button only when inputs are filled', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')

        cy.get('button')
            .contains('Login')
            .closest('button')
            .as('loginButton')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.get('@loginButton')
            .should('be.disabled')

        cy.get('input[type=text]')
            .type('some user')
            .should('have.value', 'some user')

        cy.get('@loginButton')
            .should('be.disabled')

        cy.get('input[type=password]')
            .type('some password')
            .should('have.value', 'some password')

        cy.get('@loginButton')
            .should('not.be.disabled')
    })

    it('Advances focus from username to password when enter key is pressed', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.focused()
            .should('have.attr', 'type', 'text')

        cy.get('input[type=text]')
            .type('some user')
            .should('have.value', 'some user')
            .type('{enter}')

        cy.focused()
            .should('have.attr', 'type', 'password')
    })

    // FIXME can I assert that the login button was pressed when enter was pressed in the password input?

    it('Shows error message and resets inputs when login credentials are invalid', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')

        cy.get('button')
            .contains('Login')
            .closest('button')
            .as('loginButton')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.get('input[type=text]')
            .type('unknown user')
            .should('have.value', 'unknown user')

        cy.get('input[type=password]')
            .type('invalid password')
            .should('have.value', 'invalid password')

        cy.get('@loginButton')
            .should('not.be.disabled')
            .click()

        cy.url()
            .should('include', '/login')

        cy.get('div[role="alert"]')
            .contains('Login Failure')

        cy.get('input[type=text]')
            .should('value.value', '')

        cy.get('input[type=password]')
            .should('value.value', '')

        cy.focused()
            .should('have.attr', 'type', 'text')

        cy.get('@loginButton')
            .should('be.disabled')
    })

    it('Resets inputs when the clear button is pressed', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.get('input[type=text]')
            .type('user')
            .should('have.value', 'user')

        cy.get('input[type=password]')
            .type('password')
            .should('have.value', 'password')

        cy.get('button')
            .contains('Clear')
            .closest('button')
            .click()

        cy.focused()
            .should('have.attr', 'type', 'text')
    })

    it('Shows default (Home) page after a successful login', () => {
        cy.visit('login')

        cy.url()
            .should('include', '/login')

        cy.get('button')
            .contains('Login')
            .closest('button')
            .as('loginButton')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.get('@loginButton')
            .should('be.disabled')

        cy.get('input[type=text]')
            .type('mark')
            .should('have.value', 'mark')

        cy.get('input[type=password]')
            .type('toomanysecrets')
            .should('have.value', 'toomanysecrets')

        cy.get('@loginButton')
            .should('not.be.disabled')
            .click()

        cy.url()
            .should('include', '/home')

        cy.contains('Logged in as mark')
    })

    it('Shows requested page after a successful login', () => {
        cy.visit('dashboard')

        cy.url()
            .should('include', '/login')

        cy.get('button')
            .contains('Login')
            .closest('button')
            .as('loginButton')

        cy.contains('Logged in as')
            .should('not.exist')

        cy.get('@loginButton')
            .should('be.disabled')

        cy.get('input[type=text]')
            .type('mark')
            .should('have.value', 'mark')

        cy.get('input[type=password]')
            .type('toomanysecrets')
            .should('have.value', 'toomanysecrets')

        cy.get('@loginButton')
            .should('not.be.disabled')
            .click()

        cy.url()
            .should('include', '/dashboard')

        cy.contains('Logged in as mark')
    })

})
