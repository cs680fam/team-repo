package com.example.nicollettedessy.projectidea;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MealInformation extends AppCompatActivity implements AdapterView.OnItemClickListener, TextToSpeech.OnInitListener {

    //This creates a bunch of variables for items found on the layout file for this activity
    private TextView mealNameTextView;
    private EditText mealNameEditText;
    private TextView dateTextView;
    private EditText dateEditText;
    private TextView ingredientTextView;
    private EditText ingredientEditText;
    private ListView ingredientListView;
    private Button addIngredientButton;
    private Button addMealButton;
    private Button updateMealButton;

    //This creates an array list to track ingredients
    private ArrayList<String> ingredientInformation = new ArrayList<String>(){{
        for(int i = 0; i < 5; i++){
            add((i+1) + ". ");
        }
    }};

    //This creates an array adapter to be used with the ingredientInformation array list
    private ArrayAdapter<String> ingredientAdapt = null;

    //This creates an intent that will be used to get intent information from the EnterMeals class
    private Intent intentEnterMeals;

    //This will be used to track the position in the listview from the Enter Meals class
    private int positionIndex;

    //This creates a sqlite database object
    private SQLiteDatabase sqlitedb;

    //This is a global variable used to track the location in the list view for an ingredient
    private static int ingredientPosition;

    //This will help to track ingredients
    private String allMeals;

    //This will help to store the id of a meal
    private int mealID;

    //This will be used to store updates about a meal name
    private String mealUpdate;

    //This will be used to store updates about the date of a meal
    private String dateUpdate;

    //This will also be used to help with ingredient information queried from the sqlite database
    private String ingredientList = null;

    //This will be used to store ingredients pulled out from the ingredientList String
    private String[] ingredientArray;

    //This will be used to speak out about meal information
    private TextToSpeech narrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //This initializes many variables
        mealNameTextView = (TextView) findViewById(R.id.mealNameTextView);
        mealNameEditText = (EditText) findViewById(R.id.mealNameEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        ingredientTextView = (TextView) findViewById(R.id.ingredientTextView);
        ingredientEditText = (EditText) findViewById(R.id.ingredientEditText);
        ingredientListView = (ListView) findViewById(R.id.ingredientListView);
        addIngredientButton = (Button) findViewById(R.id.addIngredientButton);
        addMealButton = (Button) findViewById(R.id.addMealButton);
        updateMealButton = (Button) findViewById(R.id.updateMealButton);
        ingredientEditText.setHint("Add ingredient");
        ingredientAdapt = new ArrayAdapter<String>(this, R.layout.list_item, ingredientInformation);
        ingredientListView.setAdapter(ingredientAdapt);
        ingredientListView.setOnItemClickListener(this);
        sqlitedb = openOrCreateDatabase("MealDB", Context.MODE_PRIVATE, null);
        ingredientEditText.setHint("Add ingredient");
        allMeals = "";

        //This initializes the test to speech object
        narrator = new TextToSpeech(this, this);

        //This retrieves the intent created in the EnterMeals class
        intentEnterMeals = getIntent();

        //This gets the position in the listview clicked from the EnterMeals class
        positionIndex = intentEnterMeals.getExtras().getInt("positionIndex");

        /**This will check if the intent passed over has a value called mealIdentifier
         If so, the add meal button will be hidden **/

        if (intentEnterMeals.hasExtra("mealIdentifier")) {
            addMealButton.setVisibility(View.GONE);
            mealID = intentEnterMeals.getExtras().getInt("mealIdentifier");
            try {
                String selectFromTable = "SELECT * FROM MealTable WHERE MealID = " +
                        mealID;
                Cursor c = sqlitedb.rawQuery(selectFromTable, null);

                //This pulls up the result with the mealID passed over
                while (c.moveToNext()) {
                    mealUpdate = c.getString(1);
                    dateUpdate = c.getString(2);
                    ingredientList = c.getString(3);

                }
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**This sets the name of the meal and the date to the values stored in the database
             for the mealID selected **/

            mealNameEditText.setText(mealUpdate);
            dateEditText.setText(dateUpdate);

            /**This checks if the ingredientList string has a "comma". If it does,
             the list is split up on commas to separate each ingredient **/

            if (ingredientList.indexOf(",") != -1) {
                ingredientArray = ingredientList.split(",");
                boolean check = true;

                for (int i = 0; i < ingredientArray.length; i++) {
                    if ((i + 1) > ingredientInformation.size()) {
                        ingredientInformation.add((i + 1) + ". " + ingredientArray[i]);
                    } else {
                        ingredientInformation.set(i, (i + 1) + ". " + ingredientArray[i]);
                    }
                }
            }
        }

        //If there is no mealIdentifer value, the update meal button is hidden
        else{
            updateMealButton.setVisibility(View.GONE);
        }
        ingredientAdapt = new ArrayAdapter<String> (this, R.layout.list_item, ingredientInformation);
        ingredientListView.setAdapter(ingredientAdapt);


        //Add a listener to the add and update meal buttons, and the add ingredient button
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) throws SecurityException {
                String mealName = mealNameEditText.getText().toString();
                String date = dateEditText.getText().toString();

                switch (view.getId()) {

                    //If the add meal button is clicked, the information is inserted into the MealTable table
                        case R.id.addMealButton:
                            for(int i = 0; i < ingredientInformation.size(); i++) {
                                if (!(ingredientInformation.get(i).equals((i + 1) + ". "))) {
                                    allMeals += ingredientInformation.get(i).substring(3) + ",";
                                }
                            }
                        String insertMeal = "INSERT INTO MEALTABLE(MealName, MealDate, MealIngredients) VALUES('" +
                                mealName + "','" + date + "','" + allMeals + "');";
                        try {
                            sqlitedb.execSQL(insertMeal);
                            Toast.makeText(getApplicationContext(), mealName + " added successfully", Toast.LENGTH_LONG).show();
                        } catch (SQLException e) {
                            String x = e.getMessage();
                            Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
                        }

                            //This will check if the version is right before implementing text to speech
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                narrator.speak("You have the ingredients: " + allMeals + " listed for " + mealName, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
                            }

                        break;

                        //If the update button is clicked, then the meal information already in the MealTable is updated with new information
                    case R.id.updateMealButton:
                        for(int i = 0; i < ingredientInformation.size(); i++) {
                            if (!(ingredientInformation.get(i).equals((i + 1) + ". "))) {
                                allMeals += ingredientInformation.get(i).substring(3) + ",";
                            }
                        }

                        String updateMeal = "UPDATE MEALTABLE SET MealName = '" + mealName + "', MealDate = '" + date  + "', MealIngredients='" + allMeals +"'" +
                                " WHERE MealID =" + mealID + ";";
                        try {
                            sqlitedb.execSQL(updateMeal);
                            Toast.makeText(getApplicationContext(), "Meal successfully updated", Toast.LENGTH_LONG).show();

                        } catch (SQLException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        //This will check if the version is right before implementing text to speech
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            narrator.speak("You have the ingredients: " + allMeals + " listed for " + mealName, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
                        }
                       break;

                   //This will add the new ingredient if the "Add" button is clicked
                    case R.id.addIngredientButton:
                        boolean check = true;
                        String newIngredient = ingredientEditText.getText().toString();

                        //This checks to make sure the value for the ingredient is not blank
                        if(newIngredient.equals("")){
                                Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_LONG).show();
                        }
                        else {
                            /**This will check to see if a value in the ingredientInformation array list
                             * is blank aside from a number. If that is the case, it will add the new ingredient
                             * to the blank line item
                             */
                            for (int i = 0; i < ingredientInformation.size() && check; i++) {
                                if (ingredientInformation.get(i).equals((i + 1) + ". ")) {
                                    ingredientInformation.set(i, (i + 1) + ". " + newIngredient);
                                    check = false;
                                }
                            }
                            //If there was no blank line, a new value is added to the array list
                            if (check) {
                                ingredientInformation.add((ingredientInformation.size() + 1) + ". " + newIngredient);
                            }
                            ingredientAdapt.notifyDataSetChanged();
                            ingredientEditText.setText("");
                        }
                        break;
                }

                //This checks if the add or update buttons were clicked, and if so, goes back
                // to the Enter Meals page
                if(view.getId() == R.id.addMealButton || view.getId() == R.id.updateMealButton){
                    Intent intent = new Intent(getApplicationContext(), EnterMeals.class);
                    startActivity(intent);

                }


            }


        };

        //This adds on click listeners for various buttons
        addIngredientButton.setOnClickListener(listener);
        addMealButton.setOnClickListener(listener);
        updateMealButton.setOnClickListener(listener);


    }


    @Override
    //This will create the options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        //This hides menu options that are only supposed to be displayed on the first
        //activity when the app opens 
        if(menu != null){
            menu.findItem(R.id.nearby).setVisible(false);
            menu.findItem(R.id.search).setVisible(false);
            menu.findItem(R.id.email).setVisible(false);
        }
        return true;
    }

    @Override
    //This will go back to the EnterMeals activity if the Meal List option is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mealList:
                Intent intent = new Intent(this, EnterMeals.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    //This method is for when an item is clicked in the ListView
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ingredientPosition = position;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogBuilder.setView(R.layout.custom);
        }

        /**This will check to make sure that the list line item is not blank with a number
        If it is not, an alert dialog will pop up allowing for an ingredient to be updated or deleted.
         Otherwise, a Toast will appear asking the user to select a valid line to update or delete **/

        if (!ingredientInformation.get(ingredientPosition).equals((ingredientPosition + 1) + ". ")) {
            AlertDialog dialog = dialogBuilder.create();
            final EditText input = new EditText(MealInformation.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setText(ingredientInformation.get(ingredientPosition).substring(3));
            dialog.setView(input);
            dialog.setTitle("Enter or change ingredient, then select what to do.");

            //This button is added to the dialog box to update ingredient information in the listview
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String result = input.getText().toString();
                            ingredientInformation.set(ingredientPosition, (ingredientPosition + 1) + ". " + result);
                            ingredientAdapt.notifyDataSetChanged();
                }
            });

            //This button is added to the dialog box to delete an ingredient from the list
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                        ingredientInformation.remove(ingredientPosition);

                        //This loop renumbers the list after a value is deleted
                        for (int i = 0; i < ingredientInformation.size(); i++) {

                            //This checks if the number is less than or greater than 9 and takes a substring accordingly
                            if (i < 9) {
                                ingredientInformation.set(i, (i + 1) + ". " + ingredientInformation.get(i).substring(3));
                            } else {
                                ingredientInformation.set(i, (i + 1) + ". " + ingredientInformation.get(i).substring(4));
                            }
                        }
                        ingredientAdapt.notifyDataSetChanged();

                }
            });

            //This adds a button to cancel what was being done
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            dialog.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Select a valid line to update or delete", Toast.LENGTH_LONG).show();
        }
    }

    //This is for the TextToSpeech object
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //This sets the language to English
            int result = narrator.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("No language", "Language is not available.");
            }
        }
    }

    //This allows everything to navigate back to the EnterMeals class if the back button is pressed 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(getApplicationContext(), EnterMeals.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
