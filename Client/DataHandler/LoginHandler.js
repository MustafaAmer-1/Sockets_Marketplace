const path = require('path')

class LoginHandler {
    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
    }
    login(userInfo, callback) {
        console.log(userInfo)

        this.conn.sendRequest({
            action: 'login',
            data: userInfo
        }, callback);
    }

    // data should be the user cart
    logout(callback) {
        this.conn.sendRequest({
            action: 'logout',
            data: {}
        }, callback)
    }
}

module.exports = LoginHandler