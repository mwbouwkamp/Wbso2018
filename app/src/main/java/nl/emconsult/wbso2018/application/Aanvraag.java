package nl.emconsult.wbso2018.application;

import android.os.Parcel;
import android.os.Parcelable;

public class Aanvraag implements Parcelable {

	private int wbsoJaargang;
	private int aantalAanvragen;
	private boolean forfaitaireAanvraag;
	private boolean starter;
	private int soUurloon;
	private AanvraagPeriode[] aanvraagPeriodes;
		
	public Aanvraag(int wbsoJaargang) {
		this.wbsoJaargang = wbsoJaargang;
	}
	
	public void maakPeriodes(int numberOfPeriods) {
		this.setNumberOfPeriods(numberOfPeriods);
		this.aanvraagPeriodes = new AanvraagPeriode[numberOfPeriods];
		for (int i = 0; i < numberOfPeriods; i++) {
			this.aanvraagPeriodes[i] = new AanvraagPeriode(0);
		}
	}
	
	//Speciale setters and getters
	
	public void setUrenVanPeriode(float uren, int periode) {
		aanvraagPeriodes[periode].setHours(uren);
	}
	
	public void setRdaVanPeriode(float rda, int periode) {
		aanvraagPeriodes[periode].setRda(rda);
	}

	//Other setters and getters

	public int getWbsoJaargang() {
		return wbsoJaargang;
	}

	public void setWbsoJaargang(int wbsoJaargang) {
		this.wbsoJaargang = wbsoJaargang;
	}

	public int getAantalAanvragen() {
		return aantalAanvragen;
	}


	/**
	 * Calculates the RDA forfait using two tranches
	 */
	public void calculateRdaForfait() {
		float rdaFirstLeft  = MainActivity.getJaargangen().get(wbsoJaargang).getRdaGrensEersteSchijf();
		for (int i = 0; i < aantalAanvragen; i++) {
			float rdaForfaitFirst;
			float rdaForfaitSecond = 0;
			if (aanvraagPeriodes[i].getHours() >= rdaFirstLeft) {
				rdaForfaitFirst = rdaFirstLeft;
				rdaForfaitSecond = aanvraagPeriodes[i].getHours() - rdaFirstLeft;
				rdaFirstLeft = 0;
			}
			else {
				rdaForfaitFirst = aanvraagPeriodes[i].getHours();
				rdaFirstLeft -= aanvraagPeriodes[i].getHours();
			}
			aanvraagPeriodes[i].setRdaForfait(rdaForfaitFirst * MainActivity.getJaargangen().get(wbsoJaargang).getRdaBedragEersteSchijf() + rdaForfaitSecond * MainActivity.getJaargangen().get(wbsoJaargang).getRdaBedragTweedeSchijf());
		}
	}
	
	/**
	 * Calculates the SO using two tranches
	 */
	public void calculateSO() {
		float soFirstLeft = MainActivity.getJaargangen().get(wbsoJaargang).getSoGrensEersteSchijf();
		for (int i = 0; i < aantalAanvragen; i++) {
			float soFirst;
			float soSecond = 0;
			float soLoonkosten = aanvraagPeriodes[i].getHours() * soUurloon;
			if (!forfaitaireAanvraag) {
				soLoonkosten += aanvraagPeriodes[i].getRda();
			}
			else {
				soLoonkosten += aanvraagPeriodes[i].getRdaForfait();
			}
			if (soLoonkosten >= soFirstLeft) {
				soFirst = soFirstLeft;
				soSecond = soLoonkosten - soFirstLeft;
				soFirstLeft = 0;
			}
			else {
				soFirst = soLoonkosten;
				soFirstLeft -= soLoonkosten;
			}
			if (starter) {
				aanvraagPeriodes[i].setSo(soFirst * MainActivity.getJaargangen().get(wbsoJaargang).getSoPercentageEersteSchijfStarter() + soSecond * MainActivity.getJaargangen().get(wbsoJaargang).getSoPercentageTweedeSchijf());
			}
			else {
				aanvraagPeriodes[i].setSo(soFirst * MainActivity.getJaargangen().get(wbsoJaargang).getSoPercentageEersteSchijf() + soSecond * MainActivity.getJaargangen().get(wbsoJaargang).getSoPercentageTweedeSchijf());
			}
		}
		
	}
	
	private void setNumberOfPeriods(int numberOfPeriods) {
		this.aantalAanvragen = numberOfPeriods;
	}

	public boolean isRdaForfait() {
		return forfaitaireAanvraag;
	}

	public void setRdaForfait(boolean rdaForfait) {
		this.forfaitaireAanvraag = rdaForfait;
	}

	public int getHourlyRate() {
		return soUurloon;
	}

	public void setHourlyRate(int hourlyRate) {
		this.soUurloon = hourlyRate;
	}

	public AanvraagPeriode[] getApplicationPeriods() {
		return aanvraagPeriodes;
	}

	public void setStarter(boolean starter) {
		this.starter = starter;
	}
	
	//Parcelable
	
    private Aanvraag(Parcel in) {
        aantalAanvragen = in.readInt();
        forfaitaireAanvraag = in.readByte() != 0x00;
        starter = in.readByte() != 0x00;
        soUurloon = in.readInt();
        aanvraagPeriodes = in.createTypedArray(AanvraagPeriode.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(aantalAanvragen);
        dest.writeByte((byte) (forfaitaireAanvraag ? 0x01 : 0x00));
        dest.writeByte((byte) (starter ? 0x01 : 0x00));
        dest.writeInt(soUurloon);
        dest.writeTypedArray(aanvraagPeriodes, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Aanvraag> CREATOR = new Parcelable.Creator<Aanvraag>() {
        @Override
        public Aanvraag createFromParcel(Parcel in) {
            return new Aanvraag(in);
        }

        @Override
        public Aanvraag[] newArray(int size) {
            return new Aanvraag[size];
        }
    };

}
