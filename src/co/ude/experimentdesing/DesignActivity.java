package co.ude.experimentdesing;

import java.util.StringTokenizer;

import co.udea.entity.ModelData;
import co.udea.expdesign.entity.ItemLatinSquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DesignActivity extends Activity {

	private Spinner spinnerModels;
	private TextView textColumns, textRows, textMatrix;
	private EditText editColumns, editRows, editMatrix;
	private Button buttonAnalysis;

	private String[] modelArray;

	private int modelSelected = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_design);

		getComponents();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.design, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getComponents() {
		spinnerModels = (Spinner) findViewById(R.id.spinnerModel);

		textColumns = (TextView) findViewById(R.id.textColumnNames);
		textRows = (TextView) findViewById(R.id.textRowNames);
		textMatrix = (TextView) findViewById(R.id.textMatrix);

		editColumns = (EditText) findViewById(R.id.editColumnNames);
		editRows = (EditText) findViewById(R.id.editRowNames);
		editMatrix = (EditText) findViewById(R.id.editMatrix);

		buttonAnalysis = (Button) findViewById(R.id.buttonDoAnalysis);

		modelArray = getResources().getStringArray(R.array.factorialModels);

		ArrayAdapter<String> adaptadorSpinnerModel = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, modelArray);
		spinnerModels.setAdapter(adaptadorSpinnerModel);

		addListeners();
	}

	private void addListeners() {
		spinnerModels.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				visibilityForModel(position);
				modelSelected = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		InputFilter[] filters = new InputFilter[1];

		filters[0] = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				if (end > start) {

					char[] acceptedChars = new char[] { 'A', 'B', 'C', 'D',
							'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
							'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
							'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
							'8', '9', '-', '.', ';', ',', ' ', '=', '\n' };

					for (int index = start; index < end; index++) {
						if (!new String(acceptedChars).contains(String
								.valueOf(source.charAt(index)))) {
							return "";
						}
					}
				}
				return null;
			}
		};

		editMatrix.setFilters(filters);
	}

	public void visibilityForModel(int model) {

		textRows.setVisibility(View.VISIBLE);
		editRows.setVisibility(View.VISIBLE);

		textMatrix.setVisibility(View.VISIBLE);
		editMatrix.setVisibility(View.VISIBLE);

		textMatrix.setVisibility(View.VISIBLE);

		buttonAnalysis.setVisibility(View.VISIBLE);

		switch (model) {
		case 0:
			textRows.setVisibility(View.GONE);
			editRows.setVisibility(View.GONE);
			textColumns.setVisibility(View.GONE);
			editColumns.setVisibility(View.GONE);
			textMatrix.setVisibility(View.GONE);
			editMatrix.setVisibility(View.GONE);
			buttonAnalysis.setVisibility(View.GONE);

		case 1:
			textColumns.setVisibility(View.GONE);
			editColumns.setVisibility(View.GONE);

			textRows.setText(getResources().getString(R.string.textTreatments));
			editRows.setHint(getResources().getString(
					R.string.editTreatmentsHint));

			editMatrix.setHint(getResources()
					.getString(R.string.editMatrixHint));

			break;

		case 2:
			textColumns.setVisibility(View.VISIBLE);
			editColumns.setVisibility(View.VISIBLE);

			textRows.setText(getResources().getString(R.string.textTreatments));
			editRows.setHint(getResources().getString(
					R.string.editTreatmentsHint));

			textColumns.setText(getResources().getString(R.string.textBlocks));
			editColumns.setHint(getResources().getString(
					R.string.editBlocksHint));

			editMatrix.setHint(getResources()
					.getString(R.string.editMatrixHint));

			break;

		case 3:
			textColumns.setVisibility(View.VISIBLE);
			editColumns.setVisibility(View.VISIBLE);

			textRows.setText(getResources().getString(R.string.textRows));
			editRows.setHint(getResources().getString(R.string.editRowsHint));

			textColumns.setText(getResources().getString(R.string.textColumns));
			editColumns.setHint(getResources().getString(
					R.string.editColumnsHint));

			editMatrix.setHint(getResources().getString(
					R.string.editMatrixHintLS));

			break;

		default:
			break;
		}

	}

	public void onClick(View view) {
		String matrixString = editMatrix.getText().toString();

		if ((!matrixString.equals(null)) && (!matrixString.equals(""))) {
			validate(matrixString);
		} else {
			Toast.makeText(getApplicationContext(), "Put some data",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void validate(String matrixString) {
		StringTokenizer treatmentsToken;

		if ((!matrixString.equals(null)) || (!matrixString.equals(""))) {
			treatmentsToken = new StringTokenizer(
					editRows.getText().toString(), ", ");
		}

		// obteining matrix

		StringTokenizer matrixRowTokens = new StringTokenizer(editMatrix
				.getText().toString(), ";\n");

		int numberOfRows = matrixRowTokens.countTokens();

		if (modelSelected != 3) {
			double[][] matrixArray = new double[numberOfRows][];

			numberOfRows = 0;
			while (matrixRowTokens.hasMoreElements()) {

				StringTokenizer rowTokenizer = new StringTokenizer(
						(String) matrixRowTokens.nextElement(), ", ");
				int rowTokens = rowTokenizer.countTokens();
				double[] rowElements = new double[rowTokens];
				rowTokens = 0;
				while (rowTokenizer.hasMoreElements()) {
					rowElements[rowTokens] = Double
							.valueOf((String) rowTokenizer.nextElement());
					rowTokens++;
				}
				matrixArray[numberOfRows] = rowElements;
				numberOfRows++;
			}

			validateMatrix(matrixArray);
		} else {
			ItemLatinSquare[][] matrixArray = new ItemLatinSquare[numberOfRows][];

			numberOfRows = 0;
			while (matrixRowTokens.hasMoreElements()) {

				StringTokenizer rowTokenizer = new StringTokenizer(
						(String) matrixRowTokens.nextElement(), ", ");
				int rowTokens = rowTokenizer.countTokens();
				ItemLatinSquare[] rowElements = new ItemLatinSquare[rowTokens];
				rowTokens = 0;
				while (rowTokenizer.hasMoreElements()) {
					StringTokenizer itemLT = new StringTokenizer(
							(String) rowTokenizer.nextElement(), "= ");
					rowElements[rowTokens] = new ItemLatinSquare(
							((String)itemLT.nextElement()).charAt(0),
							(double) Double.valueOf((String) itemLT
									.nextElement()));

					rowTokens++;
				}
				matrixArray[numberOfRows] = rowElements;
				numberOfRows++;
			}

			validateMatrix(matrixArray);
		}

	}

	private boolean isaMatrix(double matrix[][]) {
		if (matrix.length == 1) {
			return false;
		} else {
			int referenceLength = matrix[0].length;
			for (int i = 1; i < matrix.length; i++) {
				if (referenceLength != matrix[i].length) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean isaMatrix(ItemLatinSquare matrix[][]) {
		if (matrix.length == 1) {
			return false;
		} else {
			int referenceLength = matrix[0].length;
			for (int i = 1; i < matrix.length; i++) {
				if (referenceLength != matrix[i].length) {
					return false;
				}
			}
		}

		return true;
	}

	private void validateMatrix(double[][] matrixArray) {
		if (isaMatrix(matrixArray)) {
			sendData(new ModelData(matrixArray, modelSelected));
		} else {
			Toast.makeText(getApplicationContext(),
					"We don'T think that it is a matrix, be careful with the spaces after ;", Toast.LENGTH_LONG)
									.show();
		}
	}

	private void validateMatrix(ItemLatinSquare[][] matrixArray) {
		if (isaMatrix(matrixArray)) {
			sendData(new ModelData(matrixArray, modelSelected));
		} else {
			Toast.makeText(getApplicationContext(),
					"We don'T think that it is a matrix, be careful with the spaces after ; ", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void sendData(ModelData modelData) {
		Intent intent = new Intent(this, ResultsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", modelData);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
