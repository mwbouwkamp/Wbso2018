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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.R;

public class UurloonFragment extends MyFragment {

	public UurloonFragment() {
		fragment = R.layout.uurloon_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = super.onCreateView(inflater, container, savedInstanceState);

		final EditText etUurloon = (EditText) rootView.findViewById(R.id.editText_uurloon);

		final Button btnVolgende = (Button) rootView.findViewById(R.id.button_volgende);
		btnVolgende.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (etUurloon.getText().toString().length() > 3) {
					Toast.makeText(getActivity(), "Het uurloon dat u opgegeven heeft is niet realistisch.",Toast.LENGTH_LONG).show();
					etUurloon.setText("");
				}
				else {
					try {
						MainActivity.getAanvraag().setHourlyRate(Integer.parseInt(etUurloon.getText().toString()));
					} catch (NumberFormatException e) {
						MainActivity.getAanvraag().setHourlyRate(0);
					}
	            	Fragment frag = new AanvraagFragment();
	        		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
					fragTransaction.addToBackStack("tag").commit();
				}
			}
		});
		
		return rootView;
	}
	
}
