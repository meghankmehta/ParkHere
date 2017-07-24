import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.location.Address;
import java.util.List;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.io.IOException;
import android.database.sqlite.SQLiteException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by emmalautz on 10/19/16.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    private static String DB_PATH = "/data/data/com.sparkleusc.sparklesparkhere/databases/";

    public static String DB_NAME = "com.sparkleusc.sparklesparkhere.ParkHere.db";
   // private static final String DATABASE_NAME = "northwind.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
//    private SQLiteDatabase myDataBase;
//
//    private final Context myContext;
//
//    private static final String PARK_HERE_DB = "com.sparkleusc.sparklesparkhere.ParkHere";
//
//    //tables
    public static final String USER_TB = "User";
//    public static final String LISTING_TB = "Listing";
//    public static final String RESERVATION_TB = "Reservation";
//    public static final String SEEKER_FAVORITES_TB = "SeekerFavorites";
//    private static final String CANCELLATION_POLICY_TB = "CancellationPolicy";
//    private static final String AVAILABILITY_TB = "Availability";
//    private static final String SEEKER_TB = "Seeker";
//    private static final String LENDER_TB = "Lender";
//    private static final String CATEGORY_TB = "Category";
//    private static final String LISTING_IMAGES_TB = "ListingImages";
//    private static final String ADDRESS_TB = "Address";
//    private static final String CATEGORY_LISTING_TB = "CategoryListing";
//
//    //columns
//
//    //user table
   // USER_EMAIL_COL
   // private static final String EMAIL_COL = "email";
    public static final String PASS_COL = "password";
    public static final String NAME_COL = "name";
//
//    //lender
//
    public static final String USER_EMAIL_COL = "user_email";
//    private static final String LENDER_ID_COL = "lender_id";
//    private static final String PHONE_NUM_COL = "phone_number";
//    private static final String PROFILE_PIC_COL = "profile_pic";
//    private static final String IS_DEFAULT_COL = "is_default_role";
//
//    //listing
//
//    private static final String LISTING_ID_COL = "listing_id";
//    //LENDER_ID_COL
//    private static final String LISTING_TITLE_COL = "listing_title";
//    private static final String DESCRIPTION_COL = "description";
//    private static final String TOTAL_RATING_COL = "total_rating";
//    private static final String NUMBER_OF_RATINGS_COL = "number_of_ratings";
//    private static final String CANCELLATION_ID_COL = "cancellation_id";
//    private static final String ADDRESS_ID_COL = "address_id";
//    private static final String PRICE_PER_HR_COL = "price_per_hr";
//    //private static final String CATEGORIES_COL = "categories";
//
//    //seeker
//
//    //USER_EMAIL_COL
//    private static final String SEEKER_ID_COL = "seeker_id";
//    //PHONE_NUM_COL
//    //PROFILE_PIC_COL
//    //IS_DEFAULT_COL
//
//    //reservation
//
//    private static final String RESERVATION_ID_COL = "reservation_id";
//    //SEEKER_ID_COL
//    //LENDER_ID_COL
//    //LISTING_ID_COL
//    //AVAILABILITY_ID_COL
//    private static final String AMOUNT_PAID_COL = "amount_paid";
//    private static final String TRANSACTION_ID_COL = "transaction_id";
//
//    //seeker_favorites
//
//    //SEEKER_ID_COL
//    //LISTING_ID_COL
//
//    //availability
//
//    private static final String AVAILABILITY_ID_COL = "availability_id";
//    //LISTING_ID_COL
//    //private static final String DATE_COL = "date";
//    private static final String BEGIN_TIME_COL = "begin_date_time";
//    private static final String END_TIME_COL = "end_date_time";
//    private static final String IS_RESERVED_COL = "is_reserved";
//
//    //listing_images DONE
//    //LISTING_ID_COL
//    private static final String IMAGE_COL = "image";
//
//    //cancellation_policy DONE
//
//    //CANCELLATION_ID_COL
//    private static final String CANCELLATION_POLICY_COL = "cancellation_policy";
//
//    //category DONE
//    private static final String CATEGORY_ID_COL = "category_id";
//    private static final String CATEGORY_COL = "category";
//
//    //address DONE
//    //ADDRESS_ID_COL
//    private static final String ZIP_CODE_COL = "zip_code";
//    private static final String FIRST_LINE_COL = "first_line";
//    private static final String SECOND_LINE_COL = "second_line";
//    private static final String CITY_COL = "city";
//    private static final String STATE_COL = "state";
//
//    private static int version = 1;
//    //category_listing DONE
//    //CATEGORY_ID_COL
//    //LISTING_ID_COL
//
//    public DatabaseHelper(Context context){
//        super(context, PARK_HERE_DB, null, version);
//        this.myContext = context;
//    }
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//
//    }
//
//    public void createDataBase() throws IOException{
//
//        //boolean dbExist = checkDataBase();
//
////        if(dbExist){
////            //do nothing - database already exist
////        }else{
//
//            //By calling this method and empty database will be created into the default system path
//            //of your application so we are gonna be able to overwrite that database with our database.
//            this.getReadableDatabase();
//
//            try {
//
//                copyDataBase();
//
//            } catch (IOException e) {
//
//                throw new RuntimeException(e);
//
//            }
//        //}
//
//    }
//
//    /**
//     * Check if the database already exist to avoid re-copying the file each time you open the application.
//     * @return true if it exists, false if it doesn't
//     */
//    private boolean checkDataBase(){
//
//        SQLiteDatabase checkDB = null;
//
//        try{
//            String myPath = DB_PATH + DB_NAME;
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        }catch(SQLiteException e){
//
//            //database does't exist yet.
//
//        }
//
//        if(checkDB != null){
//
//            checkDB.close();
//            myContext.deleteDatabase(DB_PATH+DB_NAME);
//        }
//
//        return checkDB != null ? true : false;
//    }
//
//    /**
//     * Copies your database from your local assets-folder to the just created empty database in the
//     * system folder, from where it can be accessed and handled.
//     * This is done by transfering bytestream.
//     * */
//    private void copyDataBase() throws IOException{
//
//        //Open your local db as the input stream
//        InputStream myInput = myContext.getAssets().open(DB_NAME);
//
//        // Path to the just created empty db
//        String outFileName = DB_PATH + DB_NAME;
//
//        //Open the empty db as the output stream
//        OutputStream myOutput = new FileOutputStream(outFileName);
//
//        //transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = myInput.read(buffer))>0){
//            myOutput.write(buffer, 0, length);
//        }
//
//        //Close the streams
//        myOutput.flush();
//        myOutput.close();
//        myInput.close();
//
//    }
//
//    public void openDataBase() throws SQLException{
//
//        //Open the database
//        String myPath = DB_PATH + DB_NAME;
//        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }
//
//    @Override
//    public synchronized void close() {
//
//        if(myDataBase != null)
//            myDataBase.close();
//
//        super.close();
//
//    }
//
//    public void onCreate(SQLiteDatabase db) {
//        // TODO Auto-generated method stub
//
////        //tables with no foreign key constraints
////        db.execSQL("CREATE TABLE "+USER_TB+" ("+EMAIL_COL+ " TEXT PRIMARY KEY NOT NULL, "+PASS_COL+" TEXT NOT NULL, "+NAME_COL+" TEXT NOT NULL);");
////
////        db.execSQL("CREATE TABLE "+CATEGORY_TB+" ("+CATEGORY_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+CATEGORY_COL+" TEXT NOT NULL);");
////
////        db.execSQL("CREATE TABLE "+ADDRESS_TB+" ("+ADDRESS_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, " + ZIP_CODE_COL+" TEXT NOT NULL, "+FIRST_LINE_COL+" TEXT NOT NULL, "+
////            APT_NUM_COL+" TEXT, "+CITY_COL+" TEXT, "+STATE_COL+" TEXT NOT NULL);");
////
////        db.execSQL("CREATE TABLE "+CANCELLATION_POLICY_TB+" ("+CANCELLATION_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+CANCELLATION_POLICY_COL+" TEXT NOT NULL);");
////
////
////        //tables with foreign key constraints
////        db.execSQL("CREATE TABLE "+CATEGORY_LISTING_TB+" ("+CATEGORY_ID_COL+" INT NOT NULL PRIMARY KEY, "+LISTING_ID_COL+" INT NOT NULL PRIMARY KEY, "+
////                "FOREIGN KEY ("+CATEGORY_ID_COL+") REFERENCES "+CATEGORY_TB+"("+CATEGORY_ID_COL+"), "+
////                "FOREIGN KEY ("+LISTING_ID_COL+") REFERENCES "+LISTING_TB+"("+LISTING_ID_COL+"));");
////
////        db.execSQL("CREATE TABLE "+SEEKER_FAVORITES_TB+" ("+SEEKER_ID_COL+" INT NOT NULL PRIMARY KEY, "+LISTING_ID_COL+" INT NOT NULL PRIMARY KEY, "+
////                "FOREIGN KEY ("+SEEKER_ID_COL+") REFERENCES "+SEEKER_TB+"("+SEEKER_ID_COL+"), "+
////                "FOREIGN KEY ("+LISTING_ID_COL+") REFERENCES "+LISTING_TB+"("+LISTING_ID_COL+"));");
////
////        db.execSQL("CREATE TABLE "+LISTING_IMAGES_TB+" ("+LISTING_ID_COL+" INT NOT NULL, "+IMAGE_COL+" TEXT NOT NULL, "+
////                "FOREIGN KEY ("+LISTING_ID_COL+") REFERENCES "+LISTING_TB+"("+LISTING_ID_COL+"));");
////
////        db.execSQL("CREATE TABLE "+AVAILABILITY_TB+" ("+AVAILABILITY_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+LISTING_ID_COL+" INT NOT NULL, "+
////        DATE_COL+" TEXT NOT NULL, "+BEGIN_TIME_COL+" INT NOT NULL, "+END_TIME_COL+" INT, "+IS_RESERVED_COL+" BOOLEAN, "+
////                "FOREIGN KEY ("+LISTING_ID_COL+") REFERENCES "+LISTING_TB+"("+LISTING_ID_COL+"));");
////
////        //you would have to insert address, and then insert listing
////        db.execSQL("CREATE TABLE "+LISTING_TB+" ("+LISTING_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+LENDER_ID_COL+" INT NOT NULL, "+
////        LISTING_TITLE_COL+" TEXT NOT NULL, "+DESCRIPTION_COL+" TEXT NOT NULL, "+AVERAGE_RATING_COL+" INT, "+CANCELLATION_ID_COL+" INT NOT NULL, "+
////        ADDRESS_ID_COL+" INT NOT NULL, "+PRICE_PER_HR_COL+" INT NOT NULL,  FOREIGN KEY ("+LENDER_ID_COL+") REFERENCES "+LENDER_TB+"("+LENDER_ID_COL+")," +
////                "FOREIGN KEY ("+CANCELLATION_ID_COL+") REFERENCES "+CANCELLATION_POLICY_TB+"("+CANCELLATION_ID_COL+"), "+
////                "FOREIGN KEY ("+ADDRESS_ID_COL+") REFERENCES "+ADDRESS_TB+"("+ADDRESS_ID_COL+"));");
////
////        db.execSQL("CREATE TABLE "+RESERVATION_TB+" ("+RESERVATION_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+SEEKER_ID_COL+" INT NOT NULL, "+
////        LENDER_ID_COL+" INT NOT NULL, "+LISTING_ID_COL+" INT NOT NULL, "+AVAILABILITY_ID_COL+" INT NOT NULL, "+ AMOUNT_PAID_COL+" DOUBLE NOT NULL, "+
////                TRANSACTION_ID_COL+" INT, "+
////                "FOREIGN KEY ("+LISTING_ID_COL+") REFERENCES "+LISTING_TB+"("+LISTING_ID_COL+"), "+
////                "FOREIGN KEY ("+SEEKER_ID_COL+") REFERENCES "+SEEKER_TB+"("+SEEKER_ID_COL+"), "+
////                "FOREIGN KEY ("+LENDER_ID_COL+") REFERENCES "+LENDER_TB+"("+LENDER_ID_COL+"), "+
////                "FOREIGN KEY ("+AVAILABILITY_ID_COL+") REFERENCES "+AVAILABILITY_TB+"("+AVAILABILITY_ID_COL+"));");
////
////
////        db.execSQL("CREATE TABLE "+LENDER_TB+" ("+LENDER_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+USER_EMAIL_COL+" TEXT NOT NULL, "+PHONE_NUM_COL+" INT NOT NULL, "+
////        PROFILE_PIC_COL+" TEXT, "+IS_DEFAULT_COL+" BOOLEAN"+
////                "FOREIGN KEY ("+USER_EMAIL_COL+") REFERENCES "+USER_TB+"("+USER_EMAIL_COL+"));");
////
////        db.execSQL("CREATE TABLE "+SEEKER_TB+" ("+SEEKER_ID_COL+" INT PRIMARY KEY AUTOINCREMENT, "+USER_EMAIL_COL+" TEXT NOT NULL, "+PHONE_NUM_COL+" INT NOT NULL, "+
////                PROFILE_PIC_COL+" TEXT, "+IS_DEFAULT_COL+" BOOLEAN"+
////                "FOREIGN KEY ("+USER_EMAIL_COL+") REFERENCES "+USER_TB+"("+USER_EMAIL_COL+"));");
////
////
////        db.execSQL("INSERT INTO "+CANCELLATION_POLICY_TB+" ("+CANCELLATION_POLICY_COL+") VALUES ("+ CancellationPolicy.FIVE_DOLLAR_CHARGE+"), ("+CancellationPolicy.NO_CHARGE+"), ("+
////                CancellationPolicy.TEN_DOLLAR_CHARGE+"), ("+CancellationPolicy.TEN_PERCENT_OF_CHARGE+");");
////
////        db.execSQL("INSERT INTO "+CATEGORY_TB+" ("+CATEGORY_COL+") VALUES ("+Category.COMPACT+"), ("+Category.COVERED+"), ("+Category.HANDICAPPED+"), ("+
////                Category.SUV+"), ("+Category.TANDEM+"), ("+Category.TRUCK+");");
//    }
//
//
//    public static List<Listing> searchListings(Timestamp startTime, List<String> categories, Address address){
//        String postalCode = address.getPostalCode();
//        String locality = address.getLocality();
//        long time = startTime.getTime();
//        return null;
//    }
}
