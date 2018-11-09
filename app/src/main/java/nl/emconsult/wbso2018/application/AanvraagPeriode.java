package nl.emconsult.wbso2018.application;

import android.os.Parcel;
import android.os.Parcelable;

public class AanvraagPeriode implements Parcelable {

	private float hours;
	private float rda;
	private float rdaForfait;
	private float so;

	public AanvraagPeriode(int hours) {
		this.hours = hours;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public float getRda() {
		return rda;
	}

	public void setRda(float rda) {
		this.rda = rda;
	}

	public float getRdaForfait() {
		return rdaForfait;
	}

	public void setRdaForfait(float rdaForfait) {
		this.rdaForfait = rdaForfait;
	}

	public float getSo() {
		return so;
	}

	public void setSo(float so) {
		this.so = so;
	}


	//Parcelable
    protected AanvraagPeriode(Parcel in) {
        hours = in.readFloat();
        rda = in.readFloat();
        rdaForfait = in.readFloat();
        so = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(hours);
        dest.writeFloat(rda);
        dest.writeFloat(rdaForfait);
        dest.writeFloat(so);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AanvraagPeriode> CREATOR = new Parcelable.Creator<AanvraagPeriode>() {
        @Override
        public AanvraagPeriode createFromParcel(Parcel in) {
            return new AanvraagPeriode(in);
        }

        @Override
        public AanvraagPeriode[] newArray(int size) {
            return new AanvraagPeriode[size];
        }
    };


}
