package controller.servlet;

import business.BOFactory;
import business.custom.CustomerBO;
import business.custom.Impl.CustomerBOImpl;
import dto.CustomerDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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


@WebServlet(urlPatterns ="/customer")
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonOb = reader.readObject();

        CustomerDTO customerDTO=new CustomerDTO(
                jsonOb.getString("customerID"),
                jsonOb.getString("customerName"),
                jsonOb.getString("customerAddress"),
                jsonOb.getString("customerContactNo")
        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (customerBO.addCustomer(customerDTO,connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Customer Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }



        /*try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstm = connection.prepareStatement("Insert into Customer values(?,?,?,?,?,?)");
            pstm.setObject(1, req.getParameter("customerID"));
            pstm.setObject(2, req.getParameter("customerName"));
            pstm.setObject(3,  req.getParameter("customerAddress"));
            pstm.setObject(4, req.getParameter("city"));
            pstm.setObject(5, req.getParameter("province"));
            pstm.setObject(6, req.getParameter("postalCode"));

            if (pstm.executeUpdate() > 0) {

                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
*/

        }

}
