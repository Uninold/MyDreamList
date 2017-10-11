package arnold.agura.com.mydreamlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.provider.Contacts.SettingsColumns.KEY;
import static arnold.agura.com.mydreamlist.R.drawable.dream;

/**
 * Created by Arnold on 8 Oct 2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dreamList";

    // Contacts table name
    private static final String TABLE_DREAMS = "dreams";

    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_THUMBNAIL = "thumbnail";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DREAMS_TABLE = "CREATE TABLE " + TABLE_DREAMS + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_DESCRIPTION + " TEXT," + KEY_PRICE + " FLOAT,"
                + KEY_THUMBNAIL + " BLOB " + ")";
        db.execSQL(CREATE_DREAMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DREAMS);


        onCreate(db);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public void addDream(Dream dream) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, dream.getName());
        values.put(KEY_DESCRIPTION, dream.getDescription());
        values.put(KEY_PRICE, dream.getPrice());
        values.put(KEY_THUMBNAIL, dream.getThumbnail());

        // Inserting Row
        db.insert(TABLE_DREAMS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Dream getDream(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DREAMS, new String[] {
                        KEY_NAME, KEY_DESCRIPTION,KEY_PRICE, KEY_THUMBNAIL }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Dream dream = new Dream(cursor.getString(0),cursor.getString(1),cursor.getFloat(2), (cursor.getBlob(3)));
        // return contact
        return dream;
    }

    // Getting All Contacts
    public List<Dream> getAllDream() {
        List<Dream> dreamList = new ArrayList<Dream>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DREAMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dream dream = new Dream();
                dream.setName(cursor.getString(0));
                dream.setDescription(cursor.getString(1));
                dream.setPrice(cursor.getFloat(2));
                dream.setThumbnail(cursor.getBlob(3));
                // Adding contact to list
                dreamList.add(dream);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dreamList;
    }

    // Updating single contact
    public int updateDream(Dream dream) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, dream.getDescription());
        values.put(KEY_PRICE, dream.getPrice());


        // updating row
        return db.update(TABLE_DREAMS, values, KEY_THUMBNAIL+ " = ?",
                new String[] { String.valueOf(dream.getName())});
    }

    // Deleting single contact

    public void deleteDream(String dream) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DREAMS, KEY_NAME + " = ?",
                new String[] { String.valueOf(dream) });
        db.close();
    }
    public void deleteAllDream() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete * from "+ DATABASE_NAME);
        db.close();
    }


}
