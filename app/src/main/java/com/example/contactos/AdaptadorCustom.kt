package com.example.contactos

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.ArrayList

class AdaptadorCustom(var contexto:Context, items:ArrayList<Contacto>):BaseAdapter() {

    // Almacenar los elementos que se van a mostrar en el ListView
    var items:ArrayList<Contacto>? = null
    var copiaItems:ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items)
        this.copiaItems = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Asocia el renderizado de los elementos con los objetos en la lista
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        if(vista == null){
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as? ViewHolder
        }

        // Asignacion de valores a elementos graficos
        val item = getItem(position) as Contacto
        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.empresa?.text = item.empresa
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    override fun getItem(position: Int): Any {
        // Regresar el valor
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        // Regresar el ID del elemento que se selecciona
        return position.toLong()
    }

    override fun getCount(): Int {
        // Regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    fun addItem(item:Contacto){
        copiaItems?.add(item)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int){
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int,newItem:Contacto){
        copiaItems?.set(index,newItem)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun filtrar(str:String){
        items?.clear()

        if(str.isEmpty()){
            items = ArrayList(copiaItems!!)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()

        for(item in copiaItems!!){
            val nombre = item.nombre.toLowerCase()

            if(nombre.contains(busqueda)){
                items?.add(item)
            }
        }

        notifyDataSetChanged()

    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombre)
            foto = vista.findViewById(R.id.ivFoto)
            empresa = vista.findViewById(R.id.tvEmpresa)
        }

    }


}