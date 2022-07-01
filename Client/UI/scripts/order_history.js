let OrdersHandler;
let OrdersContainer;

const OrdersHistoryHandler = require('../../DataHandler/OrderHistoryHandler')

function showOrders(orders) {
    ordersNodes = [];
    orders.forEach(order => {
        let node = `
        <div class="form-style-3">
                <h3>Items of Order #${order.oid}</h3>
                <table>
                    <tr>
                        <th>Item</th>
                        <th>Price</th>
                        <th>Qty</th>
                    </tr>
                    ${formatItems(order.items)}
                </table>
                <p>Tatal Price: $${order.total_price}</p>
            </div>
        `;
        ordersNodes.push(node);
    });
    OrdersContainer.innerHTML = ordersNodes.join('');
}

function formatItems(items) {
    itemsNodes = [];
    items.forEach(item => {
        let node = `
        <tr>
            <td>${item.name}</td>
            <td>${item.quantity}</td>
            <td>${item.price}</td>
        </tr>
        `
        itemsNodes.push(node);
    });
    return itemsNodes.join('');
}

function startUp() {
    OrdersHandler = new OrdersHistoryHandler();
    OrdersContainer = document.querySelector('.orders_container');
    OrdersHandler.getOrdersHistory(showOrders);
}

startUp()