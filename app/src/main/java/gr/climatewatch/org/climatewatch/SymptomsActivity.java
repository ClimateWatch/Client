package gr.climatewatch.org.climatewatch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class SymptomsActivity extends Activity {

    private MyCustomAdapter dataAdapter = null;
    private FloatingActionButton myButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomsactivity);
        //Generate list View from ArrayList
        displayListView();
        checkButtonClick();
      }

    private void displayListView() {
        //Array list of countries
        ArrayList<Symptoms> SymptomsList = new ArrayList<Symptoms>();
        Symptoms Symptoms = new Symptoms("Dyspnoe", false);
        SymptomsList.add(Symptoms);
        Symptoms = new Symptoms("Cough", false);
        SymptomsList.add(Symptoms);
        Symptoms = new Symptoms("Wheeze", false);
        SymptomsList.add(Symptoms);
        Symptoms = new Symptoms("Sputum", false);
        SymptomsList.add(Symptoms);
        Symptoms = new Symptoms("Cyanoso", false);
        SymptomsList.add(Symptoms);
        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.content_symptomsactivity, SymptomsList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Symptoms Symptoms = (Symptoms) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + Symptoms.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkButtonClick() {
        myButton = (FloatingActionButton) findViewById(R.id.fab);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");
                ArrayList<Symptoms> SymptomsList = dataAdapter.SymptomsList;
                for (int i = 0; i < SymptomsList.size(); i++) {
                    Symptoms Symptoms = SymptomsList.get(i);
                    if (Symptoms.isSelected()) {
                        responseText.append("\n" + Symptoms.getName() + " \t"+ Symptoms.getRate());
                    }
                }



                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

                finish();

            }
        });

    }






    //<---------------- INNER CLASS------------>

    private class MyCustomAdapter extends ArrayAdapter<Symptoms> {

        private ArrayList<Symptoms> SymptomsList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Symptoms> SymptomsList) {
            super(context, textViewResourceId, SymptomsList);
            this.SymptomsList = new ArrayList<Symptoms>();
            this.SymptomsList.addAll(SymptomsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.content_symptomsactivity, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.seek = (SeekBar) convertView.findViewById(R.id.seekBar);

                convertView.setTag(holder);
                final Symptoms[] Symptoms = {new Symptoms()};


                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Symptoms[0] = (Symptoms) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        Symptoms[0].setSelected(cb.isChecked());
                    }
                });

                holder.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //Toast.makeText(getApplicationContext(),seekBar.getProgress(),Toast.LENGTH_LONG);
                        System.out.println(seekBar.getProgress());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int value = seekBar.getProgress();
                        Symptoms[0].setRate(value);

                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Symptoms Symptoms = SymptomsList.get(position);
            //holder.code.setText(" (" +  Symptoms.getCode() + ")");
            holder.name.setText(Symptoms.getName());
            holder.name.setChecked(Symptoms.isSelected());
            holder.name.setTag(Symptoms);


            return convertView;
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            SeekBar seek;
        }

    }
}
