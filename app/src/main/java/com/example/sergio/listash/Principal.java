package com.example.sergio.listash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Principal extends Activity {
    /*****DECLARAMOS VARIABLES*/
    ArrayList<Componente> datos;
    private Adaptador adapter;
    private RadioGroup rg;
    private EditText etN;
    private EditText etP;
    private String tipo;
    private String nombre;
    private int precio;
    private ListView lv = null;
    private EditText etNV;
    private EditText etPV;
    private Componente aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        start(); //metodo que reune lo necesario al arranca la app
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            return add();
        }
        return super.onOptionsItemSelected(item);
    }

    /* Al hacer long clic sobre item del ListView */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int id = item.getItemId();
        if(id == R.id.action_add){
            return add();
        }else if (id == R.id.action_borrar){
            return del(info);
        }else if (id == R.id.action_edit){
            return edit(info);
        }
        return super.onContextItemSelected(item);
    }

    // añadiendo datos manualmente y pasando al adaptador

    public void start(){
        lv = (ListView)findViewById(R.id.listavista);
        datos = new ArrayList<Componente>();
        Componente comp = new Componente();
        comp.setTipo("Hardware");
        comp.setNombre("Fuente de alimentacion");
        comp.setPrecio(50);

        datos.add(comp);
        comp = new Componente();
        comp.setTipo("Software");
        comp.setNombre("Microsoft Office 2013");
        comp.setPrecio(150);

        datos.add(comp);
        comp = new Componente();
        comp.setTipo("Hardware");
        comp.setNombre("WebCam");
        comp.setPrecio(20);

        datos.add(comp);


        adapter = new Adaptador(this, R.layout.elemento, datos);

        lv.setAdapter(adapter);
        registerForContextMenu(lv);
    }

    public boolean add(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.tv_titulodialogo);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoadd, null);
        alert.setView(vista);

        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rg  = (RadioGroup) vista.findViewById(R.id.grupo);
                int selectedId = rg.getCheckedRadioButtonId();//saco el ID del radiobutton que seleccionamos
                RadioButton radio = (RadioButton) rg.findViewById(selectedId);
                etN = (EditText) vista.findViewById(R.id.etNombre);
                etP = (EditText) vista.findViewById(R.id.etPrecio);
                if(radio.getText().toString().equalsIgnoreCase("Software")){
                    tipo = "Software";
                }else{
                    tipo = "Hardware";
                }
                nombre = etN.getText().toString();
                try {
                    precio = (Integer.valueOf(etP.getText().toString()));
                    Componente compo = new Componente(precio, tipo, nombre);
                    datos.add(compo);
                }catch (NumberFormatException e){
                    tostada("Campo 'precio' incompleto");
                }

                adapter.notifyDataSetChanged();
            }
            });
        alert.show();
        return true;

        }

    public boolean del(AdapterView.AdapterContextMenuInfo info){

        int indice = info.position;

        datos.remove(indice);
        adapter.notifyDataSetChanged();
        return true;
    }

    public boolean edit(AdapterView.AdapterContextMenuInfo info){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.tv_titulodialogoedit);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoedit, null);
        alert.setView(vista);
        final int indice = info.position;
        etNV = (EditText) vista.findViewById(R.id.etNViejo);
        etPV = (EditText) vista.findViewById(R.id.etPViejo);
        etNV.setText(datos.get(indice).getNombre());
        etPV.setText(String.valueOf(datos.get(indice).getPrecio()));
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        datos.get(indice).setPrecio((Integer.valueOf(etPV.getText().toString())));
                        datos.get(indice).setNombre(etNV.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                adapter.notifyDataSetChanged();
        alert.show();
        return true;
    }
    public void ordenar(View v){
        for (int i = 0; i <datos.size()-1; i++){
            for(int j = i+1; j<datos.size(); j++){
                if(datos.get(i).getTipo().compareToIgnoreCase(datos.get(j).getTipo())>0){
                    aux = datos.get(j);
                    datos.set(j,datos.get(i));
                    datos.set(i,aux);
                }
            }
        }
        tostada("Ordenados " +datos.size()+" elementos.");
        adapter.notifyDataSetChanged();
    }
    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
