package co.ude.experimentdesing;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import co.udea.entity.ModelData;
import co.udea.expdesign.LatinSquare;
import co.udea.expdesign.OneFactor;
import co.udea.expdesign.RandomizedBlocks;
import co.udea.expdesign.entity.ItemMeanComparison;
import co.udea.extras.Repository;

public class ResultsActivity extends Activity {

	TextView tableTreatmentSS, tableTreatmentGL, tableTreatmentMS,
			tableColumnSS, tableColumnGL, tableColumnMS, tableRowSS,
			tableRowGL, tableRowMS,tableErrorSS,tableErrorGL, tableErrorMS,
			tableTotalSS, tableTotalGL, 
			tableF0, tableValueP, textValidHypothesis, textCompareMeans, textLSDvalue;
	TableRow tableRowColumns, tableRowRows;

	ModelData modelData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		getComponents();
		getData();
	}

	public void getComponents() {
		tableTreatmentSS = (TextView) findViewById(R.id.tableTreatmentSS);
		tableTreatmentGL = (TextView) findViewById(R.id.tableTreatmentGL);
		tableTreatmentMS = (TextView) findViewById(R.id.tableTreatmentMS);
		tableColumnSS = (TextView) findViewById(R.id.tableColumnSS);
		tableColumnGL = (TextView) findViewById(R.id.tableColumnGL);
		tableColumnMS = (TextView) findViewById(R.id.tableColumnMS);
		tableRowSS = (TextView) findViewById(R.id.tableRowSS);
		tableRowGL = (TextView) findViewById(R.id.tableRowGL);
		tableRowMS = (TextView) findViewById(R.id.tableRowMS);
		tableErrorSS  = (TextView) findViewById(R.id.tableErrorSS);
		tableErrorGL  = (TextView) findViewById(R.id.tableErrorGL);
		tableErrorMS  = (TextView) findViewById(R.id.tableErrorMS);
		tableTotalSS = (TextView) findViewById(R.id.tableTotalSS);
		tableTotalGL = (TextView) findViewById(R.id.tableTotalGL);
		
		tableF0 = (TextView) findViewById(R.id.tableF0);
		tableValueP = (TextView) findViewById(R.id.tableValueP);
		
		tableRowColumns = (TableRow) findViewById(R.id.tableRowColumns);
		tableRowRows = (TableRow) findViewById(R.id.tableRowRows);
		
		textValidHypothesis = (TextView) findViewById(R.id.textValidHypothesis);
		textCompareMeans = (TextView) findViewById(R.id.textCompareMeans);
		textLSDvalue = (TextView) findViewById(R.id.textLSDvalue);
	}

	public void getData() {

		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();

			if (!bundle.isEmpty()) {
				modelData = (ModelData) bundle.getSerializable("data");
				analizeMatrix();
			} else {
				Toast.makeText(getApplicationContext(), "no hay Bundle",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "no hay Intent",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void analizeMatrix() {
		switch (modelData.getModel()) {
		case 1:
			setResultsOneFactor(new OneFactor(modelData.getMatrix(),"95"));
			Toast.makeText(getApplicationContext(),modelData.getMatrix().length+ "x"+ modelData.getMatrix()[0].length , Toast.LENGTH_LONG).show();
			break;
		case 2:
			setResultsBlocks(new RandomizedBlocks(modelData.getMatrix(),"95"));
			break;
		case 3:
			setResultsLatin(new LatinSquare(modelData.getItemMatrix(),"95"));
			break;
		default:
			break;
		}

	}

	private void setResultsOneFactor(OneFactor oneFactor){
		
		validHypothesis(oneFactor.isValidHypothesis());
		compareMeans(oneFactor.getMeansDifferences(), oneFactor.getLsdValue());
		
		HashMap<Integer, Double> anovaHash = oneFactor.getAnovaHash();
		
		
		tableTreatmentSS.setText(String.valueOf(anovaHash.get(OneFactor.SStrattos)));
		tableTreatmentGL.setText(String.valueOf(anovaHash.get(OneFactor.GLtrattos)));
		tableTreatmentMS.setText(String.valueOf(anovaHash.get(OneFactor.MStrattos)))
		;
		tableErrorSS.setText(String.valueOf(anovaHash.get(OneFactor.SSerror)));
		tableErrorGL.setText(String.valueOf(anovaHash.get(OneFactor.GLerror)));
		tableErrorMS.setText(String.valueOf(anovaHash.get(OneFactor.MSerror)));
		
		tableTotalSS.setText(String.valueOf(anovaHash.get(OneFactor.SStotals)));
		tableTotalGL.setText(String.valueOf(anovaHash.get(OneFactor.GLtotals)));
		
		tableF0.setText(String.valueOf(anovaHash.get(OneFactor.F0)));
		tableValueP.setText(String.valueOf(anovaHash.get(OneFactor.valueP)));
	}
	
	private void setResultsBlocks(RandomizedBlocks randomizedBlocks){
		tableRowColumns.setVisibility(View.VISIBLE);
		validHypothesis(randomizedBlocks.isValidHypothesis());
		compareMeans(randomizedBlocks.getMeansDifferences(), randomizedBlocks.getLsdValue());
		
		HashMap<Integer, Double> anovaHash = randomizedBlocks.getAnovaHash();
		
		tableTreatmentSS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.SStrattos)));
		tableTreatmentGL.setText(String.valueOf(anovaHash.get(RandomizedBlocks.GLtrattos)));
		tableTreatmentMS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.MStrattos)));
		
		tableErrorSS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.SSerror)));
		tableErrorGL.setText(String.valueOf(anovaHash.get(RandomizedBlocks.GLerror)));
		tableErrorMS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.MSerror)));
		
		tableTotalSS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.SStotals)));
		tableTotalGL.setText(String.valueOf(anovaHash.get(RandomizedBlocks.GLtotals)));
		
		tableColumnSS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.SSblocks)));
		tableColumnGL.setText(String.valueOf(anovaHash.get(RandomizedBlocks.GLblocks)));
		tableColumnMS.setText(String.valueOf(anovaHash.get(RandomizedBlocks.MSblocks)));
		
		tableF0.setText(String.valueOf(anovaHash.get(RandomizedBlocks.F0)));
		tableValueP.setText(String.valueOf(anovaHash.get(RandomizedBlocks.valueP)));
	}
	
	private void setResultsLatin(LatinSquare latinSquare){
		
		validHypothesis(latinSquare.isValidHypothesis());
		compareMeans(latinSquare.getMeansDifferences(), latinSquare.getLsdValue());
		tableRowColumns.setVisibility(View.VISIBLE);
		tableRowRows.setVisibility(View.VISIBLE);
		HashMap<Integer, Double> anovaHash = latinSquare.getAnovaHash();
		
		tableTreatmentSS.setText(String.valueOf(anovaHash.get(LatinSquare.SStrattos)));
		tableTreatmentGL.setText(String.valueOf(anovaHash.get(LatinSquare.GLtrattos)));
		tableTreatmentMS.setText(String.valueOf(anovaHash.get(LatinSquare.MStrattos)));
		
		tableErrorSS.setText(String.valueOf(anovaHash.get(LatinSquare.SSerror)));
		tableErrorGL.setText(String.valueOf(anovaHash.get(LatinSquare.GLerror)));
		tableErrorMS.setText(String.valueOf(anovaHash.get(LatinSquare.MSerror)));
		
		tableTotalSS.setText(String.valueOf(anovaHash.get(LatinSquare.SStotals)));
		tableTotalGL.setText(String.valueOf(anovaHash.get(LatinSquare.GLtotals)));
		
		tableRowSS.setText(String.valueOf(anovaHash.get(LatinSquare.SSrows)));
		tableRowGL.setText(String.valueOf(anovaHash.get(LatinSquare.GLrows)));
		tableRowMS.setText(String.valueOf(anovaHash.get(LatinSquare.MSrows)));
		
		tableColumnSS.setText(String.valueOf(anovaHash.get(LatinSquare.SScolumns)));
		tableColumnGL.setText(String.valueOf(anovaHash.get(LatinSquare.GLcolumns)));
		tableColumnMS.setText(String.valueOf(anovaHash.get(LatinSquare.MScolumns)));
		
		tableF0.setText(String.valueOf(anovaHash.get(LatinSquare.F0)));
		tableValueP.setText(String.valueOf(anovaHash.get(LatinSquare.valueP)));
	}
	
	private void validHypothesis(boolean validity){
		textValidHypothesis.setVisibility(View.VISIBLE);
		
		if(validity){
			textValidHypothesis.setText(getResources().getString(R.string.validHyphotesis) + " 95%");
		}else{
			textValidHypothesis.setText(getResources().getString(R.string.notValidHyphotesis) + " 95%");
		}
	}
	
	
	private void compareMeans(ItemMeanComparison[] totalComparisons, double lsdValue){
		textLSDvalue.setText(getResources().getString(R.string.lsdValue) + " "
							+String.valueOf(Repository.round(lsdValue,2)));
		
		String label = "";
 		for (int i = 0; i < totalComparisons.length; i++) {
			 label += "| Treament" + (totalComparisons[i].getFirstTreatment()+1) + " - Treatment" + 
		(totalComparisons[i].getSecondTreatment()+1) + " | = "
					 + Repository.round(totalComparisons[i].getDifferenceAbs(), 2);
			if(totalComparisons[i].isValid()){
				label += " *";
			}
			
			label+="\n";
		}
		
		textCompareMeans.setText(label);
	}
}
