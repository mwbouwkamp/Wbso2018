package nl.emconsult.wbso2018;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.NumberFormat;

import nl.emconsult.wbso2018.application.Aanvraag;
import nl.emconsult.wbso2018.application.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ApplicationFlowTest {

    MainActivity myActivity;
    int rdaGrensEersteSchijf;
    int rdaEersteSchrijf;
    int rdaTweedeSchijf;
    int soGrensEersteSchijf;
    float soEersteSchijf;
    float soEersteSchijfStarter;
    float soTweedeSchijf;

    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        myActivityTestRule.getActivity();
        rdaGrensEersteSchijf = 1800;
        rdaEersteSchrijf = 10;
        rdaTweedeSchijf = 4;
        soGrensEersteSchijf = 350000;
        soEersteSchijf = 0.32f;
        soEersteSchijfStarter = 0.4f;
        soTweedeSchijf = 0.16f;
    }

    private class TestAanvraag {
        private boolean starter;
        private boolean forfaitair;
        private int uurloon;
        private float[] uren;
        private float[] kosten;
        private float[] uitgaven;

        public TestAanvraag(boolean starter, int uurloon, float[] uren) {
            this.starter = starter;
            this.forfaitair = true;
            this.uurloon = uurloon;
            this.uren = uren;
        }

        public TestAanvraag(boolean starter, int uurloon, float[] uren, float[] kosten, float[] uitgaven) {
            this.starter = starter;
            this.forfaitair = false;
            this.uurloon = uurloon;
            this.uren = uren;
            this.kosten = kosten;
            this.uitgaven = uitgaven;
        }

        private float calcRda(float somUren) {
            if (somUren >= rdaGrensEersteSchijf) {
                return (somUren - rdaGrensEersteSchijf) * rdaTweedeSchijf + rdaGrensEersteSchijf * rdaEersteSchrijf;
            }
            else {
                return somUren * rdaEersteSchrijf;
            }
        }

        public int calcSo() {
            float floatToReturn;
            float somUren = 0;
            float somRda = 0;
            for (float uur: uren) {
                somUren += uur;
            }
            float soLoonKosten;
            if (forfaitair) {
                somRda = calcRda(somUren);
            }
            else {
                for (float kost: kosten) {
                    somRda += kost;
                }
                for (float uitgaaf: uitgaven) {
                    somRda += uitgaaf;
                }
            }
            soLoonKosten = somRda + somUren * uurloon;
            if (soLoonKosten >= soGrensEersteSchijf){
                if (starter) {
                    floatToReturn = (soLoonKosten - soGrensEersteSchijf) * soTweedeSchijf + soGrensEersteSchijf * soEersteSchijfStarter;
                }
                else {
                    floatToReturn = (soLoonKosten - soGrensEersteSchijf) * soTweedeSchijf + soGrensEersteSchijf * soEersteSchijf;
                }
            }
            else {
                if (starter) {
                    floatToReturn = soLoonKosten * soEersteSchijfStarter;
                }
                else {
                    floatToReturn = soLoonKosten * soEersteSchijf;
                }
            }
            return (int) floatToReturn;
        }

    }

    /**
     * Test kiezen aanvraagjaar
     */

    @Test
    public void testChangeYearchange() throws Exception {
        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.textView_jaargang)).check(matches(withText("2017")));
        onView(withId(R.id.button_jaargang)).perform(click());
        onView(withId(R.id.button_2016)).perform(click());
        onView(withId(R.id.textView_jaargang)).check(matches(withText("2016")));
        onView(withId(R.id.button_jaargang)).perform(click());
        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.textView_jaargang)).check(matches(withText("2017")));
    }

    /**
     * Tests rondom startersregime
     */

    @Test
    public void testStarterOndernemerCheckedAantalaanvragenChecked() throws Exception {
        float[] uren = new float[]{soGrensEersteSchijf / 100};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(true, uurloon, uren);

        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_starter)).perform(click());
        onView(withId(R.id.checkBox_aantalaanvragen)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_ondernemer)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_aantalaanvragen)).perform(click());
        onView(withId(R.id.checkBox_ondernemer)).perform(click());
        onView(withId(R.id.button_start_starter)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_forfaitair)).perform(click());
        onView(withId(R.id.editText_uurloon)).perform(clearText()).perform(typeText(Integer.toString(uurloon)));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).perform(clearText()).perform(typeText(Float.toString(uren[0])));
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.textView_afdrachtvermindering)).check(matches(withText(NumberFormat.getInstance().format(aanvraag.calcSo()))));
    }

    @Test
    public void testStarterOndernemerNotCheckedAantalaanvragenChecked() throws Exception {
        float[] uren = new float[]{soGrensEersteSchijf / 100};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_starter)).perform(click());
        onView(withId(R.id.checkBox_aantalaanvragen)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_ondernemer)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_aantalaanvragen)).perform(click());
        onView(withId(R.id.button_start_starter)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_forfaitair)).perform(click());
        onView(withId(R.id.editText_uurloon)).perform(clearText()).perform(typeText(Integer.toString(uurloon)));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).perform(clearText()).perform(typeText(Float.toString(uren[0])));
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.textView_afdrachtvermindering)).check(matches(withText(NumberFormat.getInstance().format(aanvraag.calcSo()))));
    }

    @Test
    public void testStarterOndernemerCheckedAantalaanvragenNotChecked() throws Exception {
        float[] uren = new float[]{soGrensEersteSchijf / 100};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_starter)).perform(click());
        onView(withId(R.id.checkBox_aantalaanvragen)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_ondernemer)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_ondernemer)).perform(click());
        onView(withId(R.id.button_start_starter)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_forfaitair)).perform(click());
        onView(withId(R.id.editText_uurloon)).perform(clearText()).perform(typeText(Integer.toString(uurloon)));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).perform(clearText()).perform(typeText(Float.toString(uren[0])));
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.textView_afdrachtvermindering)).check(matches(withText(NumberFormat.getInstance().format(aanvraag.calcSo()))));
    }

    @Test
    public void testStarterOndernemerNotCheckedAantalaanvragenNotChecked() throws Exception {
        float[] uren = new float[]{soGrensEersteSchijf / 100};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_starter)).perform(click());
        onView(withId(R.id.checkBox_aantalaanvragen)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBox_ondernemer)).check(matches(isNotChecked()));
        onView(withId(R.id.button_start_starter)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_forfaitair)).perform(click());
        onView(withId(R.id.editText_uurloon)).perform(clearText()).perform(typeText(Integer.toString(uurloon)));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).perform(clearText()).perform(typeText(Float.toString(uren[0])));
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.textView_afdrachtvermindering)).check(matches(withText(NumberFormat.getInstance().format(aanvraag.calcSo()))));
    }

    /**
     * Check default setting (uurloon 29, velden voor uren, kosten/uitgaven leeg)
     */

    @Test
    public void testDefaultSettingsForfaitair() throws Exception {
        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_forfaitair)).perform(click());
        onView(withId(R.id.editText_uurloon)).check(matches(withText("29")));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).check(matches(withText("")));
        onView(withId(R.id.linearLayout_rda)).check(matches(not(isDisplayed())));
        onView(withId(R.id.button_submit)).perform(click());
    }

    @Test
    public void testDefaultSettingsKostenuitgaven() throws Exception {
        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_1periode)).perform(click());
        onView(withId(R.id.button_kosten_uitgaven)).perform(click());
        onView(withId(R.id.editText_uurloon)).check(matches(withText("29")));
        onView(withId(R.id.button_volgende)).perform(click());
        onView(withId(R.id.editText_uren)).check(matches(withText("")));
        onView(withId(R.id.linearLayout_rda)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_kosten)).check(matches(withText("")));
        onView(withId(R.id.editText_uitgaven)).check(matches(withText("")));
        onView(withId(R.id.button_submit)).perform(click());
    }

    /**
     * Tests RDA drempel
     */

    // Een aanvraag
    @Test
    public void eenPeriodeRdaForfaitRdaEersteSchijf() throws Exception {
        float[] uren = new float[]{rdaGrensEersteSchijf / 2};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void eenPeriodeRdaForfaitRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf * 2};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    //Twee aanvragen
    @Test
    public void tweePeriodesRdaForfaitRdaEersteSchijfRdaEersteSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf / 3, rdaGrensEersteSchijf / 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void tweePeriodesRdaForfaitRdaEersteSchijfRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf / 3, rdaGrensEersteSchijf * 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void tweePeriodesRdaForfaitRdaTweedeSchijfRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf * 3, rdaGrensEersteSchijf * 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    //Drie aanvragen
    @Test
    public void driePeriodesRdaForfaitRdaEersteSchijfRdaEersteSchijfRdaEersteSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf / 4, rdaGrensEersteSchijf / 4, rdaGrensEersteSchijf / 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitRdaEersteSchijfRdaEersteSchijfRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf / 4, rdaGrensEersteSchijf / 4, rdaGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitRdaEersteSchijfRdaTweedeSchijfRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf / 4, rdaGrensEersteSchijf * 4, rdaGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitRdaTweedeSchijfRdaTweedeSchijfRdaTweedeSchijf() throws Exception{
        float[] uren = new float[]{rdaGrensEersteSchijf * 4, rdaGrensEersteSchijf * 4, rdaGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    /**
     * Tests SO drempel
     */

    // Een aanvraag

    @Test
    public void eenPeriodeRdaForfaitSOEersteSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 2};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void eenPeriodeRdaForfaitSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf * 2};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    //Twee aanvragen

    @Test
    public void tweePeriodesRdaForfaitSOEersteSchijfSOEersteSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 3, soGrensEersteSchijf / 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void tweePeriodesRdaForfaitSOEersteSchijfSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 3, soGrensEersteSchijf * 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void tweePeriodesRdaForfaitSOTweedeSchijfSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf * 3, soGrensEersteSchijf / 3};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    // Drie aanvragen
    @Test
    public void driePeriodesRdaForfaitSOEersteSchijfSOEersteSchijfSOEersteSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 4, soGrensEersteSchijf / 4, soGrensEersteSchijf / 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitSOEersteSchijfSOEersteSchijfSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 4, soGrensEersteSchijf / 4, soGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitSOEersteSchijfSOTweedeSchijfSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf / 4, soGrensEersteSchijf * 4, soGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    @Test
    public void driePeriodesRdaForfaitSOTweedeSchijfSOTweedeSchijfSOTweedeSchijf() throws Exception{
        float[] uren = new float[]{soGrensEersteSchijf * 4, soGrensEersteSchijf * 4, soGrensEersteSchijf * 4};
        int uurloon = 1;
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren);

        goThroughFlow(aanvraag);
    }

    /**
     * RDA tests
     */

    @Test
    public void eenPeriodeKostenUitgavenMoreThanForfait() throws Exception {
        float totaaluren = 1000;
        float[] uren = new float[]{totaaluren};
        int uurloon = 1;
        float rdaKostenUitgavenSameAsForfait = totaaluren * rdaEersteSchrijf;
        float[] rdaKosten = new float[]{rdaKostenUitgavenSameAsForfait + 1};
        float[] rdaUitgaven = new float[]{0};
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren, rdaKosten, rdaUitgaven);

        goThroughFlow(aanvraag);

    }

    @Test
    public void eenPeriodeKostenUitgavenLessThanForfait() throws Exception {
        int totaaluren = 1000;
        float[] uren = new float[]{totaaluren};
        int uurloon = 1;
        int rdaKostenUitgavenSameAsForfait = totaaluren * rdaEersteSchrijf;
        float[] rdaKosten = new float[]{rdaKostenUitgavenSameAsForfait - 1};
        float[] rdaUitgaven = new float[]{0};
        TestAanvraag aanvraag = new TestAanvraag(false, uurloon, uren, rdaKosten, rdaUitgaven);

        goThroughFlow(aanvraag);

    }





    private void goThroughFlow(TestAanvraag aanvraag) {
        onView(withId(R.id.button_2017)).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        switch (aanvraag.uren.length) {
            case 1:
                onView(withId(R.id.button_1periode)).perform(click());
                break;
            case 2:
                onView(withId(R.id.button_2periode)).perform(click());
                break;
            case 3:
                onView(withId(R.id.button_3periode)).perform(click());
                break;
        }
        if (aanvraag.forfaitair) {
            onView(withId(R.id.button_forfaitair)).perform(click());
        }
        else {
            onView(withId(R.id.button_kosten_uitgaven)).perform(click());
        }
        onView(withId(R.id.editText_uurloon)).perform(clearText()).perform(typeText(Integer.toString(aanvraag.uurloon)));
        onView(withId(R.id.button_volgende)).perform(click());
        for (int i = 0; i < aanvraag.uren.length; i++) {
            onView(withId(R.id.editText_uren)).perform(clearText()).perform(typeText(Float.toString(aanvraag.uren[i])));
            if (!aanvraag.forfaitair) {
                onView(withId(R.id.editText_kosten)).perform(clearText()).perform(typeText(Float.toString(aanvraag.kosten[i])));
                onView(withId(R.id.editText_uitgaven)).perform(clearText()).perform(typeText(Float.toString(aanvraag.uitgaven[i])));
            }
            onView(withId(R.id.button_submit)).perform(click());
        }
        onView(withId(R.id.textView_afdrachtvermindering)).check(matches(withText(NumberFormat.getInstance().format(aanvraag.calcSo()))));
    }
}
