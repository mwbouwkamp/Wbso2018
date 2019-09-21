package nl.emconsult.wbso2018.fragments;

import java.text.NumberFormat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.layoutElement.MyTextView;
import nl.emconsult.wbso2018.R;

import static java.lang.Math.round;

public class ResultatenFragment extends MyFragment {

	public ResultatenFragment() {
		fragment = R.layout.resultaten_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

		displayWarning(); 
		
		Button btnResultatenPerPeriode = (Button) rootView.findViewById(R.id.button_per_periode);
		btnResultatenPerPeriode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment frag = new ResultatenPerPeriodeFragment();
				FragmentTransaction fragTransaction = getFragmentManager().beginTransaction().replace(R.id.pager, frag);
				fragTransaction.addToBackStack("tag").commit();
			}
		});

		ImageView ivBelOns = (ImageView) rootView.findViewById(R.id.imageView_belons);
		ivBelOns.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0332534820"));
				startActivity(intent);
				
			}
		});
		
		boolean rdaKostenUitgavenGroterDanForfait = vulVelden(rootView);
		if (!MainActivity.getAanvraag().isRdaForfait() && !rdaKostenUitgavenGroterDanForfait) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setMessage("Let op: De berekening op basis van kosten en uitgaven komt lager uit dan het forfait. Gebruikt u de app voor een deel van het jaar en u verwacht in een latere periode aanvullende kosten en uitgaven, dan kan het zijn dat een berekening op basis van kosten en uitgaven wel hoger uitvalt. \n\n Wilt u switchen naar forfaitair?")
			       .setCancelable(false)
			       .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.getAanvraag().setRdaForfait(true);
						vulVelden(rootView);
					}
				})
			       .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();				
		}
		
		ImageView ivInfo = (ImageView) rootView.findViewById(R.id.imageView_info);
		ivInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage("De afdrachtvermindering is nooit meer dan uw totale verschuldigde loonheffing\n\nVoor meer informatie of hulp bij het aanvragen kunt u contact opnemen met Evers + Manders Subsidieadviseurs (033 - 253 4820).\n\nLet op: Aan deze berekening kunnen geen rechten worden ontleend.")
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
		ivEmconsult.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uriUrl = Uri.parse("http://www.emconsult.nl/"); 
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
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

	private void displayWarning() {
		
		SharedPreferences prefs;
		final String PREFS_NAME = "UserData";
		final String PREF_SHOW_WARNING_KEY = "show_warning";

		prefs = this.getActivity().getSharedPreferences(PREFS_NAME, 0);

	    boolean displayWarnings = prefs.getBoolean(PREF_SHOW_WARNING_KEY, true);
	    
		if (displayWarnings) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setMessage("De afdrachtvermindering is nooit meer dan uw totale verschuldigde loonheffing en wordt achteraf vastgesteld op basis van de daadwerkelijk bestede en toegekende uren")
					.setCancelable(false)
					.setPositiveButton("Ik heb het begrepen", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// do things
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
		displayWarnings = false;
		prefs.edit().putBoolean(PREF_SHOW_WARNING_KEY, displayWarnings).commit();
	}
	
	
	/**
	 * Vult de velden in het resultatenscherm en kijkt of de kosten/uitgaven hoger zijn dat het forfait
	 * @param rootView
	 * @return true als kosten/uitgaven hoger zijn dan het forfait
	 */
	private boolean vulVelden(View rootView) {
		MainActivity.getAanvraag().calculateRdaForfait();
		MainActivity.getAanvraag().calculateSO();

		TextView tvUren = (TextView) rootView.findViewById(R.id.textView_uren);
		TextView tvUurloon = (TextView) rootView.findViewById(R.id.textView_uurloon);
		TextView tvSOLoon = (TextView) rootView.findViewById(R.id.textView_soloon);
		TextView tvRdaLabel = (TextView) rootView.findViewById(R.id.textView_rda_label);
		TextView tvRda = (TextView) rootView.findViewById(R.id.textView_rda);
		TextView tvSOloonkosten = (TextView) rootView.findViewById(R.id.textView_soloonkosten);
		TextView tvSOAfdrachtvermindering = (TextView) rootView.findViewById(R.id.textView_afdrachtvermindering);

		MyTextView tvTotaal = (MyTextView) rootView.findViewById(R.id.textView_totaal);

		int uurloon = MainActivity.getAanvraag().getHourlyRate();
		float uren = 0;
		float soLoon = 0;
		float rdaKostenUitgaven = 0;
		float rdaForfait = 0;
		float soLoonkosten = 0;
		float soAfdrachtvermindering = 0;

		if (MainActivity.getAanvraag().isRdaForfait()) {
			tvRdaLabel.setText("RDA forfait");
		}
		else {
			tvRdaLabel.setText("Kosten en uitgaven");
		}
		for (int i = 0; i < 4; i++) {
			if (i < MainActivity.getAanvraag().getAantalAanvragen()) {
				uren += MainActivity.getAanvraag().getApplicationPeriods()[i].getHours();
				soLoon += MainActivity.getAanvraag().getApplicationPeriods()[i].getHours() * uurloon;
				rdaKostenUitgaven += MainActivity.getAanvraag().getApplicationPeriods()[i].getRda();
				rdaForfait += MainActivity.getAanvraag().getApplicationPeriods()[i].getRdaForfait();
				if (!MainActivity.getAanvraag().isRdaForfait()) {
					soLoonkosten += MainActivity.getAanvraag().getApplicationPeriods()[i].getHours() * uurloon + MainActivity.getAanvraag().getApplicationPeriods()[i].getRda();
				}
				else {
					soLoonkosten += MainActivity.getAanvraag().getApplicationPeriods()[i].getHours() * uurloon + MainActivity.getAanvraag().getApplicationPeriods()[i].getRdaForfait();
				}
				soAfdrachtvermindering += MainActivity.getAanvraag().getApplicationPeriods()[i].getSo();
			}
		}

		long urenLong = round(uren);
		long soLoonLong = round(soLoon);
		long rdaKostenUitgavenLong = round(rdaKostenUitgaven);
		long rdaForfaitLong = round(rdaForfait);
		long soLoonkostenLong = round(soLoonkosten);
		long soAfdrachtverminderingLong = round(soAfdrachtvermindering);

		tvUren.setText(NumberFormat.getInstance().format(urenLong));
		tvUurloon.setText(NumberFormat.getInstance().format(uurloon));
		tvSOLoon.setText(NumberFormat.getInstance().format(soLoonLong));
		if (!MainActivity.getAanvraag().isRdaForfait()) {
			tvRda.setText(NumberFormat.getInstance().format(rdaKostenUitgavenLong));
		}
		else {
			tvRda.setText(NumberFormat.getInstance().format(rdaForfaitLong));
		}
		tvSOloonkosten.setText(NumberFormat.getInstance().format(soLoonkostenLong));
		tvSOAfdrachtvermindering.setText(NumberFormat.getInstance().format(soAfdrachtverminderingLong));
		tvTotaal.setText("€" + NumberFormat.getInstance().format(soAfdrachtverminderingLong));
		return (rdaKostenUitgaven > rdaForfait);
	}
    
}
