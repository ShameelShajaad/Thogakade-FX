package repository.custom.impl;

import model.Customer;
import repository.custom.CustomerRepository;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean create(Customer customer) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public Customer getById(String s) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }
}
