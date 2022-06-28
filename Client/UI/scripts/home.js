
let currentActiveCategory;
let itemsHandler;
let ProductContainer;
let searchButton;
let searchInput;
let cartHandler;
let addCartButtons;

function changeActiveElement(element) {
    if(currentActiveCategory) currentActiveCategory.classList.remove("active")
    currentActiveCategory = element;
    currentActiveCategory.classList.add("active")
}

function showItems(items) {
    itemsNodes = []

    items.forEach(item => {
        
        // let node = `
        //     <div class="pro" data-id=${item.id}>
        //         <img src="${item.image}">
        //         <h1>${item.name}</h1>
        //         <p class="price">$${item.price}</p>
        //         <p class="cartbtn" onclick="addCartEvent(this)"><button>Add to Cart</button></p>
        //     </div>
            
        // `

        let node = `
            <div class="pro" data-id=${item.id}>
                <img src="${item.image}">
                <h1>${item.name}</h1>
                <p class="price">$${item.price}</p>
                <div class="qty" data-max=${item.quantity}>
                    <span class="minus">-</span>
                    <span class="num">0</span>
                    <span class="plus">+</span>
                </div>
                <p class="cartbtn"><a href="items.html">
                    <button onclick="onclick="addCartEvent(this)">Add to cart</button>
                </a>
                </p>
            </div>
        `
        itemsNodes.push(node)
    });

    ProductContainer.innerHTML = itemsNodes.join('')

}


function HandleTagClick(e) {
    let element = e.target;
    changeActiveElement(element);
    let items = itemsHandler.getCategorizedItems({category: element.name})
    showItems(items)
}

function searchFor(e) {
    let input = searchInput.value
    console.log(input)

    items = itemsHandler.getSearchItems({ searchQuery: input })
    showItems(items)
    searchInput.value = ''

}

function addCartEvent(e) {
    itemNode = e.parentNode
    itemData = {
        id: itemNode.dataset.id,
    }
    for(let i = 0; i < itemNode.children.length; ++i) {
        if(itemNode.children[i].tagName == 'IMG')
            itemData.image = itemNode.children[i].getAttribute('src')
        else if(itemNode.children[i].tagName == 'H1')
            itemData.name = itemNode.children[i].innerHTML
        else if(itemNode.children[i].classList.contains('price'))
            itemData.price = itemNode.children[i].innerHTML.substring(1)
    }

    cartHandler.addItem(itemData)
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

    showItems(itemsHandler.getCategorizedItems({category: 'home'}))

    
}

startUp();