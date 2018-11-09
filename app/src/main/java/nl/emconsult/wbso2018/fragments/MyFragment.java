package nl.emconsult.wbso2018.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import nl.emconsult.wbso2018.R;


/**
 * Created by mwbou on 25-8-2017.
 */

public class MyFragment extends Fragment {

    View rootView;
    int fragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(fragment, container, false);

        ImageView ivInfo = (ImageView) rootView.findViewById(R.id.imageView_info);
        ivInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Hier vult u uw uurloon in. Dit uurloon is gebaseerd op de lonen van de medewerkers die twee jaar eerder aan WBSO-projecten gewerkt hebben. Het uurloon wordt vastgesteld door RVO. \n\nDe eerste twee jaar dat u de WBSO aanvraagt werkt de WBSO met een standaarduurloon van € 29.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ImageView ivEmconsult = (ImageView) rootView.findViewById(R.id.imageView_emconsult);
        ivEmconsult.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("http://www.emconsult.nl/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });


        return rootView;
    }
}