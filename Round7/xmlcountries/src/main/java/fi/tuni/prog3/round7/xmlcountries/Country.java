
package fi.tuni.prog3.round7.xmlcountries;


public class Country implements Comparable<Country> {
    
    private String name;
    private double area;
    private long population;
    private double gdp;

    public Country(String name, double area, long population, double gdp) {
        this.name = name;
        this.area = area;
        this.population = population;
        this.gdp = gdp;
    }

    public String toString() {
        return String.format("%s%n  Area: %.1f km2%n  Population: %d%n"
                + "  GDP: %.1f (2015 USD)%n", name, area, population, gdp);
    }

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public long getPopulation() {
        return population;
    }

    public double getGdp() {
        return gdp;
    }   

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }
}
