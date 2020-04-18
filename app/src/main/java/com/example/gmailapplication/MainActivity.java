package com.example.gmailapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<ItemModel> items;

    EditText searchText;
    Button favoriteButton;
    ItemAdapter adapter;

    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.search_text);
        favoriteButton = findViewById(R.id.favorite_button);

        items = new ArrayList<>();
        Random randomTime = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++)
            items.add(new ItemModel(faker.name.name(), faker.lorem.sentence(),
                    faker.lorem.paragraph(), "12:00 AM"));

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);

        favoriteButton.setOnClickListener(this);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        clickCount = clickCount + 1;
        MainActivity.this.adapter.getItemsFavorite(clickCount).filter(null);
    }
}

