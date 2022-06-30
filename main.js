const { app, BrowserWindow } = require('electron')

const onExit = require('./Client/DataHandler/ServerConnection').onExit

const createWindow = () => {
    const win = new BrowserWindow({
        width: 700,
        height: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false
        },
        icon: './Client/UI/Templates/trolilogo.jpg'
    })
    win.removeMenu()
    win.loadFile('./Client/UI/Templates/login.html')
    win.on('close', () => {
        onExit()
    });
}

app.whenReady().then(() => {
    createWindow()
})