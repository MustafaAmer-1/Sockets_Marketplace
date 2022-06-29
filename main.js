const {app, BrowserWindow } = require('electron')

const createWindow = () => {
    const win = new BrowserWindow({
        width: 700,
        height: 600,
    })
    win.removeMenu()
    win.loadFile('./Client/UI/Templates/home.html')
}

app.whenReady().then(() => {
    createWindow()
}) 
