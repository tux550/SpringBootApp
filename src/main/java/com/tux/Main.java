package com.tux;
import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping(path = "api/v1/customers")
public class Main {
    private final  CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public record NewCustomerRequest(String name, String email, Integer age) {}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }


    public record UpdateCustomerRequest(String name, String email) {}

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer id,
            @RequestBody UpdateCustomerRequest request
    ) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Customer with id " + id + " does not exist")
        );
        if (request.name != null && !request.name.isEmpty()) {
            customer.setName(request.name);
        }
        if (request.email != null && !request.email.isEmpty()) {
            customer.setEmail(request.email);
        }
        customerRepository.save(customer);
    }

}
