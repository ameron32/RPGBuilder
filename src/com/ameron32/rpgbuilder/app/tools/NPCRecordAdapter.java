package com.ameron32.rpgbuilder.app.tools;

import com.ameron32.rpgbuilder.npcrecord.NPCRecord;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NPCRecordAdapter extends ArrayAdapter<NPCRecord> {

	Context context;
	int layoutResourceId;
	NPCRecord data[] = null;

	public NPCRecordAdapter(Context context, int layoutResourceId,
			NPCRecord[] data) {
		super(context, layoutResourceId, data);
		initialize(context, layoutResourceId, data);
	}

	private void initialize(Context context, int layoutResourceId,
			NPCRecord data[]) {
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		InfoHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new InfoHolder();
			holder.txtName = (TextView) row.findViewById(R.id.tvMyName);
			holder.txtCost = (TextView) row.findViewById(R.id.tvMyCost);
			holder.txtJob = (TextView) row.findViewById(R.id.tvMyJob);
			holder.txtNote = (TextView) row.findViewById(R.id.tvMyNote);
			holder.bDelete = (Button) row.findViewById(R.id.bDeleteMe);
			holder.bDelete.setTag(new ViewTag(position, "Delete", "Button",
					"ListViewRow"));
			holder.bDelete.setOnClickListener((OnClickListener) context);
			holder.bSearchButton = (ImageButton) row.findViewById(R.id.ibCharacterIcon);
			holder.bSearchButton.setTag(new ViewTag(position, "Upgrade", "Button", 
					"ListViewRow"));
			holder.bSearchButton.setOnClickListener((OnClickListener) context);
			holder.rlSelect = (RelativeLayout) row.findViewById(R.id.rlSelectMe);
			holder.rlSelect.setTag(new ViewTag(position, "Select", "RelativeLayout",
					"ListViewRow"));
			holder.rlSelect.setOnClickListener((OnClickListener) context);

			row.setTag(holder);
		} else {
			holder = (InfoHolder) row.getTag();
		}

		NPCRecord record = data[position];
		holder.txtName.setText(record.getMyName());
		if (record.getVer() == record.getStatRecord().getVer()) {
			holder.txtCost.setText(record.getMyCost());
		} else {
			holder.txtCost.setText("-1");
		}
		holder.txtJob.setText("("
				+ record.getStatRecord().getGender().name().substring(0, 1)
				+ ") " + record.getMyJob());
		holder.txtNote.setText(record.getMyNote());

		return row;
	}

	public static class InfoHolder {
		TextView txtName;
		TextView txtJob;
		TextView txtNote;
		TextView txtCost;
		Button bDelete;
		ImageButton bSearchButton;
		RelativeLayout rlSelect;
	}

}