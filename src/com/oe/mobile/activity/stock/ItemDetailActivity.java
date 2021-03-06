/*
 * Copyright (C) 2013  stevendreamer (in github)
 * Project Location: https://github.com/stevendreamer/openerp_mobile

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * Addition: any copy of this program should keep the author name info.
 * any copy without the author info will be a pirate

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.oe.mobile.activity.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.R;
import com.oe.mobile.R.id;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.retired.Attribute;
import com.oe.mobile.retired.ItemDetailThread;
import com.oe.mobile.retired.Model;
import com.oe.mobile.service.Stock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ItemDetailActivity extends Activity {

	ListView detaillist;
	ProgressDialog dialog;
	Handler handler;
	int productId;
	
	MyTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		productId = (Integer) getIntent().getExtras().getInt("productId");
		
		detaillist = (ListView) findViewById(R.id.itemDetailList);
		// detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "�������ݣ����Ե� ��", true, true);

		task = new MyTask();
		task.execute(productId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item_detail, menu);
		return true;
	}
	
	private class MyTask extends AsyncTask<Integer, Integer, HashMap> {

		@Override
		protected void onPreExecute() {
			Log.i("ItemListPage", "onPreExecute() called");
			// dialog.show();
		}

		@Override
		protected HashMap doInBackground(Integer... params) {
			HashMap<String,Object> result = null;
			Log.i("ITEMDETAIL", "before getItemId");
			try {
				result = Stock.getItemById(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {

		}

		@Override
		protected void onPostExecute(HashMap rc) {

			setPageView(rc);
			dialog.dismiss();

		}

	}
	
	public void setPageView(HashMap rc) {
		String[] valueList=new String[rc.size()];
		Iterator iter = rc.entrySet().iterator(); 
		int i=0;
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue(); 
		    valueList[i++] = key.toString()+" : "+val.toString();
		    Log.i("VAL", key.toString()+" : "+val.toString());
		} 

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,valueList);

		detaillist.setAdapter(adapter);
	
	}
}
