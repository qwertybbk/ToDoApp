package com.mobilesec.santoku.avdetector;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProcessResults extends Activity {
	private Button back_Button;
	private ArrayList<ArrayList<String>> dangerousInstalledPermissionLists;
	private ArrayList<ArrayList<String>> dangerousRunningPermissionLists;
	String mostFrequentDangerousInstalledPermission;
	String mostFrequentDangerousRunningPermission;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process_results);

		Bundle appBundle = this.getIntent().getExtras();

		if (appBundle != null) {

            if (appBundle.getInt("NumOfDangerousInstalledApps") != 0) {
				mostFrequentDangerousInstalledPermission = appBundle.getString("MostDangerousInstalledPermission");
				dangerousInstalledPermissionLists = new ArrayList<ArrayList<String>>(10);
				int installSize = appBundle.getInt("NumOfDangerousInstalledApps");
				for (int i = 0; i < installSize; i++)
					dangerousInstalledPermissionLists.add(appBundle.getStringArrayList("dangerousInstalledApps" + i));

				showDangerousPermissions(dangerousInstalledPermissionLists, mostFrequentDangerousInstalledPermission);

			} else if (appBundle.getInt("NumOfDangerousRunningApps") != 0) {
				mostFrequentDangerousRunningPermission = appBundle.getString("MostDangerousRunningPermission");
				dangerousRunningPermissionLists = new ArrayList<ArrayList<String>>(10);
				int runningSize = appBundle.getInt("NumOfDangerousRunningApps");
				for (int i = 0; i < runningSize; i++)
					dangerousRunningPermissionLists.add(appBundle.getStringArrayList("dangerousRunningApps" + i));
                showDangerousPermissions(dangerousRunningPermissionLists, mostFrequentDangerousRunningPermission);
			}
		}
		back_Button = (Button) findViewById(R.id.backButton);
		OnClickListener backListener = new backButtonClicker();
		back_Button.setOnClickListener(backListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.process_results_menu, menu);
		return true;
	}

	
	public void showDangerousPermissions(ArrayList<ArrayList<String>> dPLists, String mostFrequentDangerousPermission) {
		for (int i = 0; i < dPLists.size(); i++) {
			ArrayList<String> dPList = dPLists.get(i);
			for (int k = 0; k < dPList.size(); k++) {
				TextView view = new TextView(this);
				view.setText(dPList.get(k));

				LinearLayout layout = (LinearLayout) findViewById(R.id.ResultsLinearLayout);
				layout.addView(view);
			}
		}
		TextView view = new TextView(this);
		view.setText("Most Frequent Dangerous Permission: " + mostFrequentDangerousPermission);
		LinearLayout layout = (LinearLayout) findViewById(R.id.ResultsLinearLayout);
		layout.addView(view);
	}

	private class backButtonClicker implements OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ProcessResults.this, MainActivity.class);
			startActivity(intent);
		}
	}
};
