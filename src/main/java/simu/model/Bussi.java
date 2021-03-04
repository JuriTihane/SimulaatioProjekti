package simu.model;

import eduni.distributions.*;


public class Bussi extends Palvelupiste{

    private int kapasiteetti;
    private int matkustajat;
    private double saapumisaika;
    private double lahtoaika;
    private static int bussienMaara = 0;



    public Bussi(int kapasiteetti){

        this.kapasiteetti = kapasiteetti;
        uusiBussi();
    }

    public static void uusiBussi(){
        bussienMaara++;
    }

    public boolean tarkistaKapasiteetti(){
        if (matkustajat <= kapasiteetti){
            return false;
        }

        return true;
    }

    public void lisaaMatkustaja(){
        if (tarkistaKapasiteetti()){
            matkustajat++;
        }
        System.out.println("Bussi täynnä");
    }

    public static int getBussienMaara (){
        return bussienMaara;
    }
}
