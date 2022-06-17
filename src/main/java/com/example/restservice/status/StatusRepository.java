package com.example.restservice.status;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends CassandraRepository<Status, Integer> {
    @AllowFiltering
    Optional<Status> findByUserNumberAndPostTime(int userNumber, LocalDateTime postTime);
    @AllowFiltering
    List<Status> findAllByUserNumber(int userNumber);
    @AllowFiltering
    Optional<Status> findByDisappearTime(String disappearTime);

}
