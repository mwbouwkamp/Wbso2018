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
import android.widget.ImageView;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.R;

public class RdaRegimeFragment extends MyFragment {

	public RdaRegimeFragment() {
		fragment = R.layout.rda_regime_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		Button btnKostenUitgaven = (Button) rootView.findViewById(R.id.button_kosten_uitgaven);
		Button btnForfaitair = (Button) rootView.findViewById(R.id.button_forfaitair);
		
		btnKostenUitgaven.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setRdaForfait(false);
				Fragment frag = new UurloonFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		btnForfaitair.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getAanvraag().setRdaForfait(true);
				Fragment frag = new UurloonFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		return rootView;
	}
	
}
