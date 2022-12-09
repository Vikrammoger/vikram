package com.robosoft.foodApp.service;

import com.robosoft.foodApp.entity.*;

import com.robosoft.foodApp.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodService {

    void update2FAProperties(String mobileNumber, String tfacode);

    boolean checkCode(String mobileNumber, String code);

    String userRegister(User user);

    void forgotPassword(String mobileNumber, String tfacode);

    boolean updatePassword(String mobileNumber, String code, String password);

    String addRestaurants(Restaurant restaurant);


    List<Restaurant> viewRestaurants();

    List<Restaurant> viewRestaurantsByName(String name);

    String addMenu(Menu menu);

    byte[] dishImage(String dishName);


    String getImageUrl(String dishName);

    List<Menu> viewMenu(int pageNumber, long limit);

    List<Menu> viewMenuByRestaurantId(int id);

    String getRestaurantImageUrl(int rId);

    byte[] restaurantImage(int rId);

    String addRatings(Ratings ratings);

    String addToFavourites(Favourites favourites);

    List<Restaurant> viewMyFavourites();


    FilterResponse searchWithFilter(Filter filter);

    List<Restaurant> HomePage(int pageNumber, long limit);

    String addAddresses(Address address);

    List<Address> viewAddress();

    ResponseEntity<?> updateAddresses(String address);

    String addToCart(Cart cart);

    List<Cart> viewCart();

    String addCartItems(List<Item> items, int cartId);

    Integer addToOrder(CartItems cartItems);

    String placeOrder(Order order);

    List<OrderResponse> viewOrders();

    List<CategoryResponse> viewFoodBasedOnTopCategory(int pageNumber, long limit);

    String addCard(Card card);

    List<Card> viewCard();

    List<Menu> searchDishName(String dishName);

    String deleteRestaurant(int id);

    String deleteDishByRestaurant(int id, int dishId);

    String updateDishByRestaurant(Menu menu);

    String updateRestaurantMinCost(int restaurantId, double minCost);
}
