package com.aoinc.w2d2_c_sqlite_pretty.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aoinc.w2d2_c_sqlite_pretty.R;
import com.aoinc.w2d2_c_sqlite_pretty.model.Pizza;
import com.aoinc.w2d2_c_sqlite_pretty.view.adapter.PizzaItemAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements PizzaItemAdapter.PizzaDelegate {

    private Pizza extravaganza = new Pizza("Extravaganza", 24.99, 1000,
            "Mushrooms, Pepperoni, Goat Cheese, Truffles, Spanish Cheese, Gold Flakes",
            "https://tinyurl.com/y6klsb86");

    private Pizza chickenCheese = new Pizza("Chicken Cheese", 19.99, 780,
            "Mushrooms, Chicken, Goat Cheese, Black Sesame, Spanish Cheese, Edible Diamond Dust",
            "https://tinyurl.com/yxlpkrep");

    private Pizza seafood = new Pizza("Seafood Pizza", 24.99, 560,
            "Calamari, Tuna, Salmon Roe, Brie, Bonito Flakes",
            "https://tinyurl.com/y4voyq4a");

//    private List<String> availablePizzas = new ArrayList<String>() {{
//        add(extravaganza.getPizzaFlavor());
//        add(chickenCheese.getPizzaFlavor());
//        add(seafood.getPizzaFlavor());
//    }};
    private List<Pizza> availablePizzas = new ArrayList<Pizza>() {{
        add(extravaganza);
        add(chickenCheese);
        add(seafood);
    }};

    private ListView pizzasListView;
//    private ArrayAdapter<String> pizzaArrayAdapter;
    private PizzaItemAdapter pizzaArrayAdapter = new PizzaItemAdapter(this, availablePizzas);

    // 'selected' display
    private ImageView selectedPizzaImage;
    private TextView selectedPizzaTitle;
    private TextView selectedPizzaPrice;

    private Button addToCart_button;
    private Pizza savedSelectedPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pizzasListView = findViewById(R.id.pizzas_listView);
//        pizzaArrayAdapter = new ArrayAdapter<String>(this,R.layout.pizza_flavor_layout_item,
//                R.id.pizza_flavor_textView, availablePizzas);
//
//        pizzasListView.setAdapter(pizzaArrayAdapter);
//        pizzasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
//                // Every tme the user clicks an item -> displays a toast of the item selected
////                String name = availablePizzas.get(index);
//                String name = availablePizzas.get(index).getPizzaFlavor();
//                Toast.makeText(HomeActivity.this, name, Toast.LENGTH_SHORT).show();
//            }
//        });

        pizzasListView.setAdapter(pizzaArrayAdapter);

        selectedPizzaImage = findViewById(R.id.pizza_imageView);
        selectedPizzaTitle = findViewById(R.id.selectedFlavor_textView);
        selectedPizzaPrice = findViewById(R.id.selectedPrice_textView);

        addToCart_button = findViewById(R.id.add_to_cart_button);
        addToCart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedSelectedPizza == null) {
                    Toast.makeText(HomeActivity.this, "Select a pizza first!!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(HomeActivity.this, PizzaDescriptionActivity.class);

                    intent.putExtra("flavor", savedSelectedPizza.getPizzaFlavor());
                    intent.putExtra("ingredients", savedSelectedPizza.getIngredients());
                    intent.putExtra("calories", savedSelectedPizza.getCalories());
                    intent.putExtra("price", savedSelectedPizza.getPizzaPrice());
                    intent.putExtra("imageURL", savedSelectedPizza.getImageURL());

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void SelectPizza(Pizza selectedPizza) {
        Toast.makeText(HomeActivity.this, selectedPizza.getPizzaFlavor(), Toast.LENGTH_SHORT).show();
        Glide.with(this).load(selectedPizza.getImageURL()).into(selectedPizzaImage);
        selectedPizzaTitle.setText(selectedPizza.getPizzaFlavor());
        selectedPizzaPrice.setText(String.format(getResources().getString(R.string.price_text), selectedPizza.getPizzaPrice()));

        savedSelectedPizza = selectedPizza;
    }
}