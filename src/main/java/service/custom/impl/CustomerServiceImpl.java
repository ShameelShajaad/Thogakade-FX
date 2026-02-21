package service.custom.impl;

import db.DbConnection;
import model.Customer;
import service.custom.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {

    }

    @Override
    public Customer searchCustomerById(String id) {

    }

    @Override
    public List<Customer> getAll() {
    }
}
