class LoginHandler {
    constructor() {
        this.conn = require('./ServerConnection');
    }
    login(userInfo, callback) {
        console.log(userInfo)

        this.conn.sendRequest({
            action: 'login',
            data: userInfo
        }, callback);
    }
}