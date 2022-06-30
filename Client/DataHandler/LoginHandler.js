const path = require('path')
const CartHandler = require(path.join(__dirname, 'CartHandler'))

class LoginHandler {
    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
        this.cartHandler = new CartHandler()
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
        let innerCallback = function (cartItems) {
            cartItems = this.cartHandler.cartSendOutFormater(cartItems)
            this.cartHandler.deleteAllCartItems()
            this.conn.sendRequest({
                action: 'logout',
                data: cartItems
            }, callback)

        }.bind(this)

        this.cartHandler.getCartItemsCallback(innerCallback)
    }
}

module.exports = LoginHandler