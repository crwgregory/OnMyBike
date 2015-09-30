package com.onmybike.chrisgregory.onmybike.activites;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.onmybike.chrisgregory.onmybike.OnMyBike;
import com.onmybike.chrisgregory.onmybike.helpers.SQLiteHelper;
import com.onmybike.chrisgregory.onmybike.model.Route;
import com.onmybike.chrisgregory.onmybike.model.Routes;
import com.onmybike.chrisgregory.onmybike_chapter4.R;

import java.util.List;

public class RoutesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SQLiteHelper helper = ((OnMyBike) getApplication()).getHelper();
        SQLiteDatabase database = helper.getDatabase();

        List<Route> routes = Routes.getAll(helper, database);
        helper.close();

        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_1, routes);
        setListAdapter(adapter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_routes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
