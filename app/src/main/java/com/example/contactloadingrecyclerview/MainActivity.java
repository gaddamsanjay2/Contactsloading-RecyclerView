package com.example.contactloadingrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView_contacts;
    ContactModel contactModel;
    ContactAdapter contactAdapter;
    List<ContactModel> contactModelList = new ArrayList<>();

    List<ContactModel> filterList =new ArrayList<>();
    TextView textView;

    Cursor cursor;
    ContentResolver contentResolver;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView_contacts = findViewById(R.id.contactList);
        searchView=findViewById(R.id.searchview);

        textView=findViewById(R.id.TotalCount);



        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 101);
                } else {
                    readAllContacts();
            searchView.setQueryHint("Search among"+" "+contactModelList.size()+" "+"contact(s)");

                }




        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterList.clear();

               if(newText.toString().isEmpty())
               {
                   textView.setVisibility(View.GONE);

                   recyclerView_contacts.setAdapter(new ContactAdapter(getApplicationContext(),contactModelList));
                   contactAdapter.notifyDataSetChanged();


               }

              else {

                   Filter(newText.toString());
                   recyclerView_contacts.setAdapter(new ContactAdapter(getApplicationContext(),filterList));
                   contactAdapter.notifyDataSetChanged();
               }
                return true;
            }


        });


        recyclerView_contacts.setHasFixedSize(true);
        recyclerView_contacts.setLayoutManager(new LinearLayoutManager(this));


        contactAdapter = new ContactAdapter(this, contactModelList);
        recyclerView_contacts.setAdapter(contactAdapter);

//        findViewById(R.id.loadContacts).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                permissionMethod();
//            }
//
//            private void permissionMethod() {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 101);
//                } else {
//                    readAllContacts();
//                    textView.setText("TotalCount"+contactModelList.size());
//                }
//            }
//        });
   }

    private void Filter(String text) {

        textView.setVisibility(View.VISIBLE);



        for (ContactModel post : contactModelList){
            if (post.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(post);
            }
        }

        recyclerView_contacts.setAdapter(new ContactAdapter(getApplicationContext(),filterList));
         contactAdapter.notifyDataSetChanged();
            textView.setText(" "+filterList.size()+" "+"CONTACTS FOUND");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readAllContacts();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void readAllContacts() {
        contactModelList.clear();

        contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; //Uniform Resource identifiers
        String[] projections = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Photo.PHOTO_URI};
        String selection = null; //Selection for row wise Search
        String[] args = null;
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " asc";

        cursor = contentResolver.query(uri, projections, selection, args, order);

        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                @SuppressLint("Range") String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                contactModel=new ContactModel(name,number, photo);
                contactModelList.add(contactModel);

                contactAdapter=new ContactAdapter(getApplicationContext(),contactModelList);
                recyclerView_contacts.setAdapter(contactAdapter);
            }
        }else {
            Toast.makeText(this, "No contacts found in your phone ", Toast.LENGTH_SHORT).show();
        }
    }
}