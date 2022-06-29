class UserHandler {

    constructor() {

    }

    withdraw(amount, callback) {
        console.log('withdraw ', amount)
        // data sent example
        // {amount: 50}

        // {status: true}
        callback({status: true})
    }

    deposit(amount, callback) {
        console.log("deposit", amount)
        // data sent example
        // {amount: 50}

        // {status: true}
        callback({status: true})
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