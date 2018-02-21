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


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private TextView alsoKnownTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        descriptionTv = findViewById(R.id.description);
        originTv = findViewById(R.id.origin);
        ingredientsTv = findViewById(R.id.ingredients);
        alsoKnownTv = findViewById(R.id.also_known);

        descriptionTv.setText(sandwich.getDescription());
        originTv.setText(sandwich.getPlaceOfOrigin());

        String ingredientsElements = "";
        for (String ingredient : sandwich.getIngredients()) {
            ingredientsElements = ingredientsElements.concat(ingredient.concat("\n"));
        }
        if (ingredientsElements != "") {
            ingredientsTv.setText(ingredientsElements.substring(0, ingredientsElements.lastIndexOf('\n')));
        }
        else {
            ingredientsTv.setText("Not available");
        }

        String alsoKnownAsName = "";
        for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
            alsoKnownAsName = alsoKnownAsName.concat(alsoKnownAs.concat("\n"));
        }
        if (alsoKnownAsName != "") {
            alsoKnownTv.setText(alsoKnownAsName.substring(0, alsoKnownAsName.lastIndexOf('\n')));
        }
        else {
            alsoKnownTv.setText("Not available");
        }
    }
}
