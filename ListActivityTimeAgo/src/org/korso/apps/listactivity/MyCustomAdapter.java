package org.korso.apps.listactivity;

import org.korso.apps.listactivity.time.RelativeTimeTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomAdapter extends ArrayAdapter<String> {
	 
	  private final Context context;
	  private final String[] list;
     // public final String[] Colors = { "Blue", "Green", "Black" };
		 
	  
	  public MyCustomAdapter(Context context, int textViewResourceId, String[] list) {
	   super(context, textViewResourceId, list);
	   
	   this.context = context;
	    this.list = list;
	  }
	 
	  @Override
	     public View getView(int position, View convertView, ViewGroup parent) {
	             
	      View row = convertView;
	 
	      if (row == null) {
	       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		   //getLayoutInflater();
	       //row = inflater.inflate(R.layout.list_row, parent, false);
	       row= inflater.inflate(R.layout.my_list_row_layout, parent, false);
	       
	      }
	      final int itemPosition = position;
	       
	      TextView listItem = (TextView) row.findViewById(R.id.label );
	      listItem.setText(list[position]);
	      
	      RelativeTimeTextView tvTimestamp = (RelativeTimeTextView) row.findViewById(R.id.timeStamp);
	      tvTimestamp.setReferenceTime(System.currentTimeMillis()-50000); //we put the time less 50 seconds
	      
	      ImageView image = (ImageView) row.findViewById(R.id.imageView1);
	      image.setImageResource(R.drawable.android_blue_water_0_peque);
	       
	      Button btn = (Button) row.findViewById(R.id.btnColor);
	      btn.setOnClickListener( new View.OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
	    		  Toast.makeText(context, "Toast from button"+ list[itemPosition], Toast.LENGTH_SHORT).show();     
	    	  }
	      });
	           
	      row.setOnClickListener(new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
	        Toast.makeText(context, list[itemPosition], Toast.LENGTH_SHORT).show();
	       }     
	      });
	      return row;
	     }
	 }