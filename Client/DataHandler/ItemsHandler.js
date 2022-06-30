const path = require('path')
class ItemsHandler {
    // Images are expected to be in base64 encoding 

    constructor() {
        this.conn = require(path.join(__dirname, 'ServerConnection'));
    }
    getItems(request, callback) {

        // will send in the request
        // {filter: search, query: "searchQuery"}
        // or 
        // {filter: category, query: "cat"}
        // maybe we will add some other data related to authenticated user

        this.conn.sendRequest({
            action: request.filter,
            data: request.query
        }, (res) => {
            if (res.status) {
                callback(res.items)
            } else {
                console.log(res.error);
            }
        });
    }

}

module.exports = ItemsHandler