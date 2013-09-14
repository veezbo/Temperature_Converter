package com.vibhor.android.temperature;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private EditText text;
	private RadioButton cButton, fButton, kButton;
	private Spinner spinner;
	private ArrayAdapter<String> spinnerAdapter;
	
	private static final String CELSIUS = "\u2103";
	private static final String FAHRENHEIT = "\u2109";
	private static final String KELVIN = "K";
	private static final String EMPTY = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		text = (EditText) findViewById(R.id.textField);
		cButton = (RadioButton) findViewById(R.id.cRadio);
		fButton = (RadioButton) findViewById(R.id.fRadio);
		kButton = (RadioButton) findViewById(R.id.kRadio);
		spinner = (Spinner) findViewById(R.id.temp_selection);
		spinner.setOnItemSelectedListener(this);
		spinnerAdapter = (ArrayAdapter<String>) spinner.getAdapter();
		spinner.setSelection(spinnerAdapter.getPosition(EMPTY));
	}
	
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.calculate:
			String input = text.getText().toString();
			if (input.length() == 0) {
				notifyError("Please Enter a Number");
				return;
			}
			
			double val = Double.MIN_VALUE;
			try {
				val = Double.parseDouble(input);
			}
			catch (NumberFormatException nfe) {
				notifyError("Please Enter a Valid Number");
				return;
			}
			if (val == Double.MIN_VALUE || Double.isNaN(val)) {
				notifyError("Please Enter a Valid Number");
				return;
			}
			
			boolean succeeded = true;
			if (cButton.isChecked() && spinner.getSelectedItem().equals(FAHRENHEIT)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(fahrenheitToCelsius(val))));
				spinner.setSelection(spinnerAdapter.getPosition(CELSIUS));
			}
			else if (cButton.isChecked() && spinner.getSelectedItem().equals(KELVIN)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(kelvinToCelsius(val))));
				spinner.setSelection(spinnerAdapter.getPosition(CELSIUS));
			}
			else if (fButton.isChecked() && spinner.getSelectedItem().equals(CELSIUS)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(celsiusToFahrenheit(val))));
				spinner.setSelection(spinnerAdapter.getPosition(FAHRENHEIT));
			}
			else if (fButton.isChecked() && spinner.getSelectedItem().equals(KELVIN)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(kelvinToFahrenheit(val))));
				spinner.setSelection(spinnerAdapter.getPosition(FAHRENHEIT));
			}
			else if (kButton.isChecked() && spinner.getSelectedItem().equals(CELSIUS)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(celsiusToKelvin(val))));
				spinner.setSelection(spinnerAdapter.getPosition(KELVIN));
			}
			else if (kButton.isChecked() && spinner.getSelectedItem().equals(FAHRENHEIT)) {
				text.setText(String.valueOf(new DecimalFormat("#.#").format(fahrenheitToKelvin(val))));
				spinner.setSelection(spinnerAdapter.getPosition(KELVIN));
			}
			else {
				notifyError("Select Your Current Temperature Scale, and the One you Want to Convert To");
				succeeded = false;
			}
			
			if (succeeded) {
				clearRadio();
				moveCursorToEnd();
			}
			break;
			
		case R.id.clear:
			clearAll();
			break;		
		
		case R.id.button1:
			appendNumber(1);
			break;
		
		case R.id.button2:
			appendNumber(2);
			break;
			
		case R.id.button3:
			appendNumber(3);
			break;
			
		case R.id.button4:
			appendNumber(4);
			break;
			
		case R.id.button5:
			appendNumber(5);
			break;
			
		case R.id.button6:
			appendNumber(6);
			break;
			
		case R.id.button7:
			appendNumber(7);
			break;
		
		case R.id.button8:
			appendNumber(8);
			break;
			
		case R.id.button9:
			appendNumber(9);
			break;
			
		case R.id.button0:
			appendNumber(0);
			break;
		
		case R.id.delete:
			delete();
			break;
		
		}
		
	}
	
	public void onRadioClicked(View view) {
		
//		switch(view.getId()) {
//		
//		case R.id.cRadio:
//			spinner.setSelection(spinnerAdapter.getPosition(FAHRENHEIT));
//			break;
//			
//		case R.id.fRadio:
//			spinner.setSelection(spinnerAdapter.getPosition(CELSIUS));
//			break;
//			
//		}
	}
	
	private void clearAll() {
		text.setText("");
		clearAllButText();
	}
	private void clearAllButText() {
		((RadioGroup) findViewById(R.id.temp_radios)).clearCheck();
		spinner.setSelection(spinnerAdapter.getPosition(EMPTY));
	}
	private void clearRadio() {
		((RadioGroup) findViewById(R.id.temp_radios)).clearCheck();
	}
	
	private void notifyError(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	private double fahrenheitToCelsius(double val) {
		return 5/9. * (val - 32);
	}
	private double kelvinToCelsius(double val) {
		return val - 273.;
	}
	private double celsiusToFahrenheit(double val) {
		return 9/5. * val + 32.;
	}
	private double kelvinToFahrenheit(double val) {
		return celsiusToFahrenheit(kelvinToCelsius(val));
	}
	private double celsiusToKelvin(double val) {
		return val + 273.;
	}
	private double fahrenheitToKelvin(double val) {
		return celsiusToKelvin(fahrenheitToCelsius(val));
	}
	
	private void appendNumber(int digit) {
		int start = Math.max(text.getSelectionStart(), 0);
		int end = Math.max(text.getSelectionEnd(), 0);
		text.getText().replace(Math.min(start, end), Math.max(start, end), String.valueOf(digit), 0, 1);
	}
	
	private void delete() {
		int index = Math.max(text.getSelectionStart(), 0);
		if (index == 0) return;
		StringBuilder input = new StringBuilder (text.getText().toString());
		if (input.length() == 0) return;
		if (input.length() == 1) {
			text.setText("");
			return;
		}
		input.deleteCharAt(index-1);
		text.setText(input);
		text.setSelection(index-1);
	}
	
	private void moveCursorToEnd() {
		text.setSelection(text.getText().length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		
//		String text = (String) parent.getItemAtPosition(pos);
		
//		if (text.equals(CELSIUS)) {
//			fButton.setChecked(true);
//		}
//		else if (text.equals(FAHRENHEIT)) {
//			cButton.setChecked(true);
//		}
//		else if (text.equals(KELVIN)) {
//			kButton.setChecked(true);
//		}
//		else {
//			//DO NOTHING
//		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg) {}

}
