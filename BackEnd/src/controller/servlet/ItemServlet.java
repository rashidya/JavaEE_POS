package controller.servlet;

import business.BOFactory;
import business.custom.CustomerBO;
import business.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");


        ItemDTO itemDTO = new ItemDTO(
                req.getParameter("itemId"),
                req.getParameter("itemName"),
                req.getParameter("price"),
                req.getParameter("qty")

        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (itemBO.saveItem(itemDTO, connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Item Successfully Added.");
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

        String itemId = req.getParameter("itemId");
        String option = req.getParameter("option");


        try {
            Connection connection = dataSource.getConnection();

            switch (option) {

                case "SEARCH":
                    ItemDTO item = itemBO.searchItem(itemId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("itemId", item.getId());
                    objectBuilder.add("itemName", item.getItem());
                    objectBuilder.add("price", item.getUnitPrice());
                    objectBuilder.add("qty", item.getQty());


                    writer.print(objectBuilder.build());
                    break;

                case "GETALL":

                    ObservableList<ItemDTO> allItems = itemBO.getAllItems(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (ItemDTO dto : allItems) {
                        JsonObjectBuilder ob = Json.createObjectBuilder();

                        ob.add("itemId", dto.getId());
                        ob.add("itemName", dto.getItem());
                        ob.add("price", dto.getUnitPrice());
                        ob.add("qty", dto.getQty());

                        arrayBuilder.add(ob.build());
                    }

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    break;

                case "ID":
                    String id = itemBO.generateItemID(connection);

                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
                    objectBuilder1.add("id",id);
                    writer.print(objectBuilder1.build());
                    break;
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonOb = reader.readObject();



       ItemDTO itemDTO = new ItemDTO(
                jsonOb.getString("id"),
                jsonOb.getString("itemName"),
                jsonOb.getString("price"),
                jsonOb.getString("qty")

        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (itemBO.updateItem(itemDTO, connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Item Successfully Updated.");
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

        String itemId = req.getParameter("itemId");

        try {

            Connection connection = dataSource.getConnection();

            if (itemBO.deleteItem(itemId,connection)) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("message","Item Successfully Deleted.");
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
