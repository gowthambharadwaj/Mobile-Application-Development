package com.example.hw6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements chooseProfile.OnChooseProfileFragmentInteractionListener,selectImage.OnSelectImageFragmentInteractionListener,
DisplayData.OnDisplayDataFragmentInteractionListener{

    chooseProfile c = new chooseProfile();
    DisplayData dd = new DisplayData();
    String spref = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My profile");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spref = sharedPreferences.getString("mykey","NA");
        if(spref!="NA"){
            readPreference();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,c)
                    .commit();
        }
    }

    @Override
    public void gotoFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new selectImage(),"dooo")
                .addToBackStack("c")
                .commit();

    }



    @Override
    public void goToDisplay(int image, String nameOne, String nameTwo, String idOfStudent, String radioButtonSelectedIs) {

        Bundle dataSent = new Bundle();
        dataSent.putInt("image",image);
        dataSent.putString("nameOne",nameOne);
        dataSent.putString("nameTwo",nameTwo);
        dataSent.putString("idOfStudent",idOfStudent);
        dataSent.putString("radioButtonSelectedIs",radioButtonSelectedIs);
        dd.setArguments(dataSent);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,dd,"chaar")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void saveref(UserData user) {
        Gson gson = new Gson();
        String json = gson.toJson(user,UserData.class);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("mykey",json);
        editor.apply();
        //Toast.makeText(this, "User Written"+user, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void imageToBeSelectedFragement(int j) {
        Bundle imageDataSent = new Bundle();
        imageDataSent.putInt("data",j);
        c.setArguments(imageDataSent);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,c,"theen")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void goBackFragement() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,c)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("demo","popping back");
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void readPreference(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String string = sharedPreferences.getString("mykey","NA");
        Gson gson = new Gson();
        UserData user = gson.fromJson(string,UserData.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image", user.image);
        bundle.putString("nameOne",user.firstName);
        bundle.putString("nameTwo",user.lastName);
        bundle.putString("idOfStudent",user.studentId);
        bundle.putString("radioButtonSelectedIs",user.dept);
        dd.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,dd)
                .addToBackStack(null)
                .commit();
    }
}
