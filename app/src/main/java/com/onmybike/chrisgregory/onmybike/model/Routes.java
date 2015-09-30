package com.onmybike.chrisgregory.onmybike.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.onmybike.chrisgregory.onmybike.helpers.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg Christopherson on 9/30/2015.
 */
public class Routes {


    static public List<Route> getAll(SQLiteHelper helper, SQLiteDatabase database){
        List<Route> routes = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM routes;", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Route route = cursorToRoute(cursor);
            routes.add(route);
            cursor.moveToNext();
        }
        cursor.close();
        return routes;
    }

    static private Route cursorToRoute(Cursor cursor){
        Route route = new Route();
        route.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
        route.setName(cursor.getString(cursor.getColumnIndex("name")));
        route.setNotes(cursor.getString(cursor.getColumnIndex("notes")));
        return route;
    }


}
