package com.choccac.proyectoconsumirjsonnodejs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.inqbarna.tablefixheaders.TableFixHeaders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {


    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    ArrayList<Person> personaCls = new ArrayList<Person>();

    private static String url = "https://www.w3schools.com/angular/customers.php";

    TableFixHeaders tableFixHeaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new getJson().execute();

        tableFixHeaders = (TableFixHeaders) findViewById(R.id.tablefixheaders);

    }

    private class getJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Espere por favor...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Respuesta del url: " + jsonStr);
            if (jsonStr != null) {
                try {

                    personaCls.add(new Person("Name","City","Country"));
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray mArray = jsonObj.getJSONArray("records");

                    for (int i = 0; i < mArray.length(); i++) {
                        JSONObject c = mArray.getJSONObject(i);
                        String Name = c.getString("Name");
                        String City = c.getString("City");
                        String Country = c.getString("Country");
                        Log.e(TAG,"\nName: "+Name +" City: "+City+" Country: "+Country+"\n");
                        personaCls.add(new Person(Name,City,Country));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json error de análisis:" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json error de análisis:" + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "No se pudo obtener json del servidor.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No se pudo obtener json del servidor. ¡Revisa LogCat por posibles errores!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Toast.makeText(getApplicationContext(), "Cargado...",Toast.LENGTH_SHORT).show();

            int tamanio_arraybi = personaCls.size();

            String[][] datos = new String[tamanio_arraybi][3];

            for(int i=0; i<tamanio_arraybi; i++){

                datos[i][0] = personaCls.get(i).getName();
            }

            for(int i=0; i<tamanio_arraybi; i++){

                datos[i][1] = personaCls.get(i).getCity();
            }

            for(int i=0; i<tamanio_arraybi; i++){

                datos[i][2] = personaCls.get(i).getCountry();
            }

            MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(getApplicationContext(), datos);
            tableFixHeaders.setAdapter(matrixTableAdapter);
        }



    }
}
