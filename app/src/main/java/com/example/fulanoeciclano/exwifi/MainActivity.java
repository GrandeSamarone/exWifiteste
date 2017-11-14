package com.example.fulanoeciclano.exwifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnClickListener, Wifi.WiFiListener, AdapterView.OnItemClickListener {

    private Wifi wiFi;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itens;
    private List<ScanResult> scans;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.botao).setOnClickListener(this);
        list = (ListView) findViewById(R.id.list);
        itens = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_item, itens);
        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
        list.setAdapter(adapter);


  /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

       public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Wifi wifi = (Wifi) list.getItemAtPosition(position);
           Intent Intentvaiparadescricao = new Intent(MainActivity.this,Wifi_descricao.class);
           Intentvaiparadescricao.putExtra("wifi",Wifi);
           startActivity(Intentvaiparadescricao);
       }
   });
    }
*/
    }

    public void onClick(View view) {
        Toast.makeText(this, "Buscando....", Toast.LENGTH_LONG).show();

        wiFi = Wifi.startScanWIFI(this, this);
    }

    public void onResultScan(Context arg0, Intent arg1, List<ScanResult> results) {
        scans = results;
        itens.clear();
        for (ScanResult scanResult : results) {
            itens.add(scanResult.SSID /*+ " - " + scanResult.BSSID*/);
        }
        adapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        //Configuro uma rede baseada nos dados encontrados.
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
       wifiConfiguration.BSSID = scans.get(arg2).BSSID;
        wifiConfiguration.SSID = "\"" + scans.get(arg2).SSID + "\"";
        wifiConfiguration.preSharedKey = "\"mpt211992manaus\"";


        //Conecto na nova rede criada.

        WifiManager wifiManager = wiFi.getWifiManager(this);
        int netId = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.saveConfiguration();
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }




}