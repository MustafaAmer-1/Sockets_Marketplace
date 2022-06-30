const net = require('net');
let conn;
if (!conn) {
    conn = net.createConnection(9000, "localhost", () => {
        console.log('Connected to server\n');
    });
}

let resQue = [];

function sendRequest(req, callback) {
    console.log("request: " + req);
    conn.write(JSON.stringify(req) + '\n');
    resQue.push(callback);
}

conn.on('data', (data) => {
    console.log("response: " + data.toString('utf-8'));
    if (resQue.length) {
        let res = resQue.shift();
        data = data.toString('utf-8');
        res(JSON.parse(data));
    }
});

const onExit = function() {
    conn.write("BYE\n");
}

exports.sendRequest = sendRequest;
exports.onExit = onExit;