package co.udea.entity;

import java.io.Serializable;

import co.udea.expdesign.entity.ItemLatinSquare;

public class ModelData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[][] matrix;
	private int model;
	private String[] rowNames;
	private String[] columnsNames;
 	private String confidence;
 	private ItemLatinSquare[][] itemMatrix;
 	
 	
	public ModelData(double[][] matrix, int model, String[] rowNames,
			String[] columnsNames, String confidence) {
		super();
		this.matrix = matrix;
		this.model = model;
		this.rowNames = rowNames;
		this.columnsNames = columnsNames;
		this.confidence = confidence;
	}


	public ModelData(double[][] matrix, int model, String confidence) {
		super();
		this.matrix = matrix;
		this.model = model;
		this.confidence = confidence;
	}


	public ModelData(double[][] matrix, int model) {
		super();
		this.matrix = matrix;
		this.model = model;
	}
	
	public ModelData(ItemLatinSquare[][] itemMatrix, int model) {
		super();
		this.itemMatrix = itemMatrix;
		this.model = model;
	}


	public double[][] getMatrix() {
		return matrix;
	}


	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}


	public int getModel() {
		return model;
	}


	public void setModel(int model) {
		this.model = model;
	}


	public String[] getRowNames() {
		return rowNames;
	}


	public void setRowNames(String[] rowNames) {
		this.rowNames = rowNames;
	}


	public String[] getColumnsNames() {
		return columnsNames;
	}


	public void setColumnsNames(String[] columnsNames) {
		this.columnsNames = columnsNames;
	}


	public String getConfidence() {
		return confidence;
	}


	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}


	public ItemLatinSquare[][] getItemMatrix() {
		return itemMatrix;
	}


	public void setItemMatrix(ItemLatinSquare[][] itemMatrix) {
		this.itemMatrix = itemMatrix;
	}
 	
 	
	
}
