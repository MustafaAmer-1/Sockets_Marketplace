
let userHandler;

function populateBalance(userData) {
    console.log(userData)
    document.getElementsByName('balance')[0].value = userData.balance
}

function startUp() {
    userHandler = new UserHandler()
    userHandler.getUserInfo(populateBalance)
}

function getConfirmationfordeposit() {
    var resp = window.prompt("Please enter Deposit Amount")
}

function getConfirmationforredraw() {
    var resp = window.prompt("Please enter withdrawal Amount")
}

startUp()