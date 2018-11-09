package nl.emconsult.wbso2018.application;

/**
 * Created by mwbou on 23-8-2017.
 */

public class WbsoJaargang {

    private int jaar;
    private int rdaGrensEersteSchijf;
    private int rdaBedragEersteSchijf;
    private int rdaBedragTweedeSchijf;
    private int soGrensEersteSchijf;
    private float soPercentageEersteSchijf;
    private float soPercentageEersteSchijfStarter;
    private float soPercentageTweedeSchijf;

    public WbsoJaargang(int jaar){
        this.jaar = jaar;
    }

    public void setupRDA(int rdaGrensEersteSchijf, int rdaPercentageEersteSchijf, int rdaPercentageTweedeSchijf) {
        this.rdaGrensEersteSchijf = rdaGrensEersteSchijf;
        this.rdaBedragEersteSchijf = rdaPercentageEersteSchijf;
        this.rdaBedragTweedeSchijf = rdaPercentageTweedeSchijf;
    }

    public void setupSO(int soGrensEersteSchijf, float soPercentageEersteSchijf, float soPercentageEersteSchijfStarter, float soPercentageTweedeSchijf) {
        this.soGrensEersteSchijf = soGrensEersteSchijf;
        this.soPercentageEersteSchijf = soPercentageEersteSchijf;
        this.soPercentageEersteSchijfStarter = soPercentageEersteSchijfStarter;
        this.soPercentageTweedeSchijf = soPercentageTweedeSchijf;
    }

    public int getRdaGrensEersteSchijf() {
        return rdaGrensEersteSchijf;
    }

    public int getRdaBedragEersteSchijf() {
        return rdaBedragEersteSchijf;
    }

    public int getRdaBedragTweedeSchijf() {
        return rdaBedragTweedeSchijf;
    }

    public int getSoGrensEersteSchijf() {
        return soGrensEersteSchijf;
    }

    public float getSoPercentageEersteSchijf() {
        return soPercentageEersteSchijf;
    }

    public float getSoPercentageEersteSchijfStarter() {
        return soPercentageEersteSchijfStarter;
    }

    public float getSoPercentageTweedeSchijf() {
        return soPercentageTweedeSchijf;
    }

}
