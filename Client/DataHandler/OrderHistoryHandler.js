const path = require('path')
class OrdersHistoryHandler {

    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
    }

    getOrdersHistory(callback) {
        this.conn.sendRequest({
            action: 'orders_history'
        }, (res) => {
            if (res.status) {
                callback(res.orders)
            } else {
                console.log(res.error);
            }
        });
    }

    /* 
    getOrdersHistory(callback) {
        const res = {
            status: true,
            orders: [{
                    oid: 1,
                    items: [{
                            name: 'sokar',
                            quantity: 20,
                            price: 15
                        },
                        {
                            name: 'shai',
                            quantity: 10,
                            price: 15
                        },
                        {
                            name: 'makarona',
                            quantity: 50,
                            price: 15
                        }
                    ],
                    total_price: 100
                },
                {
                    oid: 2,
                    items: [{
                            name: 'le3ba',
                            quantity: 20,
                            price: 15
                        },
                        {
                            name: 'keyboard',
                            quantity: 10,
                            price: 60
                        },
                        {
                            name: 'fady',
                            quantity: 100,
                            price: 90
                        }
                    ],
                    total_price: 1500
                }
            ]
        }
        callback(res.orders);
    }
        */
}

module.exports = OrdersHistoryHandler