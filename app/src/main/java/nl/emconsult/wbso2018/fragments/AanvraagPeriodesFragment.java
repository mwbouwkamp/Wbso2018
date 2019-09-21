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

public class AanvraagPeriodesFragment extends MyFragment {

	public AanvraagPeriodesFragment() {
		fragment = R.layout.aanvraag_periodes_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		Button btn1Periode = (Button) rootView.findViewById(R.id.button_1periode);
		Button btn2Periode = (Button) rootView.findViewById(R.id.button_2periode);
		Button btn3Periode = (Button) rootView.findViewById(R.id.button_3periode);
		Button btn4Periode = (Button) rootView.findViewById(R.id.button_4periode);

		if (MainActivity.getJaargangen().get(MainActivity.getAanvraag().getWbsoJaargang()).getNumPeriodes() == 4) {
			btn4Periode.setVisibility(View.VISIBLE);
		}

		btn1Periode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().maakPeriodes(1);
				Fragment frag = new RdaRegimeFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});
		
		btn2Periode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().maakPeriodes(2);
				Fragment frag = new RdaRegimeFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btn3Periode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().maakPeriodes(3);
				Fragment frag = new RdaRegimeFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btn4Periode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().maakPeriodes(4);
				Fragment frag = new RdaRegimeFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		return rootView;
	}
	
}
