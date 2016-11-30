package ansteph.com.beecabfordrivers.view.account;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;

public class DriverAccount extends AppCompatActivity {

    GlobalRetainer mGlobalRetainer;

    TextView txtDriverID, txtCompany, txtNextPayment;
    ImageView imgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mGlobalRetainer =(GlobalRetainer) getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtDriverID =(TextView) findViewById(R.id.txtDriverID);
        txtCompany =(TextView) findViewById(R.id.txtCompany);
        txtNextPayment =(TextView) findViewById(R.id.txtDriverID);


        imgStatus = (ImageView) findViewById(R.id.imgStatus);


        txtDriverID.setText(mGlobalRetainer.get_grDriver().getId());
        txtCompany.setText(mGlobalRetainer.get_grDriver().getCompanyname());


    }

}
