package controller.item;

import db.DbConnection;
import javafx.collections.FXCollections;
import model.Item;
import model.TM.ItemTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?)");

            psTm.setString(1, item.getCode());
            psTm.setString(2, item.getDescription());
            psTm.setString(3, item.getPackSize());
            psTm.setDouble(4, item.getPrice());
            psTm.setInt(5, item.getQuantity());

            return (psTm.executeUpdate() > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM item WHERE ItemCode = ?");

            psTm.setString(1, id);

            return (psTm.executeUpdate() > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItemById(String id) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM item WHERE ItemCode = ?");

            psTm.setString(1, id);
            ResultSet resultSet = psTm.executeQuery();

            resultSet.next();


            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Item> getAll() {

        ArrayList<Item> ItemArrayList = new ArrayList<>();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            while (resultSet.next()) {
                ItemArrayList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)
                        )
                );
            }

            return ItemArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
