
let userHandler;
let balanceField;
const UserHandler = require('../../DataHandler/UserHandler')

function responseCallBackGenerator(action) {
    return function (reqStatus) {
            if(reqStatus.status) {
            smalltalk.alert('Success', `Successful ${action} operation`).catch(() => console.log('error'))
        } else {
            smalltalk.alert('Error', "Could not perform " + action)
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
    smalltalk.prompt('Deposit Request', 'Please Enter Deposit Amount', '')
    .then((value) => {
        value = parseInt(value)
        if(isNaN(value)) responseCallBackGenerator('Deposit')({status: false})
        else depositEvent(value)
    })
    .catch(console.error)
}

function getConfirmationforredraw() {
    smalltalk.prompt('Withdraw Request', 'Please Enter  Withdraw Amount', '')
    .then((value) => {
        value = parseInt(value)
        if(isNaN(value)) responseCallBackGenerator('Withdraw')({status: false})
        else if(value > balanceField.value) 
            smalltalk.alert("Error", "No sufficient  money to fulfill your operation.").catch(() => console.log('error'))
        else withdrawEvent(value)
    })
    .catch(console.error)
    
}

function startUp() {
    userHandler = new UserHandler()
    balanceField = document.getElementsByName('balance')[0]
    userHandler.getUserInfo(populateBalance)
}
startUp()