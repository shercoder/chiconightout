package com.example.mapswithfragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class drinksListItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <ArrayList <HashMap < String, String > > > drinksList;

	public drinksListItem(ArrayList <ArrayList <HashMap < String, String > > > _drinksList) {
		this.drinksList = _drinksList;
	}
	
	public ArrayList <ArrayList <HashMap < String, String > > > getList() {
		return this.drinksList;
	}
}
