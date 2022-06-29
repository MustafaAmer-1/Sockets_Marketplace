const path = require('path')

class UserHandler {

    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
    }

    withdraw(amount, callback) {
        console.log('withdraw ', amount)
            // data sent example
            // {amount: 50}

        // {status: true}
        this.conn.sendRequest({
            action: 'withdraw',
            data: amount
        }, callback);
    }

    deposit(amount, callback) {
        console.log("deposit", amount)
            // data sent example
            // {amount: 50}

        // {status: true}

        this.conn.sendRequest({
            action: 'deposite',
            data: amount
        }, callback);
    }


    getUserInfo(callback) {
        // send request to server to get data of user
        // expected data 
        this.conn.sendRequest({
            action: 'userinfo'
        }, callback);
    }
}

module.exports = UserHandler