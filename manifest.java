package com.mobilesec.santoku.avdetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	protected static Button dangerousInstalledAppsButton;
	protected static Button dangerousRunningAppsButton;
	protected ScrollView asv;
	protected static LinearLayout linearLayout;
	public static Context context;
	public static PackageManager pm;

	protected ArrayList<PackageInfo> ListOfDangerousInstalledApps;
	final int NumOfDangerousInstalledApps=100;
	protected ArrayList<PackageInfo> ListOfDangerousRunningApps;
	final int NumOfDangerousRunningApps=50;
	private static int GET_PERMISSIONS = 4096;
	final int dangerousPermissionListSize = 16;
	protected List<String> dangerousPermissionList = Arrays.asList("SEND_SMS", "RECEIVE_SMS", "RECEIVE_SMS", "READ_SMS",
			"WRITE_SMS", "RECEIVE_SMS", "RECEIVE_MMS", "PHONE_CALL", "PROCESS_OUTGOING_CALLS", "CALL_PRIVILEGED", "INTERNET",
			"READ_PHONE_STATE", "READ_CONTACTS", "READ_HISTORY_BOOKMARKS", "ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION");

	enum DangerousAppType {Installed, Running}
	int[] dangerousInstalledPermissionCnt;
	int[] dangerousRunningPermissionCnt;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);

		pm = getPackageManager();
		context = getApplicationContext();
		linearLayout = (LinearLayout) findViewById(R.id.innerLinearLayout);
		ListOfDangerousInstalledApps = new ArrayList<PackageInfo>(NumOfDangerousInstalledApps);
		ListOfDangerousRunningApps = new ArrayList<PackageInfo>(NumOfDangerousRunningApps);

		displayInstalledApps();

		processDangerousInstalledApp pdiApp = new processDangerousInstalledApp();
		dangerousInstalledAppsButton = (Button) findViewById(R.id.DangerousInstalledAppsButton);
		dangerousInstalledAppsButton.setOnClickListener(pdiApp);

		processDangerousRunningApp pdrApp = new processDangerousRunningApp();
		dangerousRunningAppsButton = (Button) findViewById(R.id.DangerousRunningAppsButton);
		dangerousRunningAppsButton.setOnClickListener(pdrApp);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}

	@Override
	public void onDestroy()
	{
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onPause() 
	{
		super.onPause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}


	private boolean isDangerousApp(PackageInfo info)
	{

		String[] pInfoList = info.requestedPermissions;
        if (pInfoList != null) {
			for (String pInfo : pInfoList) {
				for (String dPermission : dangerousPermissionList) {
					if (pInfo.contains(dPermission))
						return true;
				}

			}
		}
		return false;
	}



	public void displayInstalledApps()
	{

		int installed_cnt, running_cnt;
		String appName = "";
		List<PackageInfo> ListOfPackageApps = null;


		ActivityManager AM = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = AM.getRunningAppProcesses();

		if(context != null)
		{
			ListOfPackageApps = context.getPackageManager().getInstalledPackages(GET_PERMISSIONS);
		}

		installed_cnt=running_cnt=0;
		if (ListOfPackageApps != null) {
			for (PackageInfo pkgInfo : ListOfPackageApps) {
				if ((pkgInfo != null)&&(!checkIfSystemPackage(pkgInfo))) {
					if (isDangerousApp(pkgInfo)) {
					 for (RunningAppProcessInfo rApp : runningApps) {
						if (pkgInfo.packageName.equals(rApp.processName)) {
								ListOfDangerousRunningApps.add(pkgInfo);
								running_cnt++;
								if (running_cnt == NumOfDangerousRunningApps)
									break;
						 }
					 }

					 ListOfDangerousInstalledApps.add(pkgInfo);
					 installed_cnt++;
					 if (installed_cnt == NumOfDangerousInstalledApps)
							break;
					 }

				}
			}


			for (PackageInfo pkgInfo : ListOfPackageApps) {
				if (pkgInfo != null) {
					appName =  pkgInfo.applicationInfo.loadLabel(pm).toString();


					TextView view = new TextView(this);
					view.setText(appName);
					view.setTag(pkgInfo);
					linearLayout.addView(view);
				}
			}
		}
	}

	boolean checkIfSystemPackage(PackageInfo pkgInfo) {
		return ((pkgInfo.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) !=0);

	}

	public ArrayList<String> getAppPerm(PackageInfo pkgInfo, DangerousAppType appType)
	{
		boolean matched;
		int k;
		String appName = "";
		ArrayList<String> appPerm = new ArrayList<String>();
		appName = (String) pkgInfo.applicationInfo.loadLabel(pm);
		appPerm.add("");
		appPerm.add(appName);



		String[] permStr = pkgInfo.requestedPermissions;
		for(int i=0; i < permStr.length; i++ )
			{
				matched=false;
				k=0;
				for (String dP : dangerousPermissionList) {
					if (permStr[i].contains(dP)) {
						matched =true;
						break;
					}
					k++;
				}
				if (matched) {
					if (appType == DangerousAppType.Installed) {
						dangerousInstalledPermissionCnt[k]++;
					}
					else if (appType == DangerousAppType.Running) {
						dangerousRunningPermissionCnt[k]++;
					}
						String permElement = permStr[i];
						String perm = permElement.replaceAll("android.permission.", "");
						appPerm.add("Dangerous Permission: " + perm);

				}
			}

		return appPerm;
	}

	private class processDangerousInstalledApp implements OnClickListener
	{
		public void onClick(View v)
		{
			int i,k, maxIdx,maxCnt;
			String mostFrequentDangerousPermission;
			Intent intent = new Intent(MainActivity.this,ProcessResults.class);
			Bundle bundle = new Bundle();

			dangerousInstalledPermissionCnt = new int[dangerousPermissionListSize];


			k = 0;
			for (i=0; i<dangerousPermissionListSize; i++)
				dangerousInstalledPermissionCnt[i]=0;

			for(PackageInfo diApp : ListOfDangerousInstalledApps)
			{
				ArrayList<String> diAppPermList = getAppPerm(diApp, DangerousAppType.Installed);
				bundle.putStringArrayList("dangerousInstalledApps" + k, diAppPermList);
				k++;
			}
			maxIdx=maxCnt=0;
			for (i=0; i<dangerousPermissionListSize; i++) {
				if (maxCnt < dangerousInstalledPermissionCnt[i]) {
					maxCnt = dangerousInstalledPermissionCnt[i];
					maxIdx = i;
				}
			}
			mostFrequentDangerousPermission = dangerousPermissionList.get(maxIdx);
			bundle.putString("MostDangerousInstalledPermission", mostFrequentDangerousPermission);
			bundle.putInt("NumOfDangerousInstalledApps", ListOfDangerousInstalledApps.size());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};

	private class processDangerousRunningApp implements OnClickListener
	{
		public void onClick(View v)
		{
   			int i,k, maxIdx,maxCnt;
			String mostFrequentDangerousPermission;
			Intent intent = new Intent(MainActivity.this,ProcessResults.class);
   			Bundle bundle = new Bundle();

			dangerousRunningPermissionCnt = new int[dangerousPermissionListSize];


			k = 0;
			for (i=0; i<dangerousPermissionListSize; i++)
				dangerousRunningPermissionCnt[i]=0;

			for(PackageInfo diApp : ListOfDangerousRunningApps)
			{
				ArrayList<String> diAppPermList = getAppPerm(diApp, DangerousAppType.Running);
				bundle.putStringArrayList("dangerousRunningApps" + k, diAppPermList);
				k++;
			}
			maxIdx=maxCnt=0;
			for (i=0; i<dangerousPermissionListSize; i++) {
				if (maxCnt < dangerousRunningPermissionCnt[i]) {
					maxCnt = dangerousRunningPermissionCnt[i];
					maxIdx = i;
				}
			}
			mostFrequentDangerousPermission = dangerousPermissionList.get(maxIdx);
			bundle.putString("MostDangerousRunningPermission", mostFrequentDangerousPermission);
			bundle.putInt("NumOfDangerousRunningApps", ListOfDangerousRunningApps.size());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};


}
