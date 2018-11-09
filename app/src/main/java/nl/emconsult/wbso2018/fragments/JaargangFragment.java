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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.R;

public class JaargangFragment extends MyFragment {

	public JaargangFragment() {
		fragment = R.layout.jaargang_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);

		Button btn2016 = (Button) rootView.findViewById(R.id.button_2016);
		Button btn2017 = (Button) rootView.findViewById(R.id.button_2017);
		Button btn2018 = (Button) rootView.findViewById(R.id.button_2018);
		Button btn2019 = (Button) rootView.findViewById(R.id.button_2019);

		btn2016.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setWbsoJaargang(2016);
				Fragment frag = new StartFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btn2017.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setWbsoJaargang(2017);
				Fragment frag = new StartFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btn2018.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setWbsoJaargang(2018);
				Fragment frag = new StartFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btn2019.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setWbsoJaargang(2019);
				Fragment frag = new StartFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});


		return rootView;
	}
	
}
