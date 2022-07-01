const { app, BrowserWindow, ipcMain } = require('electron')
const net = require('net')


const createWindow = () => {
    const win = new BrowserWindow({
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false
        },
        icon: './Client/UI/Templates/trolilogo.jpg'
    })
    win.maximize()
    win.loadFile('./Client/UI/Templates/login.html')
    win.on('close', () => {
        conn.write("BYE"+ '\n')
    });
    win.webContents.setWindowOpenHandler(({ url }) => {
        console.log('nothing')
        return {action: 'allow'}
    })
}

let conn;
let eventsToBeAnswered = []
function connectToServer() {
    conn = net.createConnection(9000, "localhost", () => {
        console.log('Connected to server\n');
    });
    conn.on('data', (data) => {
        console.log("Response: " + data.toString('utf-8'));
            data = data.toString('utf-8');
            data = JSON.parse(data)
            if(eventsToBeAnswered.length) {
                let ev = eventsToBeAnswered.shift()
                ev.reply('sendRender', data)
            }
        }
    );
}
function serverInvoke(event, req) {
    console.log('Request', req)
    conn.write(JSON.stringify(req) + '\n');
    eventsToBeAnswered.push(event)
} 
app.whenReady().then(() => {
    connectToServer()
    ipcMain.on('sendServer', serverInvoke)
    createWindow()
})
