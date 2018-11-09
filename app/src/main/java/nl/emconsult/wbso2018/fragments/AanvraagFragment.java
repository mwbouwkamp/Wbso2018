package nl.emconsult.wbso2018.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.layoutElement.MyTextView;
import nl.emconsult.wbso2018.R;

public class AanvraagFragment extends MyFragment {

	int aanvraagNummer;

	public AanvraagFragment() {
		fragment = R.layout.aanvraag_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);

		final MyTextView tvAanvraag = (MyTextView) rootView.findViewById(R.id.textView_aanvraag);
		final EditText etUren = (EditText) rootView.findViewById(R.id.editText_uren);
		final EditText etKosten =(EditText) rootView.findViewById(R.id.editText_kosten);
		final EditText etUitgaven = (EditText) rootView.findViewById(R.id.editText_uitgaven);
		final Button btnSubmit = (Button) rootView.findViewById(R.id.button_submit);
		final LinearLayout llRda = (LinearLayout) rootView.findViewById(R.id.linearLayout_rda);

		ScrollView sView = (ScrollView) rootView.findViewById(R.id.scroll_area);
		sView.setVerticalScrollBarEnabled(false);
		sView.setHorizontalScrollBarEnabled(false);
		
		aanvraagNummer = MainActivity.getAanvraagnummer() + 1;
		tvAanvraag.setText("Aanvraag " + Integer.toString(aanvraagNummer));
		if (MainActivity.getAanvraag().getAantalAanvragen() > MainActivity.getAanvraagnummer() + 1) {
			btnSubmit.setText("Volgende periode");
		}
		
		if (MainActivity.getAanvraag().isRdaForfait()) {
			llRda.setVisibility(LinearLayout.GONE);
		}
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				Fragment frag;
				boolean uitvoeren = true;
				float uren = 0;
				float kosten = 0;
				float uitgaven = 0;
				try {
					uren = Float.valueOf((etUren.getText().toString()));
				} catch (NumberFormatException e) {
					uren = 0;
				}
                try {
                    kosten = Float.valueOf(etKosten.getText().toString());
                } catch (NumberFormatException e) {
                    kosten = 0;
                }
                try {
                    uitgaven = Float.valueOf(etUitgaven.getText().toString());
                } catch (NumberFormatException e) {
                    uitgaven = 0;
                }
				if (uren > 10000000) {
					Toast.makeText(getActivity(), "Het aantal uren dat u opgegeven heeft is niet realistisch.",Toast.LENGTH_LONG).show();
					etUren.setText("");
					uitvoeren = false;
				}
				if (kosten > 100000000) {
					Toast.makeText(getActivity(), "De kosten die u opgegeven heeft zijn niet realistisch.",Toast.LENGTH_LONG).show();
					etKosten.setText("");
					uitvoeren = false;
				}
				if (uitgaven > 100000000) {
					Toast.makeText(getActivity(), "De uitgaven die u opgegeven heeft zijn niet realistisch.",Toast.LENGTH_LONG).show();
					etUitgaven.setText("");
					uitvoeren = false;
				}


				if (uitvoeren) {
					storeValues(uren, kosten, uitgaven);
					if (MainActivity.getAanvraag().getAantalAanvragen() > MainActivity.getAanvraagnummer() + 1) {
		        		MainActivity.setAanvraagnummer(MainActivity.getAanvraagnummer() + 1);
						frag = new AanvraagFragment();
					}
					else {
		        		frag = new ResultatenFragment();
					}
	        		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
	        		fragTransaction.addToBackStack("tag").commit();
				}
			}

			private void storeValues(float uren, float kosten, float uitgaven) {
				try {
					MainActivity.getAanvraag().setUrenVanPeriode(uren, MainActivity.getAanvraagnummer());
				} catch (NumberFormatException e1) {
					MainActivity.getAanvraag().setUrenVanPeriode(0, MainActivity.getAanvraagnummer());
				}					
				if (!MainActivity.getAanvraag().isRdaForfait()) {
					MainActivity.getAanvraag().setRdaVanPeriode(kosten + uitgaven, MainActivity.getAanvraagnummer());
				}
			}
		});

		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            if( keyCode == KeyEvent.KEYCODE_BACK ) {

		            	Fragment frag = new StartFragment();
		        		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
		        		fragTransaction.addToBackStack("tag").commit();
		            	
		            	
		            	return true;
		            } else {
		                return false;
		            }
		        }
		    });

		return rootView;
	}
	
}
