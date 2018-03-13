package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // find all tv IDS
    TextView alsoKnownAs;
    TextView origin;
    TextView description;
    TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownAs = findViewById(R.id.also_known_tv);
        origin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        // set the name of the sandwich
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        // set place of origin
        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin.setText(R.string.no_detail_to_display);
        }


        // set also known as
        if(sandwich.getAlsoKnownAs().get(0).contains("N/A")) {
            alsoKnownAs.setText(R.string.no_detail_to_display);
        } else {
            String result = "";
            for(String s : sandwich.getAlsoKnownAs()) {
                result += s + "\n";
            }

            alsoKnownAs.setText(result);
        }

        // set description
        if(!sandwich.getDescription().isEmpty()) {
            description.setText(sandwich.getDescription());
        } else {
            description.setText(R.string.no_detail_to_display);
        }

        //set ingredients
        if(sandwich.getIngredients().isEmpty()) {
            ingredients.setText(R.string.no_detail_to_display);
        } else {
            String ingredientsString = "";
            for(String s : sandwich.getIngredients()) {

                ingredientsString += s + "\n"; // add the ingredient and start new line
            }

            ingredients.setText(ingredientsString);
        }

    }
}
