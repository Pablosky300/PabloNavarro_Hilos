package hilos.example.com.hilos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText entrada;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        salida = (TextView) findViewById(R.id.salida);
    }

    public void calcularOperacion(View view){
        int n = Integer.parseInt(entrada.getText().toString());
        salida.append(n + "! = ");
        //int res = factorial(n);
        //salida.append(res +"\n");
        MiTarea tarea = new MiTarea();
        tarea.execute(n);
    }

    public int factorial(int n){
        int res = 1;
        for(int i = 1;i <=n;i++){
            res *= i;
            SystemClock.sleep(1000);
        }
        return res;
    }

    //Practica 5.4
    /*class MiTarea extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... n) {
            return factorial(n[0]);
        }
        @Override
        protected void onPostExecute(Integer res) {
            salida.append(res + "\n");
        }
    }*/
    //Practica 5.5
    class MiTarea extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog progreso;
        @Override
        protected void onPreExecute() {
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progreso.setMessage("Calculando...");
            progreso.setCancelable(false);
            progreso.setMax(100);
            progreso.setProgress(0);
            progreso.show();
        }
        @Override
        protected Integer doInBackground(Integer... n) {
            Integer progreso = new Integer(0);
            int res = 1;
            for (int i = 1; i <= n[0]; i++) {
                res *= i;
                SystemClock.sleep(1000);
                progreso = (i * 100) / n[0];
                publishProgress(progreso);
            }
            return res;
        }
        @Override
        protected void onProgressUpdate(Integer... porcentaje) {
            progreso.setProgress(porcentaje[0]);
        }
        @Override
        protected void onPostExecute(Integer res) {
            progreso.dismiss();
            salida.append(res + "\n");
        }
    }

}
