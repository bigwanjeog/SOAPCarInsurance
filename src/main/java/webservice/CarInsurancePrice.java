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
import org.joda.time.Period;

/**
 *
 * @author RENAUD
 */
@WebService(serviceName = "CarInsurancePrice")
public class CarInsurancePrice {

    private static final int AGE_JEUNE = 25;
    private static final double PRIX_BASE = 20;
    private static final String CARBURANT_ESSENCE = "essence";
    private static final String CARBURANT_DIESEL = "diesel";
    /**
     * This is a sample web service operation
     * @param dateNaissance
     * @param annee
     * @param carburant
     * @param chevaux
     * @return double
     */
    @WebMethod(operationName = "prix")
    public double prix(@WebParam(name = "date") String dateNaissance, @WebParam(name = "annee") String annee, @WebParam(name = "carburant") String carburant, @WebParam(name = "chevaux") String chevaux) {
        double price = 0;
        try {
            Date dateParsee = new SimpleDateFormat("dd/MM/yyyy").parse(dateNaissance);
            Period period = new Period(dateParsee.getTime(), new Date().getTime());
            int age = period.getYears();
            if(age < AGE_JEUNE){
                price = calculPrix(annee, carburant, chevaux) * 1.5;
            } else {
                price = calculPrix(annee, carburant, chevaux);
            }
        } catch (ParseException ex) {
            Logger.getLogger(CarInsurancePrice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return price;
        }
    }

    private double calculPrix(String annee, String carburant, String chevaux) {
        double price = PRIX_BASE;
        price = checkCarburant(carburant, price);
        price = checkChevaux(chevaux, price);
        price = checkAnnee(annee, price);
        return price;
    }

    private double checkCarburant(String carburant, double price) {
        if(carburant.equals(CARBURANT_ESSENCE)){
            price += 10;
        } else if(carburant.equals(CARBURANT_DIESEL)) {
            price += 5;
        }
        return price;
    }

    private double checkChevaux(String chevaux, double price) {
        //pourcentage vis a vis des chevaux
        return price;
    }

    private double checkAnnee(String annee, double price) {
        //Check lannee et au dessus d'une periode + cher
        return price;
    }
    
}
