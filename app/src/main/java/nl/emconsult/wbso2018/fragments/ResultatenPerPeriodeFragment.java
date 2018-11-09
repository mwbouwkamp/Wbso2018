package nl.emconsult.wbso2018.fragments;

import java.text.NumberFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import nl.emconsult.wbso2018.application.MainActivity;
import nl.emconsult.wbso2018.layoutElement.MyTextView;
import nl.emconsult.wbso2018.R;

import static java.lang.Math.round;

public class ResultatenPerPeriodeFragment extends MyFragment {

	public ResultatenPerPeriodeFragment() {
		fragment = R.layout.resultaten_per_periode_fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = super.onCreateView(inflater, container, savedInstanceState);
				
		MainActivity.getAanvraag().calculateRdaForfait();
		MainActivity.getAanvraag().calculateSO();

		TextView[] tvUren = new TextView[3];
		TextView[] tvUurloon = new TextView[3];
		TextView[] tvSOLoon = new TextView[3];
		TextView[] tvKostenUitgaven = new TextView[3];
		TextView[] tvRdaForfait = new TextView[3];
		TextView[] tvSOloonkosten = new TextView[3];
		TextView[] tvSOAfdrachtvermindering = new TextView[3];
		
		tvUren[0] = (TextView) rootView.findViewById(R.id.textView_periode1_uren);
		tvUren[1] = (TextView) rootView.findViewById(R.id.textView_periode2_uren);
		tvUren[2] = (TextView) rootView.findViewById(R.id.textView_periode3_uren);
		
		tvUurloon[0] = (TextView) rootView.findViewById(R.id.textView_periode1_uurloon);
		tvUurloon[1] = (TextView) rootView.findViewById(R.id.textView_periode2_uurloon);
		tvUurloon[2] = (TextView) rootView.findViewById(R.id.textView_periode3_uurloon);

		tvSOLoon[0] = (TextView) rootView.findViewById(R.id.textView_periode1_soloon);
		tvSOLoon[1] = (TextView) rootView.findViewById(R.id.textView_periode2_soloon);
		tvSOLoon[2] = (TextView) rootView.findViewById(R.id.textView_periode3_soloon);

		tvKostenUitgaven[0] = (TextView) rootView.findViewById(R.id.textView_periode1_kostenuitgaven);
		tvKostenUitgaven[1] = (TextView) rootView.findViewById(R.id.textView_periode2_kostenuitgaven);
		tvKostenUitgaven[2] = (TextView) rootView.findViewById(R.id.textView_periode3_kostenuitgaven);

		tvRdaForfait[0] = (TextView) rootView.findViewById(R.id.textView_periode1_forfait);
		tvRdaForfait[1] = (TextView) rootView.findViewById(R.id.textView_periode2_forfait);
		tvRdaForfait[2] = (TextView) rootView.findViewById(R.id.textView_periode3_forfait);

		tvSOloonkosten[0] = (TextView) rootView.findViewById(R.id.textView_periode1_soloonkosten);
		tvSOloonkosten[1] = (TextView) rootView.findViewById(R.id.textView_periode2_soloonkosten);
		tvSOloonkosten[2] = (TextView) rootView.findViewById(R.id.textView_periode3_soloonkosten);

		tvSOAfdrachtvermindering[0] = (TextView) rootView.findViewById(R.id.textView_periode1_afdrachtvermindering);
		tvSOAfdrachtvermindering[1] = (TextView) rootView.findViewById(R.id.textView_periode2_afdrachtvermindering);
		tvSOAfdrachtvermindering[2] = (TextView) rootView.findViewById(R.id.textView_periode3_afdrachtvermindering);

		
		TableRow[] trKostenUitgaven = new TableRow[3];
		TableRow[] trRdaForfait = new TableRow[3];
		
		trKostenUitgaven[0] = (TableRow) rootView.findViewById(R.id.tableRow_periode1_kostenuitgaven);
		trKostenUitgaven[1] = (TableRow) rootView.findViewById(R.id.tableRow_periode2_kostenuitgaven);
		trKostenUitgaven[2] = (TableRow) rootView.findViewById(R.id.tableRow_periode3_kostenuitgaven);
		
		trRdaForfait[0] = (TableRow) rootView.findViewById(R.id.tableRow_periode1_forfait);
		trRdaForfait[1] = (TableRow) rootView.findViewById(R.id.tableRow_periode2_forfait);
		trRdaForfait[2] = (TableRow) rootView.findViewById(R.id.tableRow_periode3_forfait);
		
		LinearLayout[] llResultaten = new LinearLayout[3];
		
		llResultaten[0] = (LinearLayout) rootView.findViewById(R.id.results_period1);
		llResultaten[1] = (LinearLayout) rootView.findViewById(R.id.results_period2);
		llResultaten[2] = (LinearLayout) rootView.findViewById(R.id.results_period3);
		
		MyTextView tvTotaal = (MyTextView) rootView.findViewById(R.id.textView_totaal);
		
		if (MainActivity.getAanvraag().isRdaForfait()) {
			MainActivity.getAanvraag().calculateRdaForfait();
		}
		MainActivity.getAanvraag().calculateSO();
		
		int totaal = 0;
		for (int i = 0; i < 3; i++) {
			if (i < MainActivity.getAanvraag().getAantalAanvragen()) {
				float hours = MainActivity.getAanvraag().getApplicationPeriods()[i].getHours();
				int rate = MainActivity.getAanvraag().getHourlyRate();
				changeValueTextView(tvUren[i], hours);
				changeValueTextView(tvUurloon[i], rate);
				changeValueTextView(tvSOLoon[i], hours * rate);
				float rda = 0;
				if (!MainActivity.getAanvraag().isRdaForfait()) {
					trRdaForfait[i].setVisibility(TableRow.GONE);
					rda = MainActivity.getAanvraag().getApplicationPeriods()[i].getRda();
					changeValueTextView(tvKostenUitgaven[i], rda);
				}
				else {
					trKostenUitgaven[i].setVisibility(TableRow.GONE);
					rda = MainActivity.getAanvraag().getApplicationPeriods()[i].getRdaForfait();
					changeValueTextView(tvRdaForfait[i], rda);
				}
				changeValueTextView(tvSOloonkosten[i], hours * rate + rda);
				float so = MainActivity.getAanvraag().getApplicationPeriods()[i].getSo();
				changeValueTextView(tvSOAfdrachtvermindering[i], so);
				totaal += so;
			}
			else {
				llResultaten[i].setVisibility(LinearLayout.GONE);
			}
		}
		tvTotaal.setText("€" + NumberFormat.getInstance().format(totaal));

		return rootView;

	}

	
	

    
	private void changeValueTextView(TextView textView, float value) {
		textView.setText(NumberFormat.getInstance().format(round(value)));
		
	}

	private void changeValueTextView(TextView textView, long value) {
		textView.setText(NumberFormat.getInstance().format(value));
		
	}

}