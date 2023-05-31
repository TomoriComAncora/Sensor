package lucas.curso.tarefa;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ProgressBar barTemp;
    private RatingBar strar;
    private TextView txtTrmp, txtluz, txtHumi, txtProx;
    private SensorManager sm;
    private Sensor sensorTemp, sensorLuz, sensorHum, sensorProx, sensorMovimento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapeamento();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        criacaoSM();

        confirmacao();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valor;
        String valorTexto;
        if(sensorEvent.sensor.getType() == (Sensor.TYPE_AMBIENT_TEMPERATURE)){
            barTemp.setProgress((int)sensorEvent.values[0]);
            valor = sensorEvent.values[0];
            valorTexto = String.valueOf(valor) + " °C";
            txtTrmp.setText(valorTexto);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if((int)sensorEvent.values[0] == 0){
                strar.setProgress(0);
            } else if((int)sensorEvent.values[0]<=8000){
                strar.setProgress(2);
            } else if ((int)sensorEvent.values[0]<=16000) {
                strar.setProgress(4);
            } else if ((int)sensorEvent.values[0]<=24000) {
                strar.setProgress(6);
            } else if ((int)sensorEvent.values[0]<=32000) {
                strar.setProgress(8);
            } else if ((int)sensorEvent.values[0]>32000) {
                strar.setProgress(10);
            }

            valor = sensorEvent.values[0];
            valorTexto = String.valueOf(valor) + " Lux";
            txtluz.setText(valorTexto);
        } else if (sensorEvent.sensor.getType()==(Sensor.TYPE_RELATIVE_HUMIDITY)) {
            valor = sensorEvent.values[0];
            valorTexto = String.valueOf(valor) + " %";
            txtHumi.setText(valorTexto);
        } else if (sensorEvent.sensor.getType()==(Sensor.TYPE_PROXIMITY)) {
            valor = sensorEvent.values[0];
            valorTexto = String.valueOf(valor) + " CM";
            txtProx.setText(valorTexto);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            if(x >= 10.0 || y >= 10.0 || z >= 10.0){
                desregistrar();
                txtTrmp.setText("0");
                txtluz.setText("0");
                txtProx.setText("0");
                txtHumi.setText("0");
            }
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        desregistrar();
        sm.unregisterListener(this, sensorMovimento);
    }

    private void desregistrar(){
        sm.unregisterListener(this, sensorTemp);
        sm.unregisterListener(this, sensorLuz);
        sm.unregisterListener(this, sensorHum);
        sm.unregisterListener(this, sensorProx);
    }

    public void confirmacao(){
        if(sensorTemp != null){
            sm.registerListener(this, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "Sensor não funcional", Toast.LENGTH_SHORT).show();
        }

        if(sensorLuz != null){
            sm.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "Sensor não funcional", Toast.LENGTH_SHORT).show();
        }

        if(sensorHum != null){
            sm.registerListener(this, sensorHum, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "Sensor não funcional", Toast.LENGTH_SHORT).show();
        }

        if(sensorProx != null){
            sm.registerListener(this, sensorProx, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "Sensor não funcional", Toast.LENGTH_SHORT).show();
        }

        if(sensorMovimento != null){
            sm.registerListener(this, sensorMovimento, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "Sensor não funcional", Toast.LENGTH_SHORT).show();
        }
    }

    public void criacaoSM(){
        sensorTemp = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorLuz = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProx = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorHum = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorMovimento = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void mapeamento(){
        barTemp = findViewById(R.id.progressBar);
        txtTrmp = findViewById(R.id.txt_temp);
        strar = findViewById(R.id.starLuz);
        txtluz = findViewById(R.id.textLuz);
        txtHumi = findViewById(R.id.textHumidade);
        txtProx = findViewById(R.id.txtProximidade);
    }
}