
class CartHandler {
    
    constructor() {
        this.db = new Nedb({ filename: '/cartPresistData.db', autoload: true })
    }
    addItem(item) {
        this.db.update({id: item.id}, item, {upsert: true}, function(err, doc) {
            if(err) console.log("error", err)
            else alert('Item Added successfully')
        })
    }

    getCartItems() {
        this.db.loadDatabase()
        this.db.find({}, function(err, docs) {
            if(err) console.log("error", err)
        }) 
        return []
    }

    getCartItemsCallback(callback) {
        this.db.loadDatabase()
        this.db.find({}, function(err, docs) {
            if(err) console.log("error", err)
            callback(docs)
        }) 
    }   

    deleteCartItem(id) {
        this.db.remove({id: id}, function(err, numberRemoved) {
            if(err) console.log("error", err)
        })
    }
    saveCartToServer() {

    }
}