let form;
let registerHandler;
function responseCallBack(reqStatus) {
    if(reqStatus.status) {
        console.log('success')
        window.location.href = "login.html";
    } else {
        alert('something went wrong')
    }
}

function formHandler(e) {
    e.preventDefault()
    signFormData = {}

    data = new FormData(form)
    for(d of data.entries()) {
        signFormData[d[0]] = d[1]
    }

    registerHandler.register(signFormData, responseCallBack)
        
}

function startUp() {
    form = document.querySelector('form')
    form.addEventListener('submit', formHandler)

    registerHandler = new RegisterHandler()

}
startUp();