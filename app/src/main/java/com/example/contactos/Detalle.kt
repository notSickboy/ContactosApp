package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Detalle : AppCompatActivity() {

    var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

       // Configuracion del toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Boton hacia atras en toolbar
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Complemento del boton en listview
        index = intent.getStringExtra("ID").toString().toInt()
        // Log.d("INDEX",index.toString())
        mapearDatos()

    }

    fun mapearDatos(){
        val contacto = MainActivity.obtenerContacto(index)

        var tvNombre = findViewById<TextView>(R.id.tvNombre)
        var tvApellidos = findViewById<TextView>(R.id.tvApellidos)
        var tvEmpresa = findViewById<TextView>(R.id.tvEmpresa)
        var tvEdad = findViewById<TextView>(R.id.tvEdad)
        var tvPeso = findViewById<TextView>(R.id.tvPeso)
        var tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        var tvEmail = findViewById<TextView>(R.id.tvEmail)
        var tvDireccion = findViewById<TextView>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.ivFoto)

        tvNombre.text = contacto.nombre + " " + contacto.apellidos
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString() + " años"
        tvPeso.text = contacto.peso.toString() + " kg"
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        ivFoto.setImageResource(contacto.foto)
    }

    // Crear opciones de la Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            // Configuracion del boton hacia atras en Toolbar
            // ESTE BOTON HACE PERSISTIR LO QUE SE GUARDA
            android.R.id.home -> {
                finish()
                return true
            }

            // Configuracion del boton eliminar en la Toolbar
            R.id.iEliminar -> {
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }

            // Configuracion del boton editar en la Toolbar
            R.id.iEditar -> {
                val intent = Intent(this,Nuevo::class.java)
                intent.putExtra("ID",index.toString())
                startActivity(intent)
                return true
            }


            else -> (return super.onOptionsItemSelected(item))

        }

    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}