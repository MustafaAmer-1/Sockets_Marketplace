
let form;
let loginHandler;
function responseCallBack(reqStatus) {
    if(reqStatus.status) {
        console.log('success')
        window.location.href = "home.html"
    } else {
        alert('Wrong username or password')
    }
}
function formHandler(e) {
    e.preventDefault()

    let formData = new FormData(form)
    let loginDate = {}

    for(entry of formData.entries()) {
        loginDate[entry[0]] = entry[1]
    }

    loginHandler.login(loginDate, responseCallBack)
}
function startUp() {
    form = document.querySelector('form')
    form.addEventListener('submit', formHandler)
    loginHandler = new LoginHandler()
}

startUp()
