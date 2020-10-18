package com.vk.app_arduino;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.biansemao.widget.ThermometerView;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import static android.text.format.DateFormat.*;

public class MainActivity extends AppCompatActivity {

    ChartProgressBar mChart;

    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    Fragment fragment = null;

    private static final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter bluetoothAdapter;

    ArrayList<String> pairedDeviceArrayList;

    ListView listViewPairedDevice;
    ScrollView ButPanel;
    CardView BB;

    ArrayAdapter<String> pairedDeviceAdapter;
    private UUID myUUID;

    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;

    private StringBuilder sb = new StringBuilder();

    public TextView textInfo;

    public TextView Hin;
    public TextView Tin;
    public TextView Hout;
    public TextView Tout;

    public int H;
    public int T;
    public int Ht;
    public int Tt;

    private ThermometerView thermometerTv;

    Dialog dialog_water;
    Dialog dialog_temperature;
    Dialog dialog_GAS;


    public Handler hand = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            sb.append(new String((byte[]) msg.obj, 0, msg.arg1));
            int endOfLineIndex = sb.indexOf("\r\n");

            if (endOfLineIndex > 0) {

                String sbprint = sb.substring(0, endOfLineIndex);
                sb.delete(0, sb.length());
                String[] paket = sbprint.split(" ");
                runOnUiThread(new Runnable() { // Вывод данных
                    @Override
                    public void run() {

                        if (paket.length == 8 && paket[0].equals("A") && paket[7].equals("Z")) {
                            Tin.setText(paket[1] + " C");
                            Hin.setText(paket[2] + " %");
                            Tout.setText(paket[3] + " C");
                            Hout.setText(paket[4] + " %");
                        }
                    }
                });
                H = Integer.parseInt (paket[1]);
                T = Integer.parseInt (paket[2]);
                Ht = Integer.parseInt (paket[3]);
                Tt = Integer.parseInt (paket[4]);
                float G = (float)H * 7 + 16;
                thermometerTv.setValueAndStartAnim(G);
                if(H<20 || H>25){//температура
                    dialog_temperature.setContentView(R.layout.dialog_notification_settings);
                    dialog_temperature.findViewById(R.id.OK_button).setOnClickListener(v -> dialog_temperature.dismiss());
                    dialog_temperature.show();
                }
                if(T<30 && T>60){//влажность
                    //потом
                }
                if(Ht>150){//газ
                    //протечка
                    dialog_GAS.setContentView(R.layout.dialog_widget);
                    dialog_GAS.findViewById(R.id.OK_button).setOnClickListener(v -> dialog_GAS.dismiss());
                    dialog_GAS.show();
                }
                if(Tt<500){//Вода
                    //протечка
                    dialog_water.setContentView(R.layout.dialog_problems);
                    dialog_water.findViewById(R.id.OK_button).setOnClickListener(v -> dialog_water.dismiss());
                    dialog_water.show();
                }
            }

            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";

        textInfo = (TextView)findViewById(R.id.textInfo);
        Tin = (TextView) findViewById(R.id.Tin);
        Hin = (TextView) findViewById(R.id.Hin);
        Tout = (TextView) findViewById(R.id.Tout);
        Hout = (TextView) findViewById(R.id.Hout);

        listViewPairedDevice = (ListView)findViewById(R.id.pairedlist);

        ButPanel = (ScrollView) findViewById(R.id.ScrolViewSettings);
        BB = (CardView) findViewById(R.id.cardViewBB);

        thermometerTv = findViewById(R.id.tv_thermometer);

        dialog_water = new Dialog(MainActivity.this);
        dialog_temperature = new Dialog(MainActivity.this);
        dialog_GAS = new Dialog(MainActivity.this);


        ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Sep", 3.4f, "3.4€");
        dataList.add(data);

        data = new BarData("Oct", 8.0f, "8.0€");
        dataList.add(data);

        data = new BarData("Nov", 1.8f, "1.8€");
        dataList.add(data);

        data = new BarData("Dec", 7.3f, "7.3€");
        dataList.add(data);

        data = new BarData("Jan", 6.2f, "6.2€");
        dataList.add(data);

        data = new BarData("Feb", 3.3f, "3.3€");
        dataList.add(data);

        mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

        mChart.setDataList(dataList);
        mChart.build();
        mChart.setOnBarClickedListener(new OnBarClickedListener() {
            @Override
            public void onBarClicked(int index) {
                //Toast.makeText(CurrentTasksAtHome.this.getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        });
        mChart.disableBar(dataList.size());


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            Toast.makeText(this, "BLUETOOTH NOT support", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String stInfo = bluetoothAdapter.getName() + " " + bluetoothAdapter.getAddress();
        textInfo.setText(String.format("Это устройство: %s", stInfo));

    } // END onCreate


    @Override
    protected void onStart() { // Запрос на включение Bluetooth
        super.onStart();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        setup();
    }

    private void setup() { // Создание списка сопряжённых Bluetooth-устройств

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) { // Если есть сопряжённые устройства

            pairedDeviceArrayList = new ArrayList<>();

            for (BluetoothDevice device : pairedDevices) { // Добавляем сопряжённые устройства - Имя + MAC-адресс
                pairedDeviceArrayList.add(device.getName() + "\n" + device.getAddress());
            }

            pairedDeviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pairedDeviceArrayList);
            listViewPairedDevice.setAdapter(pairedDeviceAdapter);

            listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Клик по нужному устройству

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    listViewPairedDevice.setVisibility(View.GONE); // После клика скрываем список

                    String  itemValue = (String) listViewPairedDevice.getItemAtPosition(position);
                    String MAC = itemValue.substring(itemValue.length() - 17); // Вычленяем MAC-адрес

                    BluetoothDevice device2 = bluetoothAdapter.getRemoteDevice(MAC);

                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device2);
                    myThreadConnectBTdevice.start();  // Запускаем поток для подключения Bluetooth
                }
            });
        }
    }

    @Override
    protected void onDestroy() { // Закрытие приложения
        super.onDestroy();
        if(myThreadConnectBTdevice!=null) myThreadConnectBTdevice.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) { // Если разрешили включить Bluetooth, тогда void setup()

            if (resultCode == Activity.RESULT_OK) {
                setup();
            } else { // Если не разрешили, тогда закрываем приложение

                Toast.makeText(this, "BlueTooth не включён", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    class ThreadConnectBTdevice extends Thread { // Поток для коннекта с Bluetooth

        private BluetoothSocket bluetoothSocket = null;

        private ThreadConnectBTdevice(BluetoothDevice device) {

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() { // Коннект

            boolean success = false;

            try {
                bluetoothSocket.connect();
                success = true;
            }

            catch (IOException e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "Нет коннекта, проверьте " +
                                        "Bluetooth-устройство с которым хотите соединица!",
                                Toast.LENGTH_LONG).show();
                        listViewPairedDevice.setVisibility(View.VISIBLE);
                    }
                });

                try {
                    bluetoothSocket.close();
                }

                catch (IOException e1) {

                    e1.printStackTrace();
                }
            }

            if(success) {  // Если законнектились, тогда открываем панель
                // с кнопками и запускаем поток приёма и отправки данных

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ButPanel.setVisibility(View.VISIBLE); // открываем панель с кнопками
                        BB.setVisibility(View.INVISIBLE);
                    }
                });

                myThreadConnected = new ThreadConnected(bluetoothSocket);
                myThreadConnected.start(); // запуск потока приёма и отправки данных
            }
        }


        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "Close - BluetoothSocket", Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

    } // END ThreadConnectBTdevice:



    class ThreadConnected extends Thread {    // Поток - приём и отправка данных

        final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;

            try {
                in = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            connectedInputStream = in;
        }


        @Override
        public void run() {

            while (true) {
                try {
                    byte[] buffer = new byte[2];
                    MainActivity.this.hand.obtainMessage(0, this.connectedInputStream.read(buffer), 0, buffer).sendToTarget();
                } catch (IOException e) {
                    return;
                }
            }
        }

    }



    //Мусор

   /* BubbleNavigationConstraintView bubbleNavigation = findViewById(R.id.bottom_navigation_view_linear);

    //Значок уведомления возле кнопки на меню
       /* bubbleNavigation.setBadgeValue(0, "40");
        bubbleNavigation.setBadgeValue(1, null); //invisible badge
        bubbleNavigation.setBadgeValue(2, "7");

    //НУжно для того чтобы открыть сразу первый фрагмент
        if(savedInstanceState == null){
        fragmentManager = getSupportFragmentManager();
        CurrentTasksAtHome currentTasksAtHome = new CurrentTasksAtHome();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentTasksAtHome)
                .commit();
    }

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
        @Override
        public void onNavigationChanged(View view, int position) {
            switch (position){
                case 0:
                    fragment = new CurrentTasksAtHome();
                    break;
                case 1:
                    fragment = new ControlOfTheHouse();
                    break;
                case 2:
                    fragment = new Settings();
                    break;
            }
            if (fragment != null){
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            else{
                Log.e(TAG, "Error in creating fragment");
            }
        }
    });*/
} // END
