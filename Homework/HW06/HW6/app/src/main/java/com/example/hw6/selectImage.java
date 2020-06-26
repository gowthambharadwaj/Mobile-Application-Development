package com.example.hw6;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link selectImage.OnSelectImageFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class selectImage extends Fragment {



    private OnSelectImageFragmentInteractionListener mListener;

    public selectImage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_image,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView one = getActivity().findViewById(R.id.imageView);
        ImageView two = getActivity().findViewById(R.id.imageView2);
        ImageView four = getActivity().findViewById(R.id.imageView3);
        ImageView five= getActivity().findViewById(R.id.imageView4);
        ImageView three = getActivity().findViewById(R.id.imageView5);
        ImageView six = getActivity().findViewById(R.id.imageView6);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_f_1);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_f_2);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_f_3);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_m_1);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_m_2);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.imageToBeSelectedFragement(R.drawable.avatar_m_3);
            }
        });





    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectImageFragmentInteractionListener) {
            mListener = (OnSelectImageFragmentInteractionListener) context;
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
    public interface OnSelectImageFragmentInteractionListener {
        // TODO: Update argument type and name
        void imageToBeSelectedFragement(int j);
    }
}
