import axios from 'axios'

const SERVICE_URL = '/api/my-profile'

const getMyProfile = async () => {
    return axios.get(
        SERVICE_URL
    )
    .then(response => response.data)
    .catch(error => Promise.reject(error))
}

const updateMyProfile = async (profile) => {
    return axios.put(
        SERVICE_URL,
        profile
    )
    .then(response => response.data)
    .catch(error => Promise.reject(error))
}

export const myProfileService = {
    getMyProfile,
    updateMyProfile
}
