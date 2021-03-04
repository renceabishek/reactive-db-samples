package com.data.r2dbc.mysql.repository;

import com.data.r2dbc.mysql.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

// As of now Not being used
@Service
public interface EmployeeDao extends R2dbcRepository<Employee, Integer> {

    @Query("select e from Employees e")
    Flux<Employee> getAllEmployees();

    @Query(value = "select id from Employees order by id desc limit 1", nativeQuery = true)
    Optional<Integer> getLastRecord_id();
}
