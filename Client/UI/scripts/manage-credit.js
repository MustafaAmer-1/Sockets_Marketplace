
let userHandler;

function populateBalance(userData) {
    console.log(userData)
    document.getElementsByName('balance')[0].value = userData.balance
}

function startUp() {
    userHandler = new UserHandler()
    userHandler.getUserInfo(populateBalance)
}

startUp()