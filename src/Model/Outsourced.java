/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Class that handles Outsourced objects
 * @author Caleb Lugo
 */
public class Outsourced extends Part {
    private String companyName;
    /**
     * Constructor that creates Outsourced objects
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName 
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * Sets the company name
     * @param name 
     */
    public void setCompanyName(String name){
        companyName = name;
    }
    
    /**
     * Returns the company name
     * @return company name
     */
    public String getCompanyName(){
        return companyName;
    }
    
}
