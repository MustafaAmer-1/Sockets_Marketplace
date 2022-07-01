const path = require('path')
const CartHandler = require(path.join(__dirname, 'CartHandler'))

class LoginHandler {
    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
        this.cartHandler = new CartHandler()
    }

    login(userInfo, callback) {
        console.log(userInfo)
        const innerCallback = function(res) {
            if(res.status) {
                res.cart.forEach(function (item, index, array) {
                    this.cartHandler.addItem(item)
                }.bind(this));
            }
            callback(res)
        }.bind(this)

        this.conn.sendRequest({
            action: 'login',
            data: userInfo
        }, innerCallback);
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
