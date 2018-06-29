package com.example.innova.saborapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.innova.saborapp.R;
import com.example.innova.saborapp.models.Receta;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class itemRecetaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    //Variables
    DatabaseReference mDatabase;
    DatabaseReference mRef;
    public static final String Database_Tabla = "receta";
    ArrayList<Receta> recetaList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public itemRecetaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static itemRecetaFragment newInstance(int columnCount) {
        itemRecetaFragment fragment = new itemRecetaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itemreceta_list, container, false);
        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRef = mDatabase.child(Database_Tabla);
        recetaList = new ArrayList<Receta>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Receta oReceta = snapshot.getValue(Receta.class);
                    /*String key = snapshot.getKey();
                    String nombreReceta = snapshot.child("nombreReceta").getValue(String.class);
                    String FotoReceta = snapshot.child("fotoReceta").getValue(String.class);
                    Receta oReceta = new Receta();
                    oReceta.setNombreReceta(nombreReceta);
                    oReceta.setIdReceta(key);
                    oReceta.setFotoReceta(FotoReceta);*/
                    recetaList.add(oReceta);
                }
                recyclerView.setAdapter(new ItemRecetaRecyclerViewAdapter(recetaList, mListener));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ItemRecetaFragment", "loadTransactions:onCancelled", databaseError.toException());
            }
        });
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               /* System.out.println(dataSnapshot.getValue());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String nombreReceta = snapshot.child("nombreReceta").getValue(String.class);
                    //Receta oReceta = snapshot.getValue(Receta.class);
                    Receta oReceta = new Receta();
                    oReceta.setNombreReceta(nombreReceta);
                    oReceta.setIdReceta(key);
                    recetaList.add(oReceta);
                }*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Set the adapter
        if (view instanceof RecyclerView) {

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //recyclerView.setAdapter(new ItemRecetaRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            //recyclerView.setAdapter(new ItemRecetaRecyclerViewAdapter(recetaList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Receta item);
    }
}
