package com.data.r2dbc.mysql.repository;

import com.data.r2dbc.mysql.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// As of now Not being used
@Repository
public interface EmployeeDao extends ReactiveCrudRepository<Employee, Integer> {

    @Async
    @Query("select e from Employee e")
    List<Employee> getAllEmployees();

    @Query(value = "select top 1 id from Employee order by id desc", nativeQuery = true)
    Optional<Integer> getLastRecord_id();
}
