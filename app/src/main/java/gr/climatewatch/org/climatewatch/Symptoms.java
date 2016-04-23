package gr.climatewatch.org.climatewatch;

/**
 * Created by dimitristomaras on 23/04/16.
 */
public class Symptoms {

    String name;
    boolean isSelected;


    public Symptoms(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
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
