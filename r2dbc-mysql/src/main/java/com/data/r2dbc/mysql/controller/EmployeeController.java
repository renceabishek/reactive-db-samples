package com.data.r2dbc.mysql.controller;

import com.data.r2dbc.mysql.model.Employee;
import com.data.r2dbc.mysql.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class EmployeeController {

   private final EmployeeDao employeeDao;

    @Autowired
    DatabaseClient databaseClient;

    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/dc/employee")
    Flux<Employee> getAsyncEmployees() throws ExecutionException, InterruptedException {
        // Getting around 50K records
        return databaseClient.select().from("EMPLOYEES")
                .as(Employee.class).fetch().all();
    }

    @GetMapping("/repo/employee")
    Flux<Employee> getSyncEmployees()  {
        return employeeDao.getAllEmployees();
    }

    @GetMapping("/post/employee/{number}")
    boolean postEmployees(@PathVariable int number) {

        int first  = databaseClient.execute("select id from Employees order by id desc limit 1")
                .as(Integer.class).fetch().one().block();

        IntStream.range(first,first+number)
                .forEach(f->{
                    databaseClient.insert().into("EMPLOYEES")
                            .value("id",f+1)
                            .value("name","Abishek"+f)
                            .value("salary",1000+f).then().block();
                });

        return true;
    }
}
