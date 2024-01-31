package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistence.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;

    @Transactional
    @Override
    public Optional<Menu> getMenu(long dish_id) {
        return menuDao.getMenu(dish_id);
    }

    @Transactional
    @Override
    public Menu addMenu(Restaurant rest, String dish, String description, int price, String category) {
        return menuDao.addMenu(rest, dish, description, price, category);
    }

    @Override
    public List<Menu> findMenuByRestId(long rest_id) {
        return menuDao.findMenuByRestId(rest_id);
    }

    @Transactional
    @Override
    public void editMenu(long dish_id, String dish, String description,int price, String category) {
        menuDao.editMenu(dish_id, dish, description, price, category);
    }

    @Transactional
    @Override
    public void deleteMenu(long dish_id) {
        menuDao.deleteMenu(dish_id);
    }

    @Override
    public List<Long> search(String search) {
        return menuDao.search(search);
    }
}
