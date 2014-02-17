/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.pygmy.navbar;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Custom ListView adapter responsible for handling navbar menu items
 * @author Pygmy
 *
 */
public class NavbarAdapter extends ArrayAdapter<NavbarItem> {

	private List<NavbarItem> mItems;
	private LayoutInflater mLayoutInflater;

	public enum NavbarItemType {
		ENTRY_ITEM, SEPARATOR_ITEM;
	}

	public NavbarAdapter(Activity context, List<NavbarItem> items) {
		super(context, 0, items);
		mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItems = items;
	}

	@Override
	public int getViewTypeCount() {
		return NavbarItemType.values().length;
	}

	@Override
	public int getItemViewType(int position) {
		return mItems.get(position).getViewType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mItems.get(position).getView(mLayoutInflater, convertView);
	}

}