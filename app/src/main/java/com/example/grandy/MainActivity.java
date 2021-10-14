package com.example.grandy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.CellIdentity;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.grandy.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.List;

import android.telephony.AvailableNetworkInfo;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        TelephonyManager tpf = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        List<CellInfo> cls = tpf.getAllCellInfo();
        for (Integer i = 0; i < cls.size(); i++) {
            CellInfo cell = cls.get(i);
            if (cell instanceof CellInfoLte) {
                CellIdentityLte info = (CellIdentityLte) cell.getCellIdentity();
                int ci = info.getCi();
                int cid = ci / 256;
                int sector = ci % 256;
                int eafrcn = info.getEarfcn();
                int pci = info.getPci();
                int tac = info.getTac();
                int mnc = info.getMnc();
                if (ci != 2147483647) {
                    String message = "CID:" + cid + "\n" + "Sector:" + sector + "\n" + "EAfrCN:" + eafrcn + "\n" + "PCI:" + pci + "\n" + "MNC" + mnc + "\n" + "TAC" + tac + "\n";
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }
            }
            if (cell instanceof CellInfoWcdma){
                CellIdentityWcdma info = (CellIdentityWcdma) cell.getCellIdentity();
                int ci = info.getCid();
                int rnc = ci / 65536;
                ci -= 65536 * rnc;
                int cid = ci / 10;
                int sector = ci % 10;
                int psc = info.getPsc();
                int mnc = info.getMnc();
                int lac = info.getLac();
                int uarfcn = info.getUarfcn();
                if (ci != 65535) {
                    String message = "CID:" + cid + "\n" + "Sector:" + sector + "\n" + "uarfcn:" + uarfcn + "\n" + "Psc:" + psc + "\n" + "MNC" + mnc + "\n" + "LAC:" + lac +"\n";
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }
            }
            if (cell instanceof CellInfoGsm){
                CellIdentityGsm info = (CellIdentityGsm) cell.getCellIdentity();
                int ci = info.getCid();

                int cid = ci / 10;
                int sector = ci % 10;
                int bsic = info.getBsic();
                int mnc = info.getMnc();
                int lac = info.getLac();
                int arfcn = info.getArfcn();
                if (ci != 65535) {
                    String message = "CID:" + cid + "\n" + "Sector:" + sector + "\n" + "arfcn:" + arfcn + "\n" + "bsic:" + bsic + "\n" + "MNC" + mnc + "\n" + "LAC:" + lac +"\n";
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }
            }
            CellInfo qq = cls.get(0);


            Toast toast = Toast.makeText(context, qq.toString(), duration);
            toast.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}