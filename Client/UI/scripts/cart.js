

let tableItems;
let cartHandlerC;
let checkoutBtn;
var CartHandler = require('../../DataHandler/CartHandler')
function deleteFromCart(e) {
    let row = e.closest('tr')
    let id = row.dataset.id
    cartHandlerC.deleteCartItem(id)
    row.remove()
}
function populateTable(docs) {
    let tableInner = `<tr>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Remove</th>
            </tr>`

    docs.forEach(doc => {
        tableInner += ` <tr data-id=${doc.id}>
            <td>${doc.name}</td>
            <td>${doc.price}</td>
            <td>
                <div  id="idd" class="qty" data-maxqty=${doc.maxqty}>
                <span class="minus" onclick="decrementQty(this)">-</span>
                <span class="num">${doc.quantity}</span>
                <span class="plus" onclick="incrementQty(this)">+</span>
		        </div>
            </td>
            <td><button id="d_btn1" onclick="deleteFromCart(this)"class="dlt">Delete</button></td>
        </tr>
        `
    });
    tableItems.innerHTML = tableInner
}

function findSiblingWithClassName(element, className) {
    let par = element.parentElement

    for(node of par.children) {
        if(node.classList.contains(className)) return node;
    } 
}

function incrementQty(e) {
    console
    let sibling = findSiblingWithClassName(e, 'num')
    let qty = parseInt(sibling.innerHTML)
    let maxQty = parseInt(e.parentElement.dataset.maxqty)
    if(qty < maxQty)
        sibling.innerHTML = qty + 1
}

function decrementQty(e) {
    let sibling = findSiblingWithClassName(e, 'num')
    let qty = parseInt(sibling.innerHTML)
    if(qty > 0)
        sibling.innerHTML = qty - 1

}
function clearTable() {
    const firstElement = tableItems.firstElementChild.firstElementChild
    console.log(firstElement)
    tableItems.innerHTML = ''
    tableItems.append(firstElement)
}
function placeOrder(e) {
    cartHandlerC.placeOrder(clearTable)
}
function startUp() {
    tableItems = document.querySelector('table')
    cartHandlerC = new CartHandler()
    cartHandlerC.getCartItemsCallback(populateTable)
    checkoutBtn = document.getElementById('checkout')
    checkoutBtn.addEventListener('click', placeOrder)
}

startUp()