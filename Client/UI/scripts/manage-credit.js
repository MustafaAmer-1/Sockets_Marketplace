
let userHandler;
let balanceField;
const UserHandler = require('../../DataHandler/UserHandler')

function responseCallBackGenerator(action) {
    return function (reqStatus) {
            if(reqStatus.status) {
            alert(`successful ${action} operation`)
        } else {
            alert("Could not perform " + action)
        }
    }
}
function populateBalance(userData) {
    balanceField.value = userData.balance
}

function depositEvent(amount) {
    userHandler.deposit(amount, responseCallBackGenerator('Deposit'))
}
function withdrawEvent(amount) {
    userHandler.withdraw(amount, responseCallBackGenerator('Withdraw'))
}


function getConfirmationfordeposit() {
    var resp = parseInt(window.prompt("Please enter Deposit Amount"))
    depositEvent(resp)
}

function getConfirmationforredraw() {
    var resp = parseInt(window.prompt("Please enter withdrawal Amount"))
    if(resp > balanceField.value) alert("No sufficient  money to fulfill your operation.")
    else withdrawEvent(resp)
    
}

function startUp() {
    userHandler = new UserHandler()
    balanceField = document.getElementsByName('balance')[0]
    userHandler.getUserInfo(populateBalance)
}
startUp()