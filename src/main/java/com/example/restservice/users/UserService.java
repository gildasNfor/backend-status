package com.example.restservice.users;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.restservice.exceptions.BadRequestException;
import com.example.restservice.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
//import reseau.project.status.exceptions.BadRequestException;
//import reseau.project.status.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_TYPE;

@Service
@Transactional
@Slf4j
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(int usersNumber, MultipartFile file, String userBio, String userName) throws Exception {
        if (usersNumber < 600000000 || usersNumber > 700000000)
            throw new BadRequestException("Enter a valid phone number");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        if(!(fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg"))) throw new BadRequestException("You need to provide an image for the users photo");

        if (fileName.contains("..")) {
            throw new BadRequestException("Filename contains invalid path sequence "
                    + fileName);
        }

        if (userRepository.findById(usersNumber).isPresent()) throw new BadRequestException("A user with that number already exist");


        User user = new User(usersNumber, file.getBytes(), userBio, userName);
        return userRepository.save(user);
    }

    @Override
    public User getOneUser(int userNumber) {
        log.info("IN GET ONE USER CONTROLLER NUMBER PASSED; " + userNumber);
         Optional<User> optionalUser = userRepository.findById(userNumber);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException("A user with that number does not exist");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User deleteOneUser(int userNumber) {
        Optional<User> optionalUser = userRepository.findById(userNumber);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userNumber);
            return optionalUser.get();
        }
        throw new NotFoundException("A user with that number does not exist");
    }


}
