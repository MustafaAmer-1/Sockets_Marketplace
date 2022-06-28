let form;
let registerHandler;

function formHandler(e) {
    e.preventDefault()
    signFormData = {}

    data = new FormData(form)
    for(d of data.entries()) {
        signFormData[d[0]] = d[1]
    }

    if(registerHandler.register(signFormData)) {
        console.log('success')
        // window.location.href = "login.html";
    } else {
        alert('something went wrong')
    }
}

function startUp() {
    form = document.querySelector('form')
    form.addEventListener('submit', formHandler)

    registerHandler = new RegisterHandler()

}
startUp();