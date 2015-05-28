package com.lazaro.archivosexte;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener  {
    //se declaran las variables
    private EditText txtArchivo;
   private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //se ligan con lo grafico
        txtArchivo = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);




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
    public void onClick(View v) {



        File sdCard, directory, file = null;

        try {
            // se validaa si se encuentra la memoria externa
            if (Environment.getExternalStorageState().equals("mounted")) {

                // se obtiene el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();

                if (v.equals(btnGuardar)) {
                    String str = txtArchivo.getText().toString();

                    // permite grabar texto en un archivo
                    FileOutputStream fout = null;
                    try {

                        directory = new File(sdCard.getAbsolutePath() + "/archivos");
                        // se crea un nuevo directorio donde seguardara el archivo

                        directory.mkdirs();

                        // creamos el archivo en el directorio creado
                        file = new File(directory, "Archivo.txt");

                        fout = new FileOutputStream(file);

                        // Convierte un stream de caracteres en un stream de
                        // bytes
                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str); // Escribe en el buffer
                        ows.flush();
                        ows.close(); // Cierra el archivo de texto

                        Toast.makeText(getBaseContext(), "archivo guardado con exito!!!", Toast.LENGTH_SHORT).show();

                        txtArchivo.setText("");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                if (v.equals(btnAbrir)) {
                    try {

                        directory = new File(sdCard.getAbsolutePath() + "/archivos");

                        file = new File(directory, "Archivo.txt");

                        FileInputStream fin = new FileInputStream(file);

                        InputStreamReader isr = new InputStreamReader(fin);

                        char[] inputBuffer = new char[READ_BLOCK_SIZE];
                        String str = "";

                        // Se lee el archivo de texto

                        int charRead;
                        while ((charRead = isr.read(inputBuffer)) > 0) {

                            String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                            str += strRead;

                            inputBuffer = new char[READ_BLOCK_SIZE];
                        }

                        //  muestra el texto leido
                        txtArchivo.setText(str);

                        isr.close();

                        Toast.makeText(getBaseContext(), "archivo cargado exitosamente", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }else{
                Toast.makeText(getBaseContext(), "El almacenamineto externo fallo", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }
}

