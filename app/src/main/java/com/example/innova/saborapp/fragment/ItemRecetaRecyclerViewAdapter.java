package com.example.innova.saborapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innova.saborapp.R;

import com.example.innova.saborapp.fragment.itemRecetaFragment.OnListFragmentInteractionListener;
import com.example.innova.saborapp.models.Receta;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ItemRecetaRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecetaRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private ArrayList<Receta> recetaList;

    public ItemRecetaRecyclerViewAdapter(ArrayList<Receta> items, OnListFragmentInteractionListener listener) {
        recetaList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_itemreceta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = recetaList.get(position);
        holder.mContentView.setText(recetaList.get(position).getNombreReceta());
        String strBase64 = recetaList.get(position).getFotoReceta();
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgRecetaLista.setImageBitmap(decodedByte);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    Toast.makeText(v.getContext(),holder.mItem.getIdReceta().toString(),Toast.LENGTH_LONG).show();

                    Receta oReceta = new Receta();
                    oReceta.setNombreReceta(holder.mItem.getNombreReceta().toString());
                    oReceta.setFotoReceta(holder.mItem.getFotoReceta().toString());
                    oReceta.setIngredientes(holder.mItem.getIngredientes());
                    oReceta.setPasos(holder.mItem.getPasos());

                    infoRecetaFragment myFragment = new infoRecetaFragment();
                    myFragment.setReceta(oReceta);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frmFragmetContainer, myFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recetaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView imgRecetaLista;
        public Receta mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.lblNombreLista);
            imgRecetaLista = (ImageView) view.findViewById(R.id.imgRecetaLista);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
