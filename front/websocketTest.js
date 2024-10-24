// import {Client } from '@stomp/stompjs';
// import { WebSocket } from 'ws';
// Object.assign(global, { WebSocket });


// const client = new Client({
//     brokerURL: 'ws://localhost:8081/api/ws',
//     connectHeaders: {},
//     debug: function (str) {
//         console.log(str);
//     },
//     reconnectDelay: 5000,
//     heartbeatIncoming: 4000,
//     heartbeatOutgoing: 4000,
//     onConnect: () => {
//         console.log('connected');
//     },
//     onStompError: (frame) => {
//         console.error('Broker reported error: ' + frame.headers['message']);
//         console.error('Additional details: ' + frame.body);
//     },
//     onWebSocketClose: () => {
//         console.log("WebSocket connection closed");
//     },
// });

// client.activate();

import { StompClient } from './stompClient.js';

const client = new StompClient('http://localhost:8082/dashboardapi/public/websocket', () => {
    console.log('connected');
}, () => {
    console.log('disconnected');
});

client.connect();
