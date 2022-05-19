package business.custom.Impl;

import business.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getContactNo());
        return customerDAO.add(customer, connection);

    }

    @Override
    public ObservableList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerDTO> allCustomers = FXCollections.observableArrayList();
        ArrayList<Customer> customers = customerDAO.getAll(connection);

        for (Customer customer : customers) {
            CustomerDTO dto = new CustomerDTO(
                    customer.getId(), customer.getName(), customer.getAddress(), customer.getContact_No()
            );

            allCustomers.add(dto);

        }

        return allCustomers;
    }

    @Override
    public CustomerDTO searchCustomer(String cutomerId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cutomerId, connection);
        CustomerDTO customerDto = new CustomerDTO(
                customer.getId(), customer.getName(), customer.getAddress(), customer.getContact_No()
        );
        return customerDto;
    }

    @Override
    public String generateID(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.generateID(connection);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getContactNo());
        return customerDAO.update(customer, connection);
    }

    @Override
    public boolean deleteCustomer(String cusId, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(cusId, connection);
    }

}
