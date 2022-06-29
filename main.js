const {app, BrowserWindow } = require('electron')

const createWindow = () => {
    const win = new BrowserWindow({
        width: 700,
        height: 600,
        webPreferences: {
            nodeIntegration:  true,
            contextIsolation: false
        }
    })
    win.removeMenu()
    win.loadFile('./Client/UI/Templates/home.html')
}

app.whenReady().then(() => {
    createWindow()
}) 
