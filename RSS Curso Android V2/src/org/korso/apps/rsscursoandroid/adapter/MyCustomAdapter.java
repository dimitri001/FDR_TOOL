package org.korso.apps.rsscursoandroid.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.korso.apps.rsscursoandroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomAdapter extends ArrayAdapter<HashMap<String, String>> {
	 
	  private final Context context;
	  private final ArrayList< HashMap<String, String>> data;
	  private int layoutResourceId;
	 
	  private static int layout = R.layout.my_list_row_layout;
	 // private static String[] list = { "Blue", "Green", "Black" };

	  
     // public final String[] Colors = { "Blue", "Green", "Black" };
		 
	  
	  public MyCustomAdapter(Context context, int layoutResourceId, ArrayList< HashMap<String, String>> data) {
		  super(context, layoutResourceId, data);	   
		  this.context = context;
		  this.data=data;
		  this.layoutResourceId = layoutResourceId;
	  }
	
	  @Override
	     public View getView(int position, View convertView, ViewGroup parent) {
	             
	      View row = convertView;
	 
	      if (row == null) {
	       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	       row= inflater.inflate(layoutResourceId, parent, false);
	       
	      }
	      final int itemPosition = position;
	       
	      TextView  tvHeader = (TextView) row.findViewById(R.id.header);
	      tvHeader.setText(data.get(position).get("header"));
	      
	      TextView tvParagraph = (TextView) row.findViewById(R.id.text);
	      tvParagraph.setText(data.get(position).get("paragraph"));
	      
	      TextView tvDate = (TextView) row.findViewById(R.id.date);
	      tvDate.setText(data.get(position).get("date"));
	      
	      ImageView image = (ImageView) row.findViewById(R.id.row_image);
	      
	      //image.setImageResource(R.drawable.android_robot_peque);
	      //Comprobar R.drawable.data.get(position).get("image");
	      	       
	      /*
	      row.setOnClickListener(new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
	        Toast.makeText(context, list[itemPosition], Toast.LENGTH_SHORT).show();
	       }     
	      });
	      
	      */
	      return row;
	     }
	  
	  
	 }