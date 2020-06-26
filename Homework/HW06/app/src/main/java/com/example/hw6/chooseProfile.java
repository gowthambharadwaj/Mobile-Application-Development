package com.example.hw6;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link chooseProfile.OnChooseProfileFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class chooseProfile extends Fragment {

    boolean selectedP = false;
    int image = 0;

    private OnChooseProfileFragmentInteractionListener mListener;

    public chooseProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_profile,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int myInt = bundle.getInt("data", 0);
            this.image=myInt;
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView selectImg = getActivity().findViewById(R.id.selectImg);
        final EditText fname = getActivity().findViewById(R.id.editText);
        final EditText lname = getActivity().findViewById(R.id.editText2);
        final EditText stuId = getActivity().findViewById(R.id.editText3);
        RadioGroup rg = getActivity().findViewById(R.id.radioGroup);
        RadioButton cs = getActivity().findViewById(R.id.radioButton);
        RadioButton sis = getActivity().findViewById(R.id.radioButton2);
        RadioButton bio = getActivity().findViewById(R.id.radioButton3);
        RadioButton oth = getActivity().findViewById(R.id.radioButton4);

        final String[] firstName = new String[1];
        final String[] lastName = new String[1];
        final String[] studentId = new String[1];
        //final String[] disp = new String[1];
        final String[] radioButton = new String[1];

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String string = sharedPreferences.getString("mykey","NA");

        if(string!="NA"){
            Gson gson = new Gson();
            UserData user = gson.fromJson(string,UserData.class);
            fname.setText(user.firstName);
            lname.setText(user.lastName);
            stuId.setText(user.studentId);
            if(user.image!=0) {
                selectImg.setImageResource(user.image);
                //this.image = user.image;
            }else{
                selectImg.setImageResource(R.drawable.select_image);
                //this.image = 0;
            }

            //Log.d("demo","user data: "+user.dept.toString());

            switch (user.dept){
                case "CS":
                    cs.setChecked(true);
                    user.setDept("CS");
                    radioButton[0] = "CS";
                    selectedP = true;
                    break;
                case "SIS":
                    sis.setChecked(true);
                    user.setDept("SIS");
                    radioButton[0] = "SIS";
                    selectedP = true;
                    break;
                case "BIO":
                    bio.setChecked(true);
                    user.setDept("BIO");
                    radioButton[0] = "BIO";
                    selectedP = true;
                    break;
                case "Other":
                    oth.setChecked(true);
                    user.setDept("Other");
                    radioButton[0] = "Other";
                    selectedP = true;
                    break;
            }

        }

        if(image!=0){
            selectImg.setImageResource(image);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButtonSelected = radioGroup.findViewById(i);

                selectedP = radioButtonSelected.isChecked();
                radioButton[0] = String.valueOf(radioButtonSelected.getText());
            }
        });

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoFragment();
            }
        });

        getActivity().findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName[0] = fname.getText().toString().trim();
                lastName[0] = lname.getText().toString().trim();
                studentId[0] = stuId.getText().toString().trim();
                if (firstName[0].length() == 0 || lastName[0].length() ==0)
                {
                    Context appContext = getActivity().getApplicationContext();
                    Toast.makeText(appContext,"Enter valid First Name and Last Name",Toast.LENGTH_SHORT).show();
                }
                else if(studentId[0].equalsIgnoreCase("") || Integer.valueOf(studentId[0])<100000000) {
                    Context appContext = getActivity().getApplicationContext();
                    Toast.makeText(appContext, "Enter 9 digit student ID", Toast.LENGTH_SHORT).show();
                }
                else if (!selectedP) {
                    Context appContext = getActivity().getApplicationContext();
                    Toast.makeText(appContext, "Select a department", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserData user = new UserData(image,firstName[0],lastName[0],studentId[0],radioButton[0]);
                    Log.d("demo","user: "+user);
                    mListener.saveref(user);
                    mListener.goToDisplay(image,firstName[0],lastName[0],studentId[0],radioButton[0]);



                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChooseProfileFragmentInteractionListener) {
            mListener = (OnChooseProfileFragmentInteractionListener) context;
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
    public interface OnChooseProfileFragmentInteractionListener {
        // TODO: Update argument type and name
        void gotoFragment();
        void goToDisplay(int image, String nameOne, String nameTwo, String idOfStudent, String radioButtonSelectedIs);
        void saveref(UserData user);
    }
}
