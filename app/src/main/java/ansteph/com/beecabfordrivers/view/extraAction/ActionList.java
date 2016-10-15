package ansteph.com.beecabfordrivers.view.extraAction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.testzone.SavePicInternal;
import ansteph.com.beecabfordrivers.testzone.UploadService;

public class ActionList extends AppCompatActivity {

    ListView mActionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        String [] action = new String[]{"Profile","Referral Program","Credits","Contact Us"};



        mActionList = (ListView) findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<>();

        list.add("Profile");
        list.add("Referral Program");
        list.add("Credits");
        list.add("Contact Us");
        list.add("Test Zone");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        mActionList.setAdapter(adapter);


        mActionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0 :
                        Intent i = new Intent(getApplicationContext(), Profile.class);
                        startActivity(i); break;
                    case 1 :
                    case 2:
                    case 3 :
                    case 4 :Intent j = new Intent(getApplicationContext(), SavePicInternal.class);
                        startActivity(j); break;
                    default:
                }

            }
        });

    }
}
