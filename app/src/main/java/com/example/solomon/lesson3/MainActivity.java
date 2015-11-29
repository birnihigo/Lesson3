package com.example.solomon.lesson3;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {

    private EditText txt_input;
    private EditText txt_output;
    private Spinner spinner_input;
    private Spinner spinner_output;
    private ArrayAdapter<CharSequence> adapter_input;
    private ArrayAdapter<CharSequence> adapter_output;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        txt_input = (EditText) findViewById(R.id.input_text);
        txt_output = (EditText) findViewById(R.id.output_text);
        spinner_input = (Spinner) findViewById(R.id.units_spinner1);
        spinner_output = (Spinner) findViewById(R.id.units_spinner2);
//        txt_input = new EditText(this);
//        txt_output = new EditText(this);
//        spinner_input = new Spinner(this);
//        spinner_output = new Spinner(this);
        adapter_input = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapter_output = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);

        spinner_input.setAdapter(adapter_input);
        spinner_input.setSelection(0);
        spinner_output.setAdapter(adapter_output);
        spinner_output.setSelection(0);
//
//        LinearLayout layout_main = new LinearLayout(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
//        layout_main.setOrientation(LinearLayout.VERTICAL);
//        layout_main.setLayoutParams(lp);
//
//        LinearLayout layout_row1 = new LinearLayout(this);
//        layout_row1.setOrientation(LinearLayout.HORIZONTAL);
//        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        LinearLayout.LayoutParams params_txt = new LinearLayout.LayoutParams(100,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams params_spinner = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        layout_row1.addView(txt_input, params_txt);
//        layout_row1.addView(spinner_input, params_spinner);
//        layout_row1.setLayoutParams(lp);
//
//        LinearLayout layout_row2 = new LinearLayout(this);
//        layout_row2.setOrientation(LinearLayout.HORIZONTAL);
//        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
//        layout_row2.setLayoutParams(lp);
//        layout_row2.addView(txt_output, params_txt);
//        layout_row2.addView(spinner_output, params_spinner);
//
//        TextView title_txt = new TextView(this);
//        title_txt.setText("Units Converter");
//        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
//        layout_main.addView(title_txt, lp);
//        layout_main.addView(layout_row1);
//        layout_main.addView(layout_row2);
//
//        setContentView(layout_main);

        // Handling the listeners
        spinner_input.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if((txt_output.getText() != null) && (!txt_output.getText().toString().equals(""))) {
                    Double tmp = Double.parseDouble(txt_output.getText().toString());
                    int position_item2 = spinner_output.getSelectedItemPosition();
                    double result = convert(tmp, position_item2, position);
                    txt_input.setText("" + result);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //we do nothing here
            }

        });
        spinner_output.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if((txt_input.getText() != null)  && (!txt_input.getText().toString().equals(""))){
                    Double tmp = Double.parseDouble(txt_input.getText().toString());
                    int position_item2 = spinner_input.getSelectedItemPosition();
                    double result = convert(tmp, position_item2, position);
                    txt_output.setText("" + result);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //we do nothing here
            }

        });
    }

    /**
     * convert calculates a value amount of pos_input and returns it equivalent in pos_output units.
     * @param value
     * @param pos_input
     * @param pos_output
     * @return
     */
    private double convert(double value, int pos_input, int pos_output){
        double result = 0;
        if((pos_input == 0) && (pos_output == 1)){
            result = convertFromCubToTableSpoon(value);
        }
        if((pos_input == 0) && (pos_output == 2)){
            result = convertFromCubToTeaSpoon(value);
        }
        if((pos_input == 1) && (pos_output == 0)){
            result = convertFromTableSpoonToCub(value);
        }
        if((pos_input == 1) && (pos_output == 2)){
            result = convertFromTableSpoonToTeaSpoon(value);
        }
        if((pos_input == 2) && (pos_output == 0)){
            result = convertFromTeaSpoonToCub(value);
        }
        if((pos_input == 2) && (pos_output == 1)){
            result = convertFromTeaSpoonToTableSpoon(value);
        }

        return result;
    }

    private double convertFromTableSpoonToCub(double ts) {
        return ts / 16;
    }

    private double convertFromTableSpoonToTeaSpoon(double ts) {
        return 3 * ts;
    }

    private double convertFromTeaSpoonToCub(double ts) {
        return ts / 48;
    }

    private double convertFromTeaSpoonToTableSpoon(double ts) {
        return ts / 3;
    }

    private double convertFromCubToTableSpoon(double cub) {
        return 16 * cub;
    }

    private double convertFromCubToTeaSpoon(double cub) {
        return 48 * cub;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.solomon.lesson3/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
