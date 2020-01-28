package com.nestoras.strikewatch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StrikeList extends AppCompatActivity {
    SwipeMenuListView strikesListView;
    public StrikesAdapter strikesAdapter;
    static boolean loaded = false;
    int selectedMenu;
    public SQLiteDatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strike_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddStrike();
            }
        });

        strikesListView = findViewById(R.id.StrikesListView);



        db = new SQLiteDatabaseHandler(this);

        strikesAdapter = new StrikesAdapter(this, R.layout.strike_item, db.allStrikes());
        strikesListView.setAdapter(strikesAdapter);


        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(StrikeList.this, login.class);
                startActivity(intToMain);
            }
        });

        ImageView delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteDb();

                Context context = getApplicationContext();
                CharSequence text = "Deleted";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                strikesAdapter.clear();
                strikesAdapter.notifyDataSetChanged();
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "schedule" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xA7, 0x26)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_message_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        strikesListView.setMenuCreator(creator);

        strikesListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        sendSMSMessage();




                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        strikesListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                selectedMenu = position;
            }

            @Override
            public void onSwipeEnd(int position) {
                selectedMenu = position;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_strike_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!loaded) {
            loaded = true;
        }
        else
        {
            Intent intent = getIntent();
            Strike strikeNew = (Strike)intent.getSerializableExtra("Strike");

            if(!db.allStrikes().contains(strikeNew) && strikeNew!=null)
            {
                db.addStrike(strikeNew);

                strikesAdapter.clear();
                strikesAdapter.addAll(db.allStrikes());
                strikesAdapter.notifyDataSetChanged();
                // list all players
            }
        }
    }

    public void openAddStrike() {
        Intent intent = new Intent(this, AddStrike.class);
        startActivity(intent);

    }

    protected void sendSMSMessage() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        Strike messageStrike = strikesAdapter.getItem(selectedMenu);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String ("6985423654"));
        smsIntent.putExtra("sms_body"  , "Stuck in traffic because " + messageStrike.getName() + " is taking place. Be there in a couple of minutes.");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(StrikeList.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Strike messageStrike = strikesAdapter.getItem(selectedMenu);
                    Log.d("asdasdasdsa", messageStrike.getName());
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("415646", null, "asdasd", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

    }
}
