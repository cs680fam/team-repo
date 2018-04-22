package com.example.nicollettedessy.projectidea;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterMeals extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //This creates an array list that will store meals, each row is intially blank
    private ArrayList<String> mealInformation = new ArrayList<String>(){{
        for(int i = 0; i < 30; i++){
            add("");
        }
    }};

    //This creates an array adapter that will be used with the array list to list out meals
    private ArrayAdapter<String> adapt = null;

    //This creates a listview to store the meal information
    private ListView listView;

    //This variable will be used to track what row in the listview is clicked
    private static int positionInformation = 0;

    //This will store the name of a meal
    private String mealName;

    //This will store the date of a meal
    private String dateOfMeal;

    //This creates an instance of a sqlite database
    private SQLiteDatabase sqlitedb;

    //This will be used to track the location in the mealInformation arrayList
    private int i;

    //This will be used to store the id of a meal
    private int mealID;

    //This will be used to get the value of text in a row in the listview
    private String listItemString = "";

    //This will be used to set the options buttons when a listview item is clicked
    private String optionButton = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_meals);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        //This will initialize the array adapter and add it to the listview
        adapt = new ArrayAdapter<String> (this, R.layout.list_item, mealInformation);
        listView.setAdapter(adapt);

        i = 0;

        //Open or create the SQLite db
        sqlitedb = openOrCreateDatabase("MealDB", Context.MODE_PRIVATE,null);

        //This will be used to create the MealTable table to store meal information
        String createTable = "CREATE TABLE IF NOT EXISTS MEALTABLE(MealID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MealName varchar(200), MealDate varchar(100), MealIngredients varchar(1000))";
        try {
            sqlitedb.execSQL(createTable);
        } catch (SQLException e) {
            String x = e.getMessage();
            Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
        }

        String displayMeals = "SELECT * FROM MEALTABLE ORDER BY strftime('%Y-%m-%d', MealDate) ASC";

        //This creates a cursor with the displayMeals query
        Cursor c = sqlitedb.rawQuery(displayMeals, null);

        //This will loop through the meal table to get each meal
        while(c.moveToNext()){
            String mealResult = c.getString(1);
            String dateResult = c.getString(2);
                mealInformation.set(i, mealResult + " - " + dateResult);
                i++;
        }

    }

    @Override
    //This will create the options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu != null){
            menu.findItem(R.id.mealList).setVisible(false);
        }

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), FoodItemListActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    //This will respond to an item being clicked
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            positionInformation = position;
            listItemString = mealInformation.get(positionInformation);

            //This sets the value of the listItemString so when a listview item is clicked, depending
            //on whether the row is empty or not, there will be the option to update the meal or add a meal
            if(listItemString.equals("")){
                optionButton = "Add Meal";
            }
            else{
                optionButton = "Update";
            }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogBuilder.setView(R.layout.custom);
        }

        //This creates an alert dialog which will give options to add, update, or delete a meal
        AlertDialog dialog = dialogBuilder.create();

        //This will check if a particular row in the list has "-". If it does, it will split up the
        //string into two parts and set the title of the dialog box to the name of the meal
        if (listItemString.indexOf("-") != -1) {
            listItemString = "Meal: " + listItemString.substring(0, listItemString.indexOf("-"));
            dialog.setTitle(listItemString);
        }

        //Otherwise the title of the dialog box is "Meal Options"
        else{
            dialog.setTitle("Meal Options");
        }

        /**Depending on whether we are adding or updating a meal, this button the alert dialog will say "Add Meal"
        or "Update". If we are adding a meal, the MealInformation class opens, or if we are updating a meal, the
        MealInformation class opens after querying the database for the meal ID of the associated meal in the listview **/
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, optionButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Intent intent = new Intent(getApplicationContext(), MealInformation.class);
                intent.putExtra("positionIndex", positionInformation);

                String allResults = mealInformation.get(positionInformation);

                if (allResults.indexOf("-") != -1) {
                    String[] allResultParts = allResults.split("-");

                    try {
                        String selectFromTable = "SELECT * FROM MealTable WHERE MealName = '" + allResultParts[0].trim() + "'" +
                                "AND MealDate = '" + allResultParts[1].trim() + "'";
                        Cursor c = sqlitedb.rawQuery(selectFromTable, null);
                        while (c.moveToNext()) {
                            mealID = c.getInt(0);

                        }
                    } catch (SQLException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    intent.putExtra("mealIdentifier", mealID);
                }
                startActivity(intent);


            }
        });

        /**This will only display the delete button if the list item is not blank. If it is not blank,
        then there is the option to delete a meal **/
        if(!mealInformation.get(positionInformation).equals("")) {
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String mealDetails = mealInformation.get(positionInformation);
                    String[] separateMeal = mealDetails.split("-");

                    //Get rid of extra space before putting the values into the sql query
                    mealName = separateMeal[0].trim();
                    dateOfMeal = separateMeal[1].trim();
                    String deleteMeal = "DELETE FROM MealTable WHERE MealName ='" + mealName + "' AND MealDate = '"
                            + dateOfMeal + "';";
                    try {
                        sqlitedb.execSQL(deleteMeal);
                        Toast.makeText(getApplicationContext(), mealName + " deleted successfully", Toast.LENGTH_LONG).show();
                    } catch (SQLException e) {
                        String x = e.getMessage();
                        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
                    }
                    mealInformation.remove(positionInformation);
                    for(int i = 0; i <mealInformation.size(); i++){
                        mealInformation.set(i, mealInformation.get(i));
                    }
                    adapt.notifyDataSetChanged();
                }
            });
        }
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        dialog.show();

    }

    @Override
    //This will open up the NearbyActivity activity if the "Nearby" option is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nearby:
                Intent intent = new Intent(this, NearbyActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    //This will close the database when another activity is started
    @Override
    protected void onPause() {
        super.onPause();
        if (sqlitedb != null)
            sqlitedb.close();
    }

}
