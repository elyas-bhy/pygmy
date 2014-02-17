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

import com.dev.pygmy.R;
import com.dev.pygmy.navbar.NavbarAdapter.NavbarItemType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Implementation of NavbarItem used for menu entries
 * @author Pygmy
 *
 */
public class NavbarEntryItem implements NavbarItem {

	private int mIconResourceId;
	private int mTitleResourceId;

	public NavbarEntryItem(int iconResourceId, int titleResourceId) {
		mIconResourceId = iconResourceId;
		mTitleResourceId = titleResourceId;
	}

	@Override
	public int getViewType() {
		return NavbarItemType.ENTRY_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View row;
		if (convertView == null) {
			row = inflater.inflate(R.layout.navbar_list_item_entry, null);
		} else {
			row = convertView;
		}

		ImageView icon = (ImageView) row.findViewById(R.id.navbar_list_item_entry_icon);
		TextView title = (TextView) row.findViewById(R.id.navbar_list_item_entry_title);
		icon.setImageResource(mIconResourceId);
		title.setText(mTitleResourceId);
		return row;
	}

}