package controller.item;

import model.Item;

import java.util.List;

public interface ItemService {

    boolean addItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(Item item);

    Item searchItemById(String id);

    List<Item> getAll();

}
