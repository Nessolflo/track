package com.inteltrack.inteltrack.vehiculos.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.domain.JsonKeys;
import com.inteltrack.inteltrack.vehiculos.VehiculosContract;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosAdapter extends RecyclerView.Adapter<VehiculosAdapter.ItemVehiculo> {

    private JsonArray data;
    private VehiculosContract.View view;

    public VehiculosAdapter(JsonArray data) {
        this.data = data;
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
            json.addProperty(JsonKeys.latitud, 14.5647064);
            json.addProperty(JsonKeys.longitud, -90.4663358);
            jsonArray.add(json);
        }

        return jsonArray;
    }
    class ItemVehiculo extends RecyclerView.ViewHolder{
        TextView txtPlaca;
        TextView txtCliente;
        TextView txtGrupo;
        TextView txtVehiculo;
        TextView txtVelocidad;
        TextView txtMaps;
        TextView txtWaze;

        public ItemVehiculo(View itemView) {
            super(itemView);
            txtPlaca =(TextView) itemView.findViewById(R.id.txtPlaca);
            txtCliente = (TextView) itemView.findViewById(R.id.txtCliente);
            txtGrupo = (TextView) itemView.findViewById(R.id.txtGrupo);
            txtVehiculo = (TextView) itemView.findViewById(R.id.txtVehiculo);
            txtMaps = (TextView) itemView.findViewById(R.id.txtMaps);
            txtWaze = (TextView) itemView.findViewById(R.id.txtWaze);
            txtVelocidad = (TextView) itemView.findViewById(R.id.txtVelocidad);
        }

        public void bind(final JsonObject json){
            Log.e(getClass().getName(), json.toString());
            txtPlaca.setText(json.get(JsonKeys.placa).getAsString().toUpperCase());
            txtCliente.setText(json.get(JsonKeys.propietario).getAsString().toUpperCase());
            txtGrupo.setText(json.get(JsonKeys.grupo).getAsString().toUpperCase());
            txtVehiculo.setText(json.get(JsonKeys.nombre).getAsString().toUpperCase());
            if(json.get(JsonKeys.lastVelocity) == null)
                txtVelocidad.setVisibility(View.GONE);
            else {
                txtVelocidad.setVisibility(View.VISIBLE);
                txtVelocidad.setText(String.format(txtVelocidad.getContext().getString(R.string.velocidad),
                        json.get(JsonKeys.lastVelocity).getAsString()));
            }
            txtWaze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getView()!=null)
                        getView().abrirWaze(json.get(JsonKeys.latitud).getAsDouble(), json.get(JsonKeys.longitud).getAsDouble());
                        //getView().obtenerCoordenadas(json.get(JsonKeys.placa).getAsString(), VehiculosContract.AppConstant.WAZE);
                }
            });
            txtMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getView()!=null)
                        getView().abrirMaps(json.get(JsonKeys.latitud).getAsDouble(), json.get(JsonKeys.longitud).getAsDouble());
                        //getView().obtenerCoordenadas(json.get(JsonKeys.placa).getAsString(), VehiculosContract.AppConstant.GOOGLE);
                }
            });
        }
    }

    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public VehiculosContract.View getView() {
        return view;
    }

    public void setView(VehiculosContract.View view) {
        this.view = view;
    }
}
