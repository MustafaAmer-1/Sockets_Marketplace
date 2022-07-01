const ipcRenderer  = require('electron').ipcRenderer

async function dummy() {
    await reveive()
}
dummy()
let resQue = [];

function sendRequest(req, callback) {
    console.log('data to sent ', req)
    ipcRenderer.send('sendServer', req)
    resQue.push(callback)
}

async function reveive() {
    ipcRenderer.on("sendRender", (_event, res) => {
        console.log('recieved: ', res)
        if(resQue.length) {
            let resCallback = resQue.shift()
            resCallback(res)
        }
    })
    return
}


const onExit = function() {
    sendRequest("BYE")
}

exports.sendRequest = sendRequest;
exports.onExit = onExit;