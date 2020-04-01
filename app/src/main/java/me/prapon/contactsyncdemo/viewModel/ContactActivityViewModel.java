package me.prapon.contactsyncdemo.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.prapon.contactsyncdemo.database.ContactDAO;
import me.prapon.contactsyncdemo.database.Database;
import me.prapon.contactsyncdemo.model.Contact;

public class ContactActivityViewModel extends AndroidViewModel {

    private Database database;
    private ContactDAO contactDAO;

    private LiveData<List<Contact>> contactList;

    public ContactActivityViewModel(@NonNull Application application) {
        super(application);

        database = Database.getInstance(getApplication().getApplicationContext());
        contactDAO = database.contactDAO();
        contactList = contactDAO.load();
    }
    
    public void sync() {
        new DatabaseAsyncTask(contactDAO).execute();
    }

    public LiveData<List<Contact>> getContactList() {
        return contactList;
    }

    private void syncWithPhoneBook(ContactDAO contactDAO){
        ContentResolver contentResolver = getApplication().getContentResolver();

        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        //String[] from = {ContactsContract.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone_ID};

        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString((cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{String.valueOf(id)}, null);

                    if (pCur != null) {
                        while (pCur.moveToNext()) {

                            String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //                        String username = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                            //                        Integer imageID = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID));

                            contactDAO.insert(new Contact(name, phone));
                        }
                        pCur.close();
                    }
                }
            }
            cur.close();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        ContactDAO mContactDAO;

        DatabaseAsyncTask(ContactDAO contactDAO) {
            this.mContactDAO = contactDAO;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            syncWithPhoneBook(contactDAO);
            return null;
        }
    }
}
