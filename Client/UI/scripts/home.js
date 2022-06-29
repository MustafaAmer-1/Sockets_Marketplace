let currentActiveCategory;
let itemsHandler;
let ProductContainer;
let searchButton;
let searchInput;
let cartHandler;
let addCartButtons;

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

function changeActiveElement(element) {
    if (currentActiveCategory) currentActiveCategory.classList.remove("active")
    currentActiveCategory = element;
    currentActiveCategory.classList.add("active")
}

function showItems(items) {
    console.log(items)
    itemsNodes = []
    items.forEach(item => {

        let node = `
            <div class="pro" data-id=${item.id}>
                <img src="data:image/webp;base64,${item.image}">
                <h1>${item.name}</h1>
                <p class="price">$${item.price}</p>
                <div class="qty" data-maxqty=${item.quantity}>
                    <span class="minus" onclick="decrementQty(this)">-</span>
                    <span class="num">0</span>
                    <span class="plus" onclick="incrementQty(this)">+</span>
                </div>
                <p class="cartbtn">
                    <button onclick="addCartEvent(this)">Add to cart</button>
                </p>
            </div>
        `
        itemsNodes.push(node)
    });

    ProductContainer.innerHTML = itemsNodes.join('')

}

function findSiblingWithClassName(element, className) {
    let par = element.parentElement

    for (node of par.children) {
        if (node.classList.contains(className)) return node;
    }
}

function incrementQty(e) {
    let sibling = findSiblingWithClassName(e, 'num')
    let qty = parseInt(sibling.innerHTML)
    let maxQty = parseInt(e.parentElement.dataset.maxqty)
    if (qty < maxQty)
        sibling.innerHTML = qty + 1
}

function decrementQty(e) {
    let sibling = findSiblingWithClassName(e, 'num')
    let qty = parseInt(sibling.innerHTML)
    if (qty > 0)
        sibling.innerHTML = qty - 1

}

function HandleTagClick(e) {
    let element = e.target;
    changeActiveElement(element);
    itemsHandler.getItems({
        filter: 'category',
        query: element.name
    }, showItems)
}

function searchFor(e) {
    let input = searchInput.value

    itemsHandler.getItems({
        filter: 'search',
        query: input
    }, showItems)
    searchInput.value = ''

}

function addCartEvent(e) {
    itemNode = e.parentNode.parentNode
    itemData = {
        id: itemNode.dataset.id,
    }
    for (let i = 0; i < itemNode.children.length; ++i) {

        if (itemNode.children[i].tagName == 'H1')
            itemData.name = itemNode.children[i].innerHTML
        else if (itemNode.children[i].classList.contains('price'))
            itemData.price = itemNode.children[i].innerHTML.substring(1)
        else if (itemNode.children[i].classList.contains('qty')) {
            itemData.quantity = parseInt(itemNode.children[i].querySelector('.num').innerHTML)
            itemData.maxqty = itemNode.children[i].dataset.maxqty
        }
    }
    if (itemData.quantity == 0) alert('Please add an item')
    else cartHandler.addItem(itemData)
}

function startUp() {

    currentActiveCategory = document.getElementById("home")
    tagList = document.querySelectorAll('.tag')
    searchButton = document.getElementById('searchButton')
    ProductContainer = document.querySelector('.products-container')
    searchInput = document.getElementById('searchInput')
    addCartButtons = document.getElementsByClassName('.cartBtn')

    itemsHandler = new ItemsHandler();
    cartHandler = new CartHandler()

    tagList.forEach(tag => {
        tag.addEventListener('click', HandleTagClick)
    });

    searchButton.addEventListener('click', searchFor)

    itemsHandler.getItems({
        filter: 'all',
        query: '*'
    }, showItems)

}

startUp();