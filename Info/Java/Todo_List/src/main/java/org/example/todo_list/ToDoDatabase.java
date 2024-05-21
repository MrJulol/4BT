package org.example.todo_list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ToDoDatabase {
    private static ToDoDatabase instance;
    private final HashSet<Item> items;
    private final ObservableList<HBox> itemsObservableList;
    public static ToDoDatabase getInstance() {
        if (instance == null) {
            instance = new ToDoDatabase();
        }
        return instance;
    }
    private ToDoDatabase() {
        items = new HashSet<>();
        itemsObservableList = FXCollections.observableArrayList();
    }
    public void addItem(String content) {
        Item item = new Item(content);
        items.add(item);
        updateObservableList();

    }
    public ObservableList<HBox> getItems() {
        return itemsObservableList;
    }

    public void removeItem(Item item) {
        items.remove(item);
        itemsObservableList.remove(item.getBox());
        updateObservableList();
    }

    public void updateObservableList() {
        itemsObservableList.clear();
        items.stream()
                .sorted((a, b)-> Boolean.compare(a.isCompleted(), b.isCompleted()))
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .forEach((i)-> itemsObservableList.add(i.getBox()));
    }
}
