package com.inteltrack.inteltrack.vehiculos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.domain.JsonKeys;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosAdapter extends RecyclerView.Adapter<VehiculosAdapter.ItemVehiculo> {

    private JsonArray data;

    public VehiculosAdapter() {
        data = crearJsonArray();
    }

    @Override
    public ItemVehiculo onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, parent, false);
        return new ItemVehiculo(v);
    }

    @Override
    public void onBindViewHolder(ItemVehiculo holder, int position) {
        JsonObject json = data.get(position).getAsJsonObject();
        holder.bind(json);
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
            json.addProperty(JsonKeys.placa, "P-12"+i);
            jsonArray.add(json);
        }

        return jsonArray;
    }
    class ItemVehiculo extends RecyclerView.ViewHolder{
        TextView txtPlaca;
        TextView txtCliente;
        TextView txtMaps;
        TextView txtWaze;

        public ItemVehiculo(View itemView) {
            super(itemView);
            txtPlaca =(TextView) itemView.findViewById(R.id.txtPlaca);
            txtCliente = (TextView) itemView.findViewById(R.id.txtCliente);
            txtMaps = (TextView) itemView.findViewById(R.id.txtMaps);
            txtWaze = (TextView) itemView.findViewById(R.id.txtWaze);
        }

        public void bind(JsonObject json){
            txtPlaca.setText(json.get(JsonKeys.placa).getAsString());
            txtCliente.setText(json.get(JsonKeys.nombre).getAsString());
        }
    }
}
