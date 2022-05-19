package controller.servlet;

import business.BOFactory;
import business.custom.CustomerBO;
import business.custom.Impl.CustomerBOImpl;
import dto.CustomerDTO;
import javafx.collections.ObservableList;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");


        CustomerDTO customerDTO = new CustomerDTO(
                req.getParameter("customerID"),
                req.getParameter("customerName"),
                req.getParameter("customerAddress"),
                req.getParameter("customerContactNo")
        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (customerBO.addCustomer(customerDTO, connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Customer Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());

            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        }


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String cutomerId = req.getParameter("cusId");
        String option = req.getParameter("option");


        try {
            Connection connection = dataSource.getConnection();

            switch (option) {

                case "SEARCH":
                    CustomerDTO customer = customerBO.searchCustomer(cutomerId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getId());
                    objectBuilder.add("name", customer.getName());
                    objectBuilder.add("address", customer.getAddress());
                    objectBuilder.add("contact_No", customer.getContactNo());


                    writer.print(objectBuilder.build());
                    break;

                case "GETALL":

                    ObservableList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (CustomerDTO dto : allCustomers) {
                        JsonObjectBuilder ob = Json.createObjectBuilder();

                        ob.add("id", dto.getId());
                        ob.add("name", dto.getName());
                        ob.add("address", dto.getAddress());
                        ob.add("contact_No", dto.getContactNo());

                        arrayBuilder.add(ob.build());
                    }

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    break;

                case "ID":
                    String id = customerBO.generateID(connection);

                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
                    objectBuilder1.add("id",id);
                    writer.print(objectBuilder1.build());
                    break;
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }

       /* PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");



        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            ObservableList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            for (CustomerDTO dto : allCustomers) {
                JsonObjectBuilder ob = Json.createObjectBuilder();

                ob.add("id", dto.getId());
                ob.add("name", dto.getName());
                ob.add("address", dto.getAddress());
                ob.add("contactNo", dto.getContactNo());

                arrayBuilder.add(ob.build());

            }


            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message", "Table Loaded");
            response.add("data", arrayBuilder.build());

            writer.print(response.build());

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();

        }*/
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();


        CustomerDTO customerDTO = new CustomerDTO(
                req.getParameter("id"),
                req.getParameter("name"),
                req.getParameter("address"),
                req.getParameter("contact")
        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (customerBO.updateCustomer(customerDTO, connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Customer Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());

            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/jason");
        PrintWriter writer = resp.getWriter();

        String cusId = req.getParameter("cusId");

        try {

            Connection connection = dataSource.getConnection();

            if (customerBO.deleteCustomer(cusId,connection)) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("message","Customer Successfully Deleted.");
                objectBuilder.add("status",resp.getStatus());
                writer.print(objectBuilder.build());

            }else {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message","Wrong Id Inserted.");
                objectBuilder.add("status",400);
                writer.print(objectBuilder.build());

            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {

            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Error");
            objectBuilder.add("status",resp.getStatus());
            writer.print(objectBuilder.build());

            e.printStackTrace();
        }
    }
}
