//package com.example.restservice;
//
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class NettySocketioConfig {
//    /**
//     * netty-socketio The server
//     */
//    @Bean
//    public SocketIOServer socketIOServer() {
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setHostname("localhost");
//        config.setPort(9092);
//
//        SocketIOServer server = new SocketIOServer(config);
//        return server;
//    }
//
//    /**
//     *  For scanning netty-socketio Annotations , such as  @OnConnect、@OnEvent
//     */
//    @Bean
//    public SpringAnnotationScanner springAnnotationScanner() {
//        return new SpringAnnotationScanner(socketIOServer());
//    }
//}
