const Stomp = require('stompjs');
let client = null;

export function connect() {
    client = Stomp.client('ws://localhost:8081/ws-stomp');
    client.connect({}, () => {
        console.log('connected');
    });
    client.subscribe('/topic/hello', (message) => {
        const messages = document.getElementById('messages');
        messages.innerHTML += `<p>${message.body}</p>`;
    });
}

export function disconnect() {
    if (client) {
        client.disconnect();
    }
}

export function sendMessage() {
    client.send('/app/hello', {}, JSON.stringify({ name: message }));
}

const connectButton = document.getElementById('connect');
connectButton.addEventListener('click', connect);
const disconnectButton = document.getElementById('disconnect');
disconnectButton.addEventListener('click', disconnect);