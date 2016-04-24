package gr.climatewatch.org.climatewatch;

import java.io.Serializable;

/**
 * Created by dimitristomaras on 23/04/16.
 */
public class Symptoms implements  Serializable{

    private String name;
    private int rate;
    private boolean isSelected;

    private static long id = -1L;
    public Symptoms() {
    }

    public Symptoms(String name, int rate, boolean isSelected) {
        this.name = name;
        this.rate = rate;
        this.isSelected = isSelected;
    }

    public Symptoms(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
        this.rate = 0;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
