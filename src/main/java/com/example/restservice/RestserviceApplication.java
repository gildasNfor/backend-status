package com.example.restservice;
//
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
//import com.example.restservice.files.FilesStorageService;
//import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class RestserviceApplication {

//	@Resource
//	FilesStorageService storageService;

//	@Bean
//	public SocketIOServer socketIOServer() {
//		Configuration config = new Configuration();
//		config.setHostname("localhost");
//		config.setPort(9092);
//		config.setAllowCustomRequests(true);
//		return new SocketIOServer(config);
//	}

//	@Bean
//	public SpringAnnotationScanner springAnnotationScanner() {
//		return new SpringAnnotationScanner(socketIOServer());
//	}

//	@Override
//	public void run(String... arg) throws Exception {
//		storageService.deleteAll();
//		storageService.init();
//	}

	public static void main(String[] args) {
		SpringApplication.run(RestserviceApplication.class, args);
	}

//	@Autowired
//	private SocketIOServer socketIOServer;
//
//	@Override
//	public void run(String... strings) {
//		socketIOServer.start();
//		log.info("socket.io Successful launch ！");
//	}


}
