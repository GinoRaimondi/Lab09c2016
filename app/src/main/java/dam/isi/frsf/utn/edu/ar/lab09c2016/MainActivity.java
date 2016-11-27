package dam.isi.frsf.utn.edu.ar.lab09c2016;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView magnitudX;
    private TextView magnitudY;
    private TextView magnitudZ;
    private TextView horaMaxX;
    private TextView horaMaxY;
    private TextView horaMaxZ;
    private TextView todos;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double acceleration[]= {0.0,0.0,0.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        magnitudX = (TextView) findViewById(R.id.magnitudX);
        magnitudY = (TextView) findViewById(R.id.magnitudY);
        magnitudZ = (TextView) findViewById(R.id.magnitudZ);
        horaMaxX = (TextView) findViewById(R.id.horaMaxX);
        horaMaxY = (TextView) findViewById(R.id.horaMaxY);
        horaMaxZ = (TextView) findViewById(R.id.horaMaxZ);
        todos = (TextView) findViewById(R.id.textViewtodos);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean exito = mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Se registró el sensor ", ""+ exito);
        listarSensores();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        /*// ejemplo de internet, no entiendo lo que hace.
        final double alpha = 0.8;
        double gravity[]= new double[3];
        double linear_acceleration[]= new double[3];

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        magnitudX.setText("Magnitud: "+ linear_acceleration[0]);
        magnitudY.setText("Magnitud: "+ linear_acceleration[1]);
        magnitudZ.setText("Magnitud: "+ linear_acceleration[2]);
        */

        //Probar en el caso de TYPE_LINEAR_ACCELERATION

        SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yy G 'at' HH:mm:ss z");
        long hora =(long) (System.currentTimeMillis());
        Date actual = new Date(hora);

        if(event.values[0]>acceleration[0]){
            acceleration[0] = event.values[0];
            magnitudX.setText("Magnitud: "+ acceleration[0]);
            horaMaxX.setText("Hora Máximo X: "+ formato.format(actual));
        }
        if(event.values[1]>acceleration[1]){
            acceleration[1] = event.values[1];
            magnitudY.setText("Magnitud: "+ acceleration[1]);
            horaMaxY.setText("Hora Máximo Y: "+ formato.format(actual));
        }
        if(event.values[2]>acceleration[2]){
            acceleration[2] = event.values[2];
            magnitudZ.setText("Magnitud: "+ acceleration[2]);
            horaMaxZ.setText("Hora Máximo Z: "+ formato.format(actual));
        }

        todos.setText(  "Magnitud X: "+event.values[0]+"\n"+
                        "Magnitud Y: "+event.values[1]+"\n"+
                        "Magnitud Z: "+event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

    private void listarSensores(){
        SensorManager mgr= (SensorManager) this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors= mgr.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensors) {
            Log.d("Nombre",sensor.getName());
            Log.d(" Type: ", sensor.getStringType());
            Log.d(" Vendor: ", sensor.getVendor());
            Log.d(" Version: ", sensor.getVersion()+"");
            try {
                Log.d(" Min Delay: ", sensor.getMinDelay() + "");
            } catch(NoSuchMethodError e) {}
             // ignore ifnotfound
            try {
                Log.d("FIFO Max EventCount:",""+ sensor.getFifoMaxEventCount());
            } catch(NoSuchMethodError e) {} // ignore ifnotfound
            Log.d(" Resolution: " ,""+sensor.getResolution() + "");
            Log.d(" Max Range: ",""+sensor.getMaximumRange() + "");
            Log.d(" Power: ",""+sensor.getPower() + " mA");
        }
    }
}
