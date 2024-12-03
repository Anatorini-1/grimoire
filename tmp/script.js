const WebSocket = require('ws');

// Create a WebSocket server
const wss = new WebSocket.Server({host:"0.0.0.0", port: 8080 });

console.log('WebSocket server is running on ws://localhost:8080');

// Store all connected clients
const clients = new Set();

// Handle new connections
wss.on('connection', (ws) => {
    console.log('New client connected');
    clients.add(ws);

    // Handle incoming messages
    ws.on('message', (message) => {
        console.log(`Received: ${message}`);

        // Broadcast the message to all connected clients
        for (const client of clients) {
            if (client !== ws && client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        }
    });

    // Handle client disconnection
    ws.on('close', () => {
        console.log('Client disconnected');
        clients.delete(ws);
    });
});
