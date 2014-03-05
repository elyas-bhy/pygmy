package com.dev.pygmy;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCreator extends ArrayAdapter<String>{
	private final Activity current_act;
	private final String[] game_name;
	private final String[] games_info;
	private final Integer[] game_icon;
	
	
	public ListCreator(Activity context,String[] web, Integer[] game_icon,String[] games_infos) {
			super(context, R.layout.game_list_item, web);
			this.current_act = context;
			this.game_name = web;
			this.game_icon = game_icon;
			this.games_info=games_infos;
			
	}
	
	
	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		LayoutInflater inflater = current_act.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.game_list_item, null, true);
		
		//Getting view ids
		TextView game_name_text = (TextView) rowView.findViewById(R.id.game_name);
		TextView game_info_text = (TextView) rowView.findViewById(R.id.game_dev_descr);
		ImageView game_icon_image = (ImageView) rowView.findViewById(R.id.game_icon);
		
		//setting infos on views
		game_name_text.setText(game_name[pos]);	
		game_icon_image.setImageResource(game_icon[pos]);
		game_info_text.setText(games_info[pos]);
		return rowView;
	}
}