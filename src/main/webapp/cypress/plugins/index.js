/// <reference types="cypress" />
// ***********************************************************
// This example plugins/index.js can be used to load plugins
//
// You can change the location of this file or turn off loading
// the plugins file with the 'pluginsFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/plugins-guide
// ***********************************************************

// This function is called when a project is opened or re-opened (e.g. due to
// the project's config changing)

// Starting maximised is canny annoying on an ultra-wide display
const customWindowPlacement = false

/**
 * @type {Cypress.PluginConfig}
 */
module.exports = (on, config) => {
    // `on` is used to hook into various events Cypress emits
    // `config` is the resolved Cypress config

    if (customWindowPlacement) {
        on('before:browser:launch', (browser = {}, launchOptions) => {
            if (browser.family === 'chromium' && browser.name !== 'electron') {
                launchOptions.args = launchOptions.args.filter(opt => opt !== '--start-maximized')
                launchOptions.args.push('--window-position=1840,0')
                launchOptions.args.push('--window-size=1600,1480')
                return launchOptions
             }
        })
    }
}
