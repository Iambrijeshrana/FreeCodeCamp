package com.enlightiks.utility.pojo;

import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

import org.apache.log4j.Logger;

/** 
 * 
 * @author Brijesh
 * 
 */

@SuppressWarnings({ "rawtypes", "serial", "unchecked"})
public class SortedListModel extends AbstractListModel {

	private SortedSet model;
	private static Logger log = null;
	
	public SortedListModel() {
		super();
		model = new TreeSet();
		log = Logger.getLogger(SortedListModel.class);
	}

	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		return model.toArray()[index];
	}

	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
		log.info("item added successfully.");
	}

	public void addAll(Object selected) {
		Collection items = Arrays.asList(selected);
		model.addAll(items);
		fireContentsChanged(this, 0, getSize());
		log.info("all item added successfully.");
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
		log.info("all item clered successfully.");
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		log.info("item removed successfully.");
		return removed;
	}
}