package com.oopsproject.GroceryBasket.dao;

import java.util.List;

import com.oopsproject.GroceryBasket.model.Cart;
import com.oopsproject.GroceryBasket.model.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private SessionFactory sessionFactory;


    public void addCartItem(CartItem cartItem) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(cartItem);
        session.flush();
        session.close();
    }

    public void removeCartItem(String CartItemId) {
        Session session = sessionFactory.openSession();
        CartItem cartItem = (CartItem) session.get(CartItem.class, CartItemId);
        session.delete(cartItem);
        Cart cart = cartItem.getCart();
        List<CartItem> cartItems = cart.getCartItem();
        cartItems.remove(cartItem);
        session.flush();
        session.close();
    }

    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItem();
        for (CartItem cartItem : cartItems) {
            removeCartItem(cartItem.getCartItemId());
        }
    }

}
