//package com.example.restservice;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.corundumstudio.socketio.annotation.OnEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//@Slf4j
//public class MessageEventHandler {
//
//    @Autowired
//    private SocketIOServer socketIoServer;
//
//    public static ConcurrentMap<String, SocketIOClient> socketIOClientMap = new ConcurrentHashMap<>();
//
//    /**
//     *  Trigger when the client connects
//     *
//     * @param client
//     */
//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        String mac = client.getHandshakeData().getSingleUrlParam("mac");
//        // Storage SocketIOClient, For sending messages
//        socketIOClientMap.put(mac, client);
//        // Send back a message
//        client.sendEvent("message", "onConnect back");
//        log.info(" client :" + client.getSessionId() + " Connected ,mac=" + mac);
//    }
//
//    /**
//     *  Triggered when the client closes the connection
//     *
//     * @param client
//     */
//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        log.info(" client :" + client.getSessionId() + " disconnect ");
//    }
//
///**
// *  Client events
// *
// * @param client  　 Client information
// * @param request  Request information
// * @param data    　 Client sends data
// */
//
//@OnEvent(value = "messageevent")
//public void onEvent(SocketIOClient client, AckRequest request, Message data) {
//    log.info(" Send a message ：" + data);
//    // Send back a message
//    client.sendEvent("messageevent", " I am the message sent by the server ");
//    // Broadcast news
//    sendBroadcast();
//}
//
//    /**
//     *  Broadcast news
//     */
//    public void sendBroadcast() {
//        for (SocketIOClient client : socketIOClientMap.values()) {
//            if (client.isChannelOpen()) {
//                client.sendEvent("Broadcast", " current time ", System.currentTimeMillis());
//            }
//        }
//
//    }
//}
//
