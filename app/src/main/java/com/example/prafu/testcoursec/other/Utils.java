package com.example.prafu.testcoursec.other;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by prafu on 4/2/2017.
 */

public class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
//            mDatabase.setPersistenceEnabled(false);
        }
        return mDatabase;
    }

}