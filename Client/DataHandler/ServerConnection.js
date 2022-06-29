const net = require('net');
let conn;
if (!conn) {
    conn = net.createConnection(9000, "localhost", () => {
        console.log('Connected to server\n');
    });
}

let resQue = [];

function sendRequest(req, callback) {
    conn.write(JSON.stringify(req) + '\n');
    resQue.push(callback);
}

conn.on('data', (data) => {
    if (resQue.length) {
        let res = resQue.shift();
        data = data.toString('utf-8');
        res(JSON.parse(data));
    }
});

exports.sendRequest = sendRequest;