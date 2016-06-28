/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.dictionaryproviderexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary.Words;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * This is the central activity for the Provider Dictionary Example App. The purpose of this app is
 * to show an example of accessing the {@link Words} list via its' Content Provider.
 */
public class MainActivity extends ActionBarActivity {

    private final String[] COLUMN_NAMES = new String[] {
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.TIMES_CONTACTED};

    private final int[] COLUMN_INTS = new int[] {
            android.R.id.text1,
            android.R.id.text2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView which will be populated with the Dictionary ContentProvider data.
        TextView dictTextView = (TextView) findViewById(R.id.dictionary_text_view);

        // Get the ContentResolver which will send a message to the ContentProvider
        ContentResolver resolver = getContentResolver();

        Log.d("test", ContactsContract.Contacts.CONTENT_URI.toString());

        // Get a Cursor containing all of the rows in the Words table
        String orderBy = ContactsContract.Contacts.TIMES_CONTACTED + " DESC";
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, orderBy);

/*        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                cursor,
                COLUMN_NAMES,
                COLUMN_INTS,
                0);*/

        String toPrint = null;

        try {

            int count = cursor.getCount();
            toPrint = "There are " + String.valueOf(count) + " contacts in your device. \n";

            while (cursor.moveToNext()) {
                int idCol = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int timesCol = cursor.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED);
                int nameCol = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                toPrint = toPrint + cursor.getString(idCol) + " - ";
                toPrint = toPrint + cursor.getString(timesCol) + " - ";
                toPrint = toPrint + cursor.getString(nameCol) + "\n";
            }

            dictTextView.setText(toPrint);
            cursor.close();

        } catch (NullPointerException e) {
            Log.e(NullPointerException.class.getName(), e.toString());
        } finally {
            cursor.close();
        }

    }
}
