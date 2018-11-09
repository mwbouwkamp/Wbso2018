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
import android.widget.TextView;

import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.R;

public class StartFragment extends MyFragment {

	
	public StartFragment() {
		fragment = R.layout.start_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		MainActivity.setAanvraagnummer(0);

		TextView textview_jaargang = (TextView) rootView.findViewById(R.id.textView_jaargang);
		textview_jaargang.setText(Integer.toString(MainActivity.getAanvraag().getWbsoJaargang()));

		Button btnStart = (Button) rootView.findViewById(R.id.button_start);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setStarter(false);
				Fragment frag = new AanvraagPeriodesFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});
		
		Button btnStop = (Button) rootView.findViewById(R.id.button_stop);
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	Intent intent = new Intent(Intent.ACTION_MAIN);
            	intent.addCategory(Intent.CATEGORY_HOME);
            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	startActivity(intent);
			}
		});
		
		Button btnStarter = (Button) rootView.findViewById(R.id.button_starter);
		btnStarter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment frag = new StarterFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		Button btnJaargang = (Button) rootView.findViewById(R.id.button_jaargang);
		btnJaargang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment frag = new JaargangFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		return rootView;
	}
		
}
