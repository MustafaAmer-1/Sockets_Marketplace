class CartHandler {

    constructor() {
        this.db = new Nedb({ filename: '/cartPresistData.db', autoload: true })
        this.conn = require('./ServerConnection');
    }
    addItem(item) {
        this.db.update({ id: item.id }, item, { upsert: true }, function(err, doc) {
            if (err) console.log("error", err)
            else alert('Item Added successfully')
        })
    }

    getCartItems() {
        this.db.loadDatabase()
        this.db.find({}, function(err, docs) {
            if (err) console.log("error", err)
        })
        return []
    }

    getCartItemsCallback(callback) {
        this.db.loadDatabase()
        this.db.find({}, function(err, docs) {
            if (err) console.log("error", err)
            callback(docs)
        })
    }

    deleteCartItem(id) {
        this.db.remove({ id: id }, function(err, numberRemoved) {
            if (err) console.log("error", err)
        })
    }

    placeOrder(callback) {
        // send data of  order in format
        // {items: [
        //    {id: 4, quantity: 5}
        //    {id: 5, quantity: 5}
        // ]}

        // should get { status: true }
        // or 
        // {status: false, err: "something"}
        let innerCallback = function(reqStatus) {
            if (reqStatus.status) {
                this.db.remove({}, { multi: true }, function(err, numberRemoved) {
                    if (err) console.log('error', err)
                    else console.log(numberRemoved)
                })
                callback()
                alert('Order placed successfully')
            } else {
                alert('Something went wrong')
            }
        }.bind(this)

        this.db.find({}, function(err, docs) {
            docs = docs.map((doc) => {
                delete doc.name
                delete doc.maxqty
                delete doc.price
                return doc
            })

            // Send data here
            this.conn.sendRequest({
                action: 'order',
                data: docs
            }, innerCallback);
        })
    }
}