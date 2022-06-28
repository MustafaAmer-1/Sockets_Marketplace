
let form;
let loginHandler;

function formHandler(e) {
    e.preventDefault()

    let formData = new FormData(form)
    let loginDate = {}

    for(entry of formData.entries()) {
        loginDate[entry[0]] = entry[1]
    }

    if(loginHandler.login(loginDate)) {
        console.log('success')
        // redirect to home page
    } else {
        alert('Wrong email or password')
    }
}
function startUp() {
    form = document.querySelector('form')
    form.addEventListener('submit', formHandler)
    loginHandler = new LoginHandler()
}

startUp()
