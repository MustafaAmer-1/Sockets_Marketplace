let userHandler;

function populateForm() {
    let userData = userHandler.getUserInfo();
    
    document.getElementsByName('name')[0].value = userData.name
    document.getElementsByName('email')[0].value = userData.email
    document.getElementsByName('telephone')[0].value = userData.telephone
    document.getElementsByName('country')[0].value = userData.country
    document.getElementsByName('balance')[0].value = userData.balance


}
function startUp() {
    userHandler = new UserHandler()
    populateForm()
}

startUp()