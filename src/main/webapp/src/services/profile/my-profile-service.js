import axios from 'axios'

const SERVICE_URL = '/api/my-profile'

const getMyProfile = async () => {
    return axios.get(
        SERVICE_URL
    )
    .then(response => {
        console.log("GOT RESPONSE")
        return response.data
    })
    .catch(error => {
        console.log("GOT ERROR")
        return Promise.reject(error)
    })
}

const updateMyProfile = async (profile) => {
    return axios.put(
        SERVICE_URL,
        profile
    )
    .then(response => {
        return response.data
    })
    .catch(error => {
        return Promise.reject(error)
    })
}

export const myProfileService = {
    getMyProfile,
    updateMyProfile
}
