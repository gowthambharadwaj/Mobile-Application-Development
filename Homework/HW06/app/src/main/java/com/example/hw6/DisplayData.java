package com.example.hw6;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayData.OnDisplayDataFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DisplayData extends Fragment {

    private OnDisplayDataFragmentInteractionListener mListener;

    int image;
    String firstName;
    String lastName;
    String studentId;
    String radioBtnSelected;

    public DisplayData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_data, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.image = bundle.getInt("image",0);
            this.firstName = bundle.getString("nameOne", "");
            this.lastName = bundle.getString("nameTwo", "");
            this.studentId = bundle.getString("idOfStudent", "");
            this.radioBtnSelected = bundle.getString("radioButtonSelectedIs", "");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView displayImage;
        Button edit;
        displayImage = getActivity().findViewById(R.id.disImg);
        edit = getActivity().findViewById(R.id.editButton);

        TextView fullName = getActivity().findViewById(R.id.studentName);
        TextView id = getActivity().findViewById(R.id.studentId);
        TextView dept = getActivity().findViewById(R.id.department);

        fullName.setText(firstName + " "+ lastName);
        id.setText(studentId);
        dept.setText(radioBtnSelected);
        displayImage.setImageResource(image);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackFragement();

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayDataFragmentInteractionListener) {
            mListener = (OnDisplayDataFragmentInteractionListener) context;
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
    public interface OnDisplayDataFragmentInteractionListener {
        // TODO: Update argument type and name
        void goBackFragement();
    }
}
