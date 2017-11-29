package com.inteltrack.inteltrack.clientes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.domain.JsonKeys;

import java.util.HashMap;

/**
 * Created by nestorso on 28/11/2017.
 */

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ItemCliente> {

    private Context context;
    private JsonArray data;
    private ClienteContract.View view;
    public ClientesAdapter (Context context){
        this.context = context;
        data = crearJsonArray();
    }

    @Override
    public ItemCliente onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ItemCliente(v);
    }

    @Override
    public void onBindViewHolder(ItemCliente holder, int position) {
        final JsonObject info = data.get(position).getAsJsonObject();
        holder.bind(info);
        if(getView()!=null){
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getView().abrirFlotilla(info);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private JsonArray crearJsonArray(){

        JsonArray jsonArray = new JsonArray();
        JsonParser parser= new JsonParser();
        for (int i = 1; i <25 ; i++) {
            JsonObject json = new JsonObject();
            json.addProperty(JsonKeys.nombre, "Cliente "+i);
            json.addProperty(JsonKeys.flotilla, "F. "+i);
            jsonArray.add(json);
        }

        return jsonArray;
    }

    class ItemCliente extends RecyclerView.ViewHolder{
        TextView txtNombreCliente;
        TextView txtFlota;
        RelativeLayout item;

        public ItemCliente(View v){
            super(v);
            item = (RelativeLayout) v.findViewById(R.id.item);
            txtNombreCliente = (TextView) v.findViewById(R.id.txtNombreCliente);
            txtFlota = (TextView) v.findViewById(R.id.txtFlota);
        }

        public void bind(JsonObject cliente) {
            txtNombreCliente.setText(cliente.get(JsonKeys.nombre).getAsString());
            txtFlota.setText(cliente.get(JsonKeys.flotilla).getAsString());
        }
    }

    public ClienteContract.View getView() {
        return view;
    }

    public void setView(ClienteContract.View view) {
        this.view = view;
    }
}
