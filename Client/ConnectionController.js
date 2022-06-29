const net = require('net');
const readline = require('readline');

const client = net.createConnection(9000, process.argv[2], () => {
    console.log('Connected to server\n');
});
client.on('data', (data) => {
    console.log(data.toString('utf-8'));
});

const rl = readline.createInterface({ input: process.stdin });

rl.on('line', (line) => {
    // client.write(`${line}\n`);
    client.write(
        JSON.stringify({
            msg: line,
            time: new Date()
        }) + '\n'
    );
});

rl.on('close', () => {
    client.end();
});