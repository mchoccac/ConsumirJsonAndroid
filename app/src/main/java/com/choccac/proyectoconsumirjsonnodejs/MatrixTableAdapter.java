package com.choccac.proyectoconsumirjsonnodejs;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;


public class MatrixTableAdapter<T> extends BaseTableAdapter{


	private final static int WIDTH_DIP = 110;
	private final static int HEIGHT_DIP = 32;

	private final Context context;
	private LayoutInflater inflater;

	private T[][] table;

	private final int width;
	private final int height;

	public MatrixTableAdapter(Context context) {
		this(context, null);
		inflater = LayoutInflater.from(context);
	}

	public MatrixTableAdapter(Context context, T[][] table) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		Resources r = context.getResources();

		width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_DIP, r.getDisplayMetrics()));
		height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DIP, r.getDisplayMetrics()));

//		width = r.getDimensionPixelSize(R.dimen.table_width);
//		height = r.getDimensionPixelSize(R.dimen.table_height);

		setInformation(table);
	}

	public void setInformation(T[][] table) {
		this.table = table;
	}

	@Override
	public int getRowCount() {
		return table.length - 1;
	}

	@Override
	public int getColumnCount() {
		return table[0].length - 1;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_table, parent, false);
		}
		convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
		((TextView) convertView.findViewById(android.R.id.text1)).setText(table[row + 1][column + 1].toString());
		return convertView;
	}

	@Override
	public int getHeight(int row) {
		return height;
	}

	@Override
	public int getWidth(int column) {
		return width;
	}

	@Override
	public int getItemViewType(int row, int column) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

}
