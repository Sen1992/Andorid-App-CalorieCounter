package edu.monash.infotech.caloriecounter.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.monash.infotech.caloriecounter.R;
import edu.monash.infotech.caloriecounter.model.MyApplication;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by sen on 2016/4/16.
 */
public class MainUnitFragment extends Fragment {
    View vMain;
    protected MyApplication app;
    protected EditText  name;
    protected EditText  age;
    protected EditText  gender;
    protected EditText  height;
    protected EditText  weight;
    protected EditText  activity;
    protected EditText  step;
    protected EditText  passwrod;
    protected Button    modify;
    protected Button    submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main_unit, container, false);
        app = (MyApplication)getActivity().getApplication();
        init();
        return vMain;
    }

    /**
     * 初始化组件
     */
    private void init(){
        name = (EditText)vMain.findViewById(R.id.nameEdit);
        age = (EditText)vMain.findViewById(R.id.editAge);
        gender = (EditText)vMain.findViewById(R.id.editgender);
        height = (EditText)vMain.findViewById(R.id.editHeight);
        weight = (EditText)vMain.findViewById(R.id.editWeight);
        activity = (EditText)vMain.findViewById(R.id.nameActivity);
        step = (EditText)vMain.findViewById(R.id.stepPerMileEdit);
        passwrod = (EditText)vMain.findViewById(R.id.passwordEdit);
        name.setText(app.getName());
        age.setText(app.getAge());
        gender.setText(app.getGender());
        height.setText(app.getHeight());
        weight.setText(app.getWeight());
        activity.setText(app.getActivity());
        step.setText(app.getStep());
        passwrod.setText(app.getPassword());
        modify = (Button)vMain.findViewById(R.id.Modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEdit();
                System.out.println("哈哈哈");
            }
        });
        submit = (Button)vMain.findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = URLvector.URL_modifyUser + name.getText().toString() + "/" + age.getText().toString() + "/" + height.getText().toString() + "/"
                        + weight.getText().toString() + "/" + gender.getText().toString() + "/" + activity.getText().toString() + "/" + step.getText().toString()
                        + "/" + app.getUsername() + "/" + passwrod.getText().toString();
                setNoEdit();
                System.out.println(url);
                new UpdateUserInfo().execute(url);
            }
        });

        setNoEdit();
    }
    private void setNoEdit(){
        name.setEnabled(false);
        age.setEnabled(false);
        gender.setEnabled(false);
        weight.setEnabled(false);
        height.setEnabled(false);
        activity.setEnabled(false);
        step.setEnabled(false);
        passwrod.setEnabled(false);
    }
    private void setEdit(){
        name.setEnabled(true);
        age.setEnabled(true);
        gender.setEnabled(true);
        weight.setEnabled(true);
        height.setEnabled(true);
        activity.setEnabled(true);
        step.setEnabled(true);
        passwrod.setEnabled(true);
    }

    private class UpdateUserInfo extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
        }
    }
}
