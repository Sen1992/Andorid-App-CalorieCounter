package edu.monash.infotech.caloriecounter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.monash.infotech.caloriecounter.FoodActivity;
import edu.monash.infotech.caloriecounter.R;

/**
 * Created by sen on 2016/4/15.
 */
public class FoodUnitFragment extends Fragment{
    View vFood;
    protected LinearLayout drinkTitile;
    protected LinearLayout mealTitle;
    protected LinearLayout meatTitle;
    protected LinearLayout snackTitle;
    protected LinearLayout breadTitle;
    protected LinearLayout cakeTitle;
    protected LinearLayout fruitTitle;
    protected LinearLayout veggiesTitle;
    protected LinearLayout otherTitle;
    protected String    username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vFood = inflater.inflate(R.layout.fragment_food_uint,container,false);

        Bundle bundle = getArguments();
        username = bundle.getString("username");
        drinkTitile = (LinearLayout)vFood.findViewById(R.id.drinkTitle);
        drinkTitile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Drink");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        mealTitle = (LinearLayout)vFood.findViewById(R.id.mealTitle);
        mealTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title", "Meal");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        meatTitle = (LinearLayout)vFood.findViewById(R.id.meatTitle);
        meatTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title", "Meat");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        snackTitle = (LinearLayout)vFood.findViewById(R.id.snackTitle);
        snackTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Snack");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        breadTitle = (LinearLayout)vFood.findViewById(R.id.breadTitle);
        breadTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Bread");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        cakeTitle = (LinearLayout)vFood.findViewById(R.id.cakeTitle);
        cakeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Cake");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        fruitTitle = (LinearLayout)vFood.findViewById(R.id.fruitTitle);
        fruitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Fruit");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        veggiesTitle = (LinearLayout)vFood.findViewById(R.id.veggiesTitle);
        veggiesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Veggies");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        otherTitle = (LinearLayout)vFood.findViewById(R.id.otherTitle);
        otherTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("title","Other");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        return vFood;
    }
}
