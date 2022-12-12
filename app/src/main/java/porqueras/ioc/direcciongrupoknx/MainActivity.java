package porqueras.ioc.direcciongrupoknx;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText numPlanta, numDali, numGrupo;
    TextView direccion;
    Button botonConvertir;
    private int resultado = 0;
    private int planta = 0;
    private int dali = 0;
    private int grupo = 0;
    private int e = 0;
    private int f = 0;
    private int m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos los objetos
        numPlanta = (EditText) findViewById(R.id.editTextPlanta);
        numDali = (EditText) findViewById(R.id.editTextDali);
        numGrupo = (EditText) findViewById(R.id.editTextGrupo);
        direccion = (TextView) findViewById(R.id.textViewResultado);
        botonConvertir = (Button) findViewById(R.id.botonConversor);

        //Recuperamos los datos si están guardados
        if (savedInstanceState != null) {
            m = savedInstanceState.getInt("n1");
            f = savedInstanceState.getInt("n2");
            direccion.setText("Dirección de grupo:  * / " + m + " / " + f);
        }

        //Si pulsamos el botón 'CONVERTIR' se realiza la conversión
        botonConvertir.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                    planta = Integer.parseInt(String.valueOf(numPlanta.getText()));
                    dali = Integer.parseInt(String.valueOf(numDali.getText()));
                    grupo = Integer.parseInt(String.valueOf(numGrupo.getText()));
                    resultado = planta * 100 + (dali - 1) * 16 + grupo;
                    for (int n = 1; n < 8; n++) {
                        e = resultado - (n * 256);
                        if (e > 0) {
                            f = e;
                            m = n;
                        }
                    }
                    direccion.setText("Dirección de grupo:  * / " + m + " / " + f);
                    numPlanta.setText("");
                    numDali.setText("");
                    numGrupo.setText("");
                } catch (Exception e) {
                    Context context = getApplicationContext();
                    CharSequence text = "Error en la introducción de los datos, faltan campos por rellenar";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //Escondemos el teclado
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(botonConvertir.getWindowToken(), 0);
            }
        });

    }

    //Guardamos el estado de la actividad
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("n1", m);
        savedInstanceState.putInt("n2", f);
    }

    //Inflamos el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Opciones del menú
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acercaDe:
                Intent i = new Intent(this, AcercaDe.class);
                startActivity(i);
                break;
            case R.id.salir:
                finish();
                break;
        }
        return false;
    }
}
