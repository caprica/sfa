/**
 * Database initialisation script for MongoDB.
 *
 * Any files copied to the "/docker-entrypoint-initdb.d/" directory in the container will be executed by the MongoDB
 * instance on startup.
 *
 * Instead of copying files from the local filesystem to the container, it would be possible instead to map/mount a
 * local directory directly to the container directory.
 */


// Create an application user for the "SFA" database, this is the Spring Boot repository user.

db.createUser(
    {
        user : 'sfa',
        pwd  : 'veryfineindeed',
        roles: [
            {
                role: 'readWrite',
                db  : 'sfa'
            }
        ]
    }
)


// Switch to the SFA database

db = db.getSiblingDB('sfa')


// Create document indices

db.user.createIndex({username:1})

db.profile.createIndex({username:1})


// Create seed-data for the user collection

// pw: toomanysecrets
db.user.insert(
    {
        'username'   : 'mark',
        'password'   : '$2y$10$WxFrxAmdzXl21MSwydIBXeNzRCLqCeTzxPY6ow/0yq.v1ZfLl/6R6',
        'authorities': [
            'GET_MY_USER',
            'GET_MY_PROFILE',
            'UPDATE_MY_PROFILE'
        ]
    }
)

// pw: nexus6
db.user.insert(
    {
        'username'   : 'deckard',
        'password'   : '$2y$10$Pyax032BmLcgazp1xYWygeCpfapylu5cUI5fRe5Xk.nmNPQ7Ae1lm',
        'authorities': [
            'GET_MY_USER',
            'GET_MY_PROFILE',
            'UPDATE_MY_PROFILE'
        ]
    }
)

// pw:daisy
db.user.insert(
    {
        'username'   : 'babayaga',
        'password'   : '$2y$10$vnI90h6Y2nkJAEz33.XO1.fCbkuV84Mjqvy.vlrfxlfJ/OvmoDequ',
        'authorities': [
            'GET_MY_USER',
            'GET_MY_PROFILE',
            'UPDATE_MY_PROFILE'
        ]
    }
)

// pw:iambatman
db.user.insert(
    {
        'username'   : 'bruce',
        'password'   : '$2y$10$DnsBGCMU26myi0Si1JAaL.ekIyRCi38uF1ONGwe8xB.bYvcH2EHxe',
        'authorities': [
            'GET_MY_USER',
            'GET_MY_PROFILE',
            'UPDATE_MY_PROFILE'
        ]
    }
)


// Create seed-data for the profile collection

db.profile.insert(
    {
        'username' : 'mark',
        'firstName': 'Mark',
        'lastName' : 'Lee',
        'position' : 'The Boss',
        'aboutMe'  : 'I am like so totes awesome.'
    }
)

db.profile.insert(
    {
        'username' : 'deckard',
        'firstName': 'Rick',
        'lastName' : 'Deckard',
        'position' : 'Blade Runner',
        'aboutMe'  : 'Blade Runner, retired. I\'ve seen things you people wouldn\'t believe.'
    }
)

db.profile.insert(
    {
        'username' : 'babayaga',
        'firstName': 'John',
        'lastName' : 'Wick',
        'position' : 'Assassin',
        'aboutMe'  : 'I am revenge. And somewhat totally awesome.'
    }
)

db.profile.insert(
    {
        'username' : 'bruce',
        'firstName': 'Bruce',
        'lastName' : 'Wayne',
        'position' : 'Vigilante',
        'aboutMe'  : 'The world\'s greatest detective. Dark Knight. Eccentric billionaire.'
    }
)
