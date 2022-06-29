class UserHandler {

    constructor() {

    }

    
    getUserInfo(callback) {
        // send request to server to get data of user
        // expected data 
        let userData = {
            email: 'hello@gmail',
            name: 'testing',
            telephone: 'nothing',
            country: 'Egypt',
            balance: '50'
        }

        callback(userData)
    }
}