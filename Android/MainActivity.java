package com.example.sensorsv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private Button buttonRegister;
    private ProgressDialog progress;
    private final String DeviceID=Build.MANUFACTURER+"_"+Build.MODEL+"_"+Build.ID;
    List<Sensor> BS = new ArrayList<>();
    List<reading> listtosend = new ArrayList<>();
    String time = java.text.DateFormat.getDateTimeInstance().format(new Date());
    Date currenttime=new Date();
    int senddelay=10; //seconds delayed between two data packages


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"MissingSuperCall", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        progress=new ProgressDialog(this);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(v==buttonRegister)
                    for (Sensor currentSensor : BS){
                    registerSensor(currentSensor);
                    }
            }
        });


        SensorManager mSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //trazi sve senzore koje moze nac, rezultat 25
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        //senzori su listed redom: akcelerometar, ziroskop, svjetlosni senzor, senzor magnetnog polja, senzor blizine
        for (final Sensor currentSensor : sensorList) {
            if (currentSensor == mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) {
                Sensor akcelerometar = currentSensor;
                final TextView akcime = (TextView) findViewById(R.id.akcime);
                akcime.setText(akcelerometar.getName());
                mSensorManager.registerListener(this, currentSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                BS.add(currentSensor);
                reading temp=new reading (currentSensor.getStringType());
                listtosend.add(temp);
                akcime.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(v==akcime)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout),
                                    "Type: "+currentSensor.getStringType()+
                                            "\nVendor: "+currentSensor.getVendor()+
                                         "\nRange: "+currentSensor.getMaximumRange()+
                                            "\nResolution: "+currentSensor.getResolution()+
                                          "\nVersion: "+currentSensor.getVersion()+
                                          "\nPower: "+currentSensor.getPower(), Snackbar.LENGTH_LONG);

                            View sbview=snackbar.getView();
                            TextView sbtview=(TextView)sbview.findViewById(com.google.android.material.R.id.snackbar_text);
                            sbtview.setMaxLines(6);
                            snackbar.show();
                        }
                    }
                });
            }
            if (currentSensor == mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)) {
                Sensor gyro = currentSensor;
                final TextView mgyroime = (TextView) findViewById(R.id.gyroime);
                mgyroime.setText(gyro.getName());
                int gyromax = (int) gyro.getMaximumRange() + 1;
                mSensorManager.registerListener(this, currentSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                BS.add(currentSensor);
                reading temp=new reading (currentSensor.getStringType());
                listtosend.add(temp);
                mgyroime.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(v==mgyroime)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout),
                                    "Type: "+currentSensor.getStringType()+
                                            "\nVendor: "+currentSensor.getVendor()+
                                            "\nRange: "+currentSensor.getMaximumRange()+
                                            "\nResolution: "+currentSensor.getResolution()+
                                            "\nVersion: "+currentSensor.getVersion()+
                                            "\nPower: "+currentSensor.getPower(), Snackbar.LENGTH_LONG);

                            View sbview=snackbar.getView();
                            TextView sbtview=(TextView)sbview.findViewById(com.google.android.material.R.id.snackbar_text);
                            sbtview.setMaxLines(6);
                            snackbar.show();
                        }
                    }
                });
            }
            if (currentSensor == mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)) {
                Sensor light = currentSensor;
                final TextView mlightime = (TextView) findViewById(R.id.lightime);
                mlightime.setText(light.getName());
                int lightmax = (int) light.getMaximumRange() + 1;
                mSensorManager.registerListener(this, currentSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                BS.add(currentSensor);
                reading temp=new reading (currentSensor.getStringType());
                listtosend.add(temp);
                mlightime.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(v==mlightime)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout),
                                    "Type: "+currentSensor.getStringType()+
                                            "\nVendor: "+currentSensor.getVendor()+
                                            "\nRange: "+currentSensor.getMaximumRange()+
                                            "\nResolution: "+currentSensor.getResolution()+
                                            "\nVersion: "+currentSensor.getVersion()+
                                            "\nPower: "+currentSensor.getPower(), Snackbar.LENGTH_LONG);

                            View sb1view=snackbar.getView();
                            TextView sbtview=(TextView)sb1view.findViewById(com.google.android.material.R.id.snackbar_text);
                            sbtview.setMaxLines(6);
                            snackbar.show();
                        }
                    }
                });
            }
            if (currentSensor == mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
                Sensor magn = currentSensor;
                final TextView mmagn = (TextView) findViewById(R.id.magnime);
                mmagn.setText(magn.getName());
                int magnmax = (int) magn.getMaximumRange() + 1;
                mSensorManager.registerListener(this, currentSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                BS.add(currentSensor);
                reading temp=new reading (currentSensor.getStringType());
                listtosend.add(temp);
                mmagn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(v==mmagn)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout),
                                    "Type: "+currentSensor.getStringType()+
                                            "\nVendor: "+currentSensor.getVendor()+
                                            "\nRange: "+currentSensor.getMaximumRange()+
                                            "\nResolution: "+currentSensor.getResolution()+
                                            "\nVersion: "+currentSensor.getVersion()+
                                            "\nPower: "+currentSensor.getPower(), Snackbar.LENGTH_LONG);

                            View sb1view=snackbar.getView();
                            TextView sbtview=(TextView)sb1view.findViewById(com.google.android.material.R.id.snackbar_text);
                            sbtview.setMaxLines(6);
                            snackbar.show();
                        }
                    }
                });
            }
            if (currentSensor == mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)) {
                Sensor prox = currentSensor;
                final TextView mprox = (TextView) findViewById(R.id.proxime);
                mprox.setText(prox.getName());
                int proxmax = (int) prox.getMaximumRange() + 1;
                mSensorManager.registerListener(this, currentSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                BS.add(currentSensor);
                reading temp=new reading (currentSensor.getStringType());
                listtosend.add(temp);
                mprox.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(v==mprox)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout),
                                    "Type: "+currentSensor.getStringType()+
                                            "\nVendor: "+currentSensor.getVendor()+
                                            "\nRange: "+currentSensor.getMaximumRange()+
                                            "\nResolution: "+currentSensor.getResolution()+
                                            "\nVersion: "+currentSensor.getVersion()+
                                            "\nPower: "+currentSensor.getPower(), Snackbar.LENGTH_LONG);

                            View sb1view=snackbar.getView();
                            TextView sbtview=(TextView)sb1view.findViewById(com.google.android.material.R.id.snackbar_text);
                            sbtview.setMaxLines(6);
                            snackbar.show();
                        }
                    }
                });
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view){
            if(view==buttonRegister)
                for (Sensor currentSensor : BS){
                    registerSensor(currentSensor);
                }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                ((ProgressBar) findViewById(R.id.akcx)).setProgress((int) (event.values[0] * 10 / event.sensor.getMaximumRange()));
                ((ProgressBar) findViewById(R.id.akcy)).setProgress((int) (event.values[1] * 10 / event.sensor.getMaximumRange()));
                ((ProgressBar) findViewById(R.id.akcz)).setProgress((int) (event.values[2] * 10 / event.sensor.getMaximumRange()));
                ((TextView) findViewById(R.id.akcvalx)).setText("x: " + vrij(event.values[0]) + "m/s");
                ((TextView) findViewById(R.id.akcvaly)).setText("y: " + vrij(event.values[1]) + "m/s");
                ((TextView) findViewById(R.id.akcvalz)).setText("z: " + vrij(event.values[2]) + "m/s");
                listtosend.get(0).x=event.values[0];
                listtosend.get(0).y=event.values[1];
                listtosend.get(0).z=event.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                ((ProgressBar) findViewById(R.id.gyrox)).setProgress((int) (event.values[0] * 100 / event.sensor.getMaximumRange()));
                ((ProgressBar) findViewById(R.id.gyroy)).setProgress((int) (event.values[1] * 100 / event.sensor.getMaximumRange()));
                ((ProgressBar) findViewById(R.id.gyroz)).setProgress((int) (event.values[2] * 100 / event.sensor.getMaximumRange()));
                ((TextView) findViewById(R.id.gyrovalx)).setText("x: " + vrij(event.values[0]) + "rad/s");
                ((TextView) findViewById(R.id.gyrovaly)).setText("y: " + vrij(event.values[1]) + "rad/s");
                ((TextView) findViewById(R.id.gyrovalz)).setText("z: " + vrij(event.values[2]) + "rad/s");
                listtosend.get(1).x=event.values[0];
                listtosend.get(1).y=event.values[1];
                listtosend.get(1).z=event.values[2];
                break;
            case Sensor.TYPE_LIGHT:
                ((ProgressBar) findViewById(R.id.light)).setProgress((int) (event.values[0] * 10000 / event.sensor.getMaximumRange()));
                ((TextView) findViewById(R.id.lightval)).setText(vrij(event.values[0]) + "lux");
                listtosend.get(3).x=event.values[0];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                ((ProgressBar) findViewById(R.id.magnx)).setProgress((int) Math.abs((event.values[0] * 1000 / event.sensor.getMaximumRange())));
                ((ProgressBar) findViewById(R.id.magny)).setProgress((int) Math.abs(event.values[1] * 1000 / event.sensor.getMaximumRange()));
                ((ProgressBar) findViewById(R.id.magnz)).setProgress((int) Math.abs((event.values[2] * 1000 / event.sensor.getMaximumRange())));
                ((TextView) findViewById(R.id.magnvalx)).setText("x: " + vrij(event.values[0]) + "μT");
                ((TextView) findViewById(R.id.magnvaly)).setText("y: " + vrij(event.values[1]) + "μT");
                ((TextView) findViewById(R.id.magnvalz)).setText("z: " + vrij(event.values[2]) + "μT");
                listtosend.get(2).x=event.values[0];
                listtosend.get(2).y=event.values[1];
                listtosend.get(2).z=event.values[2];
                break;
            case Sensor.TYPE_PROXIMITY:
                ((ProgressBar) findViewById(R.id.prox)).setProgress((int) (event.values[0] * 100 / event.sensor.getMaximumRange()));
                int b;
                if (event.values[0] == 0)
                    b = 0;
                else b = 1;
                ((TextView) findViewById(R.id.proxval)).setText(b + "(y/n)");
                listtosend.get(4).x=event.values[0];
                break;
        }
        if(checktime())
        {for(int i=0;i<listtosend.size();i++)
            sendData(listtosend.get(i));}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public String vrij(float a){
    int i;
    i=(int)a;
    String str = String.valueOf(i);
    a=(a-i)*100;
    if(a<0)
        a=0;
    i=(int)a;
    str = str+"."+String.valueOf(i);
    return str;
    }

   public boolean checktime(){
        Date temp=new Date();
        int sec=temp.getSeconds()-currenttime.getSeconds()-senddelay;
        int min=temp.getMinutes()-currenttime.getMinutes();
        if(sec>=0||min>0)
        {
            currenttime=temp;
            time=Integer.toString(currenttime.getYear()+1900)+"/"
                    +Integer.toString(currenttime.getMonth()+1)
                    +"/"+Integer.toString(currenttime.getDate())+"/"
                    +Integer.toString(currenttime.getHours())+"/"
                    +Integer.toString(currenttime.getMinutes())+"/"
                    +Integer.toString(currenttime.getSeconds())+"/";
            return true;
        }
        else return false;
   }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void registerSensor(Sensor s){
        final int Type=s.getType();
        final String Name=s.getName();
        final String Vendor=s.getVendor();
        final String Version= String.valueOf(s.getVersion());
        final double Resolution=s.getResolution();
        final double Sensorrange=s.getMaximumRange();
        final double Mindelay = s.getMinDelay();
        final double Maxdelay = s.getMaxDelay();
        final double Power = s.getPower();

        progress.setMessage("Registering sensors");
        progress.show();

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("type", String.valueOf(Type));
                params.put("name", String.valueOf(Name));
                params.put("vendor", String.valueOf(Vendor));
                params.put("version", Version);
                params.put("resolution", String.valueOf(Resolution));
                params.put("range", String.valueOf(Sensorrange));
                params.put("mindelay", String.valueOf(Mindelay));
                params.put("maxdelay", String.valueOf(Maxdelay));
                params.put("power", String.valueOf(Power));
                params.put("deviceid", DeviceID);
                return params;
                //return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void sendData(final reading data){
        //final String sensor=event.sensor.getName();
        final double x = data.x;
        final double y = data.y;
        final double z = data.z;

        //progress.setMessage("Registering data");
        //progress.show();

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progress.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progress.hide();
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("time", String.valueOf(time));
                params.put("device", String.valueOf(DeviceID));
                params.put("sensor", String.valueOf(data.name));
                params.put("x", String.valueOf(x));
                params.put("y", String.valueOf(y));
                params.put("z", String.valueOf(z));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
