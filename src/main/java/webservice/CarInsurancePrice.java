/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author RENAUD
 */
@WebService(serviceName = "CarInsurancePrice")
public class CarInsurancePrice {

    private static final int JEUNE_AGE = 25;
    private static final double JEUNE_TAUX = 1.5;
    private static final double PRIX_BASE = 20;
    private static final String CARBURANT_ESSENCE = "essence";
    private static final String CARBURANT_DIESEL = "diesel";
    private static final int CARBURANT_DIESEL_PRIX = 5;
    private static final int CARBURANT_ESSENCE_PRIX = 10;
    private static final int CHEVAUX_NB_MAX = 500;
    private static final int CHEVAUX_NB_MINI = 101;
    private static final int CHEVAUX_TAUX_MAX = 5;
    private static final double CHEVAUX_TAUX_MINI = 1.1;
    private static final double ANNEE_TAUX_NEW = 1.2;
    private static final double ANNEE_TAUX_MINI = 1.5;
    private static final int ANNEE_MINI = 5;
    private static final int ANNEE_TAUX_MAX = 2;
    private static final int ANNEE_MAX = 10;

    /**
     * This is a sample web service operation
     *
     * @param dateNaissance
     * @param annee
     * @param carburant
     * @param chevaux
     * @return double
     */
    @WebMethod(operationName = "prix")
    public double prix(@WebParam(name = "date") String dateNaissance, @WebParam(name = "annee") String annee, @WebParam(name = "carburant") String carburant, @WebParam(name = "chevaux") String chevaux) {
        return calculPrix(dateNaissance, annee, carburant, chevaux);
    }

    private double calculPrix(String dateNaissance, String annee, String carburant, String chevaux) {
        double price = PRIX_BASE;
        DateTime dateJour = new DateTime();
        price = checkAge(dateNaissance, dateJour, price);
        price = checkCarburant(carburant, price);
        price = checkChevaux(chevaux, price);
        price = checkAnnee(annee, dateJour, price);
        return price;
    }

    private double checkAge(String dateNaissance, DateTime dateJour, double price) {
        DateTimeFormatter dft = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime dtNaissance = dft.parseDateTime(dateNaissance);
        Period period = new Period(dtNaissance.getMillis(), dateJour.getMillis());
        int age = period.getYears();
        if (age < JEUNE_AGE) {
            price *= JEUNE_TAUX;
        }
        return price;
    }

    private double checkCarburant(String carburant, double price) {
        if (carburant.equals(CARBURANT_ESSENCE)) {
            price += CARBURANT_ESSENCE_PRIX;
        } else if (carburant.equals(CARBURANT_DIESEL)) {
            price += CARBURANT_DIESEL_PRIX;
        }
        return price;
    }

    private double checkChevaux(String chevaux, double price) {
        int nbChevaux = Integer.parseInt(chevaux);
        if (nbChevaux < CHEVAUX_NB_MINI) {
            price *= CHEVAUX_TAUX_MINI;
        } else if (nbChevaux > CHEVAUX_NB_MAX) {
            price *= CHEVAUX_TAUX_MAX;
        } else {
            price *= (nbChevaux / 100);
        }
        return price;
    }

    private double checkAnnee(String annee, DateTime dateJour, double price) {
        int year = dateJour.getYear() - Integer.parseInt(annee);
        if(year > ANNEE_MAX){
            price *= ANNEE_TAUX_MAX;
        } else if (year > ANNEE_MINI){
            price *= ANNEE_TAUX_MINI;
        } else {
            price *= ANNEE_TAUX_NEW;
        }
        return price;
    }
}
