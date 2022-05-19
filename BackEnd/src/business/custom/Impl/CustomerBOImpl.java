package business.custom.Impl;

import business.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer =new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getContactNo());
        return customerDAO.add(customer,connection);

    }

}
