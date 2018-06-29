package com.example.innova.saborapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.innova.saborapp.R;
import com.example.innova.saborapp.models.Ingrediente;
import com.example.innova.saborapp.models.Paso;
import com.example.innova.saborapp.models.Receta;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddRecetaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddRecetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecetaFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final int SELECT_PICTURE = 300;
    int IMAGE_PICKER_SELECT = 0;

    //Controles
    private OnFragmentInteractionListener mListener;
    private ImageView imgReceta;
    private TextInputEditText txtTitulo, txtIngrediente, txtPaso;
    private Button btnAddIngrediente, btnGrabarReceta, btnAddPaso;

    //Variables
    DatabaseReference mDatabase;
    private ArrayList<Ingrediente> listIngredientes;
    private ArrayList<Paso> listPasos;
    private String fotoReceta = "";

    public AddRecetaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecetaFragment newInstance(String param1, String param2) {
        AddRecetaFragment fragment = new AddRecetaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_receta, container, false);
        //Inicialización de Variables
        listIngredientes = new ArrayList<Ingrediente>();
        listPasos = new ArrayList<Paso>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Inicialización de Controles
        imgReceta = (ImageView)view.findViewById(R.id.imgReceta);
        imgReceta.requestFocus();
        imgReceta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //seleccionarImagen();
                showOptions();
            }
        });
        txtTitulo = (TextInputEditText)view.findViewById(R.id.txtTitulo);
        txtTitulo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtTitulo.setHint("Ej. Pollo a la Olla");
                } else {
                    txtTitulo.setHint("");
                }
            }
        });
        txtIngrediente = (TextInputEditText)view.findViewById(R.id.txtIngrediente);
        btnAddIngrediente = (Button) view.findViewById(R.id.btnAddIngrediente);
        btnAddIngrediente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String NombreIngrediente = txtIngrediente.getText().toString().trim();
                AddIngrediente(NombreIngrediente);
                txtIngrediente.setText("");
            }
        });

        txtPaso = (TextInputEditText)view.findViewById(R.id.txtPaso);
        btnAddPaso = (Button) view.findViewById(R.id.btnAddPaso);
        btnAddPaso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Descripcion = txtPaso.getText().toString().trim();
                AddPaso(Descripcion);
                txtPaso.setText("");
            }
        });


        btnGrabarReceta = (Button) view.findViewById(R.id.btnGrabarReceta);
        btnGrabarReceta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                grabarReceta();
            }
        });

        return view;
    }

    private void AddIngrediente(String NombreIngrediente) {
        Ingrediente oIngrediente = new Ingrediente();
        oIngrediente.setNombreIngrediente(NombreIngrediente);
        listIngredientes.add(oIngrediente);
    }
    private void AddPaso(String Descripcion) {
        Paso oPaso = new Paso();
        oPaso.setDescripcionPaso(Descripcion);
        listPasos.add(oPaso);
    }

    private void grabarReceta(){
        Receta oReceta = new Receta();
        String nombreReceta = txtTitulo.getText().toString().trim();
        oReceta.setNombreReceta(nombreReceta);
        oReceta.setFotoReceta(fotoReceta);
        oReceta.setIngredientes(listIngredientes);
        oReceta.setPasos(listPasos);

        String id= mDatabase.push().getKey();
        oReceta.setIdReceta(id);
        mDatabase.child("receta").child(id).setValue(oReceta);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imgReceta.setImageURI(path);
                    final InputStream imageStream;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(path);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        fotoReceta = encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }

        /*if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.getActivity());
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {
                setFullImageFromFilePath(mImgProfile, path);
            }
        }*/
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    //openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
