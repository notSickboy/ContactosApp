package com.example.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null

    companion object{
        var adaptador:AdaptadorCustom? = null
        var contactos:ArrayList<Contacto>? = null
        var adaptadorGrid:AdaptadorCustomGrid? = null

        fun agregarContacto(contacto:Contacto){
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto

        }

        fun eliminarContacto(index:Int){
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int,nuevoContacto:Contacto){
            adaptador?.updateItem(index,nuevoContacto)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Diego","Martinez","Contoso",21,60.00F,"Reforma 2950","9171078345","diego.pue22@gmail.com",R.drawable.foto_01))
        contactos?.add(Contacto("America","Rincon","Comex",20,60.00F,"Reforma 2950","9191848","di22@gmail.com",R.drawable.foto_02))
        contactos?.add(Contacto("Jose","Jose","Pemex",29,60.00F,"Reforma 2950","23482348","die2@gmail.com",R.drawable.foto_03))
        contactos?.add(Contacto("Pepe","Pan","Gaytan",30,60.00F,"Reforma 2950","2893748","pue2@gmail.com",R.drawable.foto_04))
        contactos?.add(Contacto("Pancho","Pistolas","Palacios",28,60.00F,"Reforma 2950","2834779","dieg@gmail.com",R.drawable.foto_05))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustom(this,contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)
        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        // Identificar ListView con la vista Detalle, se complementa desde Detalle.kt
        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID",position.toString())
            startActivity(intent)
        }
    }

    // Crear opciones de la Toolbar

    // Busqueda
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.Search_view)
        val searchView =itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switch_view)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            // Preparar los datos
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar
                adaptador?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filtrar
                return true
            }
        })

        //Switch
        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo ->{
                val intent = Intent(this,Nuevo::class.java)
                startActivity(intent)
                return true
            }

            else -> (return super.onOptionsItemSelected(item))

        }

    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }

}