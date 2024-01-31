package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    Menu addMenu(Restaurant rest, String dish, String description, int price, String category);
    Optional<Menu> getMenu(long dish_id);
    List<Menu> findMenuByRestId(long rest_id);
    void editMenu(long dish_id, String dish, String description,int price, String category);
    void deleteMenu(long dish_id);
    List<Long> search(String search);
}
