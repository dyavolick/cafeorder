package ru.athex.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkboxMilk;
    private CheckBox    checkboxSugar;
    private CheckBox          checkboxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;


    private String name;
    private String password;
    private String drink;
    private StringBuilder builderAdditions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if(intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }else{
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }

        drink = getString(R.string.tea);

        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);

        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);

        checkboxMilk = findViewById(R.id.checkboxMilk);
        checkboxSugar = findViewById(R.id.checkboxSugar);
        checkboxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        builderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id==R.id.radioButtonTea){
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkboxLemon.setVisibility(View.VISIBLE);
        }
        else if(id==R.id.radioButtonCoffee){
            drink = getString(R.string.coffee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkboxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {
        builderAdditions.setLength(0);
        if(checkboxMilk.isChecked()){
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if(checkboxSugar.isChecked()){
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        if(checkboxLemon.isChecked() && drink.equals(getString(R.string.tea))){
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }
        String optionOfDrink = "";
        if(drink.equals(getString(R.string.tea))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        }else{
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }

        String order = String.format(getString(R.string.order), name, password, drink,optionOfDrink );
        String additions;
        if(builderAdditions.length()>0){
            additions = getString(R.string.need_additions)+ builderAdditions.toString();
        }else{
            additions="";
        }
        String fullOrder = order+ additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
startActivity(intent);
    }
}
