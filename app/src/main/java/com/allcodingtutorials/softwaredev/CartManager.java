package com.allcodingtutorials.softwaredev;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartManager {
    private static final String CART_PREFERENCES = "CartPreferences";
    private static final String CART_ITEMS_KEY = "cartItems";
    private static final String DATABASE_PATH = "cartItems";

    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

    public static void addToCart(Context context, CartItem cartItem) {
        ArrayList<CartItem> cartItems = getCartItems(context);

        // Ensure that cartItems is not null
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Add to the local cart
        cartItems.add(cartItem);
        saveCartItems(context, cartItems);

        // Add to the remote database
        addToRemoteDatabase(cartItem);
    }

    private static void addToRemoteDatabase(CartItem cartItem) {
        // Generate a unique key for the new cart item in the database
        String cartItemKey = databaseReference.push().getKey();

        // Set the value of the new cart item using the key
        databaseReference.child(cartItemKey).setValue(cartItem);
    }

    public static ArrayList<CartItem> getCartItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        String cartItemsJson = sharedPreferences.getString(CART_ITEMS_KEY, "");
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return new Gson().fromJson(cartItemsJson, type);
    }

    public static void saveCartItems(Context context, ArrayList<CartItem> cartItems) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String cartItemsJson = new Gson().toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }
}
