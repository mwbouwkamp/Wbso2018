package nl.emconsult.wbso2018.application;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.util.HashMap;

import nl.emconsult.wbso2018.R;
import nl.emconsult.wbso2018.fragments.JaargangFragment;
import nl.emconsult.wbso2018.fragments.StartFragment;

public class MainActivity extends AppCompatActivity {

	private static Aanvraag aanvraag;
	private static int aanvraagnummer;
	private static SparseArray<WbsoJaargang> jaargangen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().hide();

		setWbsoJaargangen();

		aanvraag = new Aanvraag(2020);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.pager, new JaargangFragment()).commit();
		}
		else {
			aanvraag = savedInstanceState.getParcelable("aanvraag");
			aanvraagnummer = savedInstanceState.getInt("aanvraagnummer");
			//jaargangen = (HashMap<Integer, WbsoJaargang>) savedInstanceState.getSerializable("jaargangen");
		}
	    checkFirstRun();
	}

	private void setWbsoJaargangen() {
		WbsoJaargang jaargang2016 = new WbsoJaargang(2016, 3);
		jaargang2016.setupRDA(1800, 10, 4);
		jaargang2016.setupSO(350000, 0.32f, 0.4f, 0.16f);

		WbsoJaargang jaargang2017 = new WbsoJaargang(2017, 3);
		jaargang2017.setupRDA(1800, 10, 4);
		jaargang2017.setupSO(350000, 0.32f, 0.4f, 0.16f);

		WbsoJaargang jaargang2018 = new WbsoJaargang(2018, 3);
		jaargang2018.setupRDA(1800, 10, 4);
		jaargang2018.setupSO(350000, 0.32f, 0.4f, 0.14f);

		WbsoJaargang jaargang2019 = new WbsoJaargang(2019, 3);
		jaargang2019.setupRDA(1800, 10, 4);
		jaargang2019.setupSO(350000, 0.32f, 0.4f, 0.16f);

		WbsoJaargang jaargang2020 = new WbsoJaargang(2020, 4);
		jaargang2020.setupRDA(1800, 10, 4);
		jaargang2020.setupSO(350000, 0.32f, 0.4f, 0.16f);

		jaargangen = new SparseArray<>();
		jaargangen.put(2016, jaargang2016);
		jaargangen.put(2017, jaargang2017);
		jaargangen.put(2018, jaargang2018);
		jaargangen.put(2019, jaargang2019);
		jaargangen.put(2020, jaargang2020);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable("aanvraag", aanvraag);
		savedInstanceState.putInt("aanvraagnummer", aanvraagnummer);
		//savedInstanceState.putSerializable("jaargangen", jaargangen);
	    super.onSaveInstanceState(savedInstanceState);
	}

	private void checkFirstRun() {
		
		SharedPreferences prefs;
		final String PREFS_NAME = "UserData";
		final String PREF_VERSION_CODE_KEY = "version_code";
		final String PREF_SHOW_WARNING_KEY = "show_warning";
		final int DOESNT_EXIST = -1;

		// Get current version code
	    int currentVersionCode;
	    try {
	        currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
	    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
	        // handle exception
	        e.printStackTrace();
	        return;
	    }

		prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

	    // Get saved version code
	    int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

	    // Check for first run or upgrade
	    if (currentVersionCode == savedVersionCode) {
	        // This is just a normal run
	    }
		else if (savedVersionCode == DOESNT_EXIST) {
			
	    	// This is a new install (or the user cleared the shared preferences)
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Aan deze app kunnen geen rechten worden ontleend. Voor meer informatie over de WBSO of hulp bij de aanvraag kunt u contact opnemen met Evers + Manders Subsidieadviseurs.")
			       .setCancelable(false)
			       .setPositiveButton("Ik heb het begrepen", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();				
	    } 
		else if (currentVersionCode > savedVersionCode) {
			// This is an upgrade
			prefs.edit().putBoolean(PREF_SHOW_WARNING_KEY, false).apply();

	    }

	    // Update the shared preferences with the current version code
	    prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
	}

	public static int getAanvraagnummer() {
		return aanvraagnummer;
	}

	public static void setAanvraagnummer(int aanvraagnummer) {
		MainActivity.aanvraagnummer = aanvraagnummer;
	}

	public static Aanvraag getAanvraag() {
		return aanvraag;
	}

	public static SparseArray<WbsoJaargang> getJaargangen() {
		return jaargangen;
	}

}
