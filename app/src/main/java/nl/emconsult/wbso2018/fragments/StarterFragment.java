package nl.emconsult.wbso2018.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.R;

public class StarterFragment extends MyFragment {

	public StarterFragment() {
		fragment = R.layout.starter_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		final CheckBox cbInhoudingsplichtige = (CheckBox) rootView.findViewById(R.id.checkBox_ondernemer);
		final CheckBox cbSOjaren = (CheckBox) rootView.findViewById(R.id.checkBox_aantalaanvragen);
		
		Button btnStarter = (Button) rootView.findViewById(R.id.button_start_starter);
		btnStarter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (cbInhoudingsplichtige.isChecked() && cbSOjaren.isChecked()) {
					Toast.makeText(getActivity(), "U wordt voor de WBSO gezien als starter en geniet van een hoger percentage over de eerste schijf",Toast.LENGTH_LONG).show();
					MainActivity.getAanvraag().setStarter(true);
				}
				else {
					Toast.makeText(getActivity(), "U wordt voor de WBSO niet gezien als starter",Toast.LENGTH_LONG).show();
					MainActivity.getAanvraag().setStarter(false);
				}
				Fragment frag = new AanvraagPeriodesFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		return rootView;
	}
	
}
