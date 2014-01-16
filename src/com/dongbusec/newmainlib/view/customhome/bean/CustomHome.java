package com.dongbusec.newmainlib.view.customhome.bean;

import java.util.ArrayList;

public class CustomHome {
	String celltype;
	ArrayList<Item> items = null;

	public String getCelltype() {
		return celltype;
	}

	public void setCelltype(String celltype) {
		this.celltype = celltype;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	

	public static class Item {
		String rect;
		String type;
		String moduletype;
		String itemcode;

		public String getRect() {
			return rect;
		}

		public void setRect(String rect) {
			this.rect = rect;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getModuletype() {
			return moduletype;
		}

		public void setModuletype(String moduletype) {
			this.moduletype = moduletype;
		}

		public String getItemcode() {
			return itemcode;
		}

		public void setItemcode(String itemcode) {
			this.itemcode = itemcode;
		}

	}

}