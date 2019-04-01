package me.prapon.contactsyncdemo.sevices;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.widget.Toast;

import me.prapon.contactsyncdemo.database.DatabaseHelper;
import me.prapon.contactsyncdemo.model.Contact;


public class SyncDatabaseService extends Service {


    final class ContactSync implements Runnable {

        int serviceID;

        ContactSync(int serviceID) {
            this.serviceID = serviceID;

        }


        @Override
        public void run() {

            synchronized (this) {

                loadContacts();

                stopSelf(serviceID);

            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "Contacts Syncing..", Toast.LENGTH_SHORT).show();

        Thread thread = new Thread(new ContactSync(startId));
        thread.start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // to stop the service work using onDestroy() method

    @Override
    public void onDestroy() {

        Toast.makeText(getApplicationContext(), "Contacts Sync Complete", Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }

    private void loadContacts(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Contact contact;
        ContentResolver contentResolver = getContentResolver();
        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        //String[] from = {ContactsContract.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone_ID};

        if(cur.getCount() > 0) {
            while(cur.moveToNext()){
                int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString((cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){

                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{String.valueOf(id)},null);

                    while(pCur.moveToNext()){

                        String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        String username = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
//                        Integer imageID = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID));

                        contact = new Contact(id, name, phone);
                        databaseHelper.insertContacts(contact);
                    }
                    //closeDB();
                    pCur.close();
                }
            }

        }

    }
}
