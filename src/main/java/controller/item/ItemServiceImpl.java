package controller.item;

import db.DbConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService{
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

            return  (psTm.executeUpdate() > 0);

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
        return null;
    }

    @Override
    public List<Item> getAll() {
        return List.of();
    }
}
