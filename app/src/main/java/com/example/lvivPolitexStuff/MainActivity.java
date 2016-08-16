package com.example.lvivPolitexStuff;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lviv.PolitexStuff.R;


public  class  MainActivity extends Activity implements OnClickListener,android.view.View.OnClickListener {

	TextView tvTitle, tvID, tvSubjectName, tvMArk, tvCoef, tvSQLShow, tvChange,tvGoInformation;
	ImageView iAdda, iEdit, iDelet, iCount, iList, iChage;
	CastomDialogFactory castDialog;
	BLL bll = new BLL(this);
	
	final static int DIALOG_CHANGE = 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater mni = getMenuInflater();
		mni.inflate(R.menu.menu_main, menu);

		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializate();
		try {
			showDbData();
		} catch (Exception e) {

			showDbData();
		}
		tvSQLShow.setTextColor(Color.RED);

	}
	
	public MainActivity(){tvMArk=null;}
	private void initializate() {
	// ///////====
	
	tvTitle = (TextView) findViewById(R.id.tvTitle);
	tvID = (TextView) findViewById(R.id.tvID);
	tvSubjectName = (TextView) findViewById(R.id.tvSubjectName);
	tvMArk = (TextView) findViewById(R.id.tvMark);
	tvCoef = (TextView) findViewById(R.id.tvCoef);
	tvSQLShow = (TextView) findViewById(R.id.tvSQLShow);
	

	// ========
	iAdda = (ImageView) findViewById(R.id.iAdd);
	iEdit = (ImageView) findViewById(R.id.iEdit);
	iDelet = (ImageView) findViewById(R.id.iDelete);
	iCount = (ImageView) findViewById(R.id.iCount);
	iList = (ImageView) findViewById(R.id.iListItems);
	iChage = (ImageView) findViewById(R.id.iChange);

	// ====
	iAdda.setOnClickListener(this);
	iEdit.setOnClickListener(this);
	iDelet.setOnClickListener(this);
	iCount.setOnClickListener(this);
	iList.setOnClickListener(this);
	iChage.setOnClickListener(this);

	
	// =-====
	tvID.setTextColor(Color.RED);
	tvTitle.setTextColor(Color.BLACK);

	// ==========================
	
}

@SuppressWarnings("deprecation")
public void onClick(View v) {

	switch (v.getId()) {
	case R.id.iAdd:
		iCount.setEnabled(true);
		castDialog = new CastomDialogFactory(this, 1);

		castDialog.show();

		break;
	case R.id.iEdit:
		iCount.setEnabled(true);
		castDialog = new CastomDialogFactory(this, 2);
		castDialog.show();
		break;
	case R.id.iDelete:
		iCount.setEnabled(true);
		castDialog = new CastomDialogFactory(this, 0);
		castDialog.show();
		break;
	case R.id.iCount:
		Count();
		iCount.setEnabled(false);

		break;
	case R.id.iListItems:
		iCount.setEnabled(true);
		castDialog = new CastomDialogFactory(this, 3);
		castDialog.show();

		break;

	case R.id.iChange:

		showDialog(DIALOG_CHANGE);

		break;
	
		

	default:
		break;

	}

}

public void showDbData() {

	String dataID = "id \n";
	String dataNAME = "Назва предмету \n";
	String dataMARK = "Бал \n";
	String dataFACTOR = "Коефіціент \n";
	bll.open();
	for (int i = 0; i < bll.getID().size(); i++) {
		dataID += bll.getID().get(i) + "\n";
		dataNAME += bll.getSubjectName().get(i) + "\n";
		dataMARK += " " + bll.getMarks().get(i) + "\n";
		dataFACTOR += "\t" + bll.getFactor().get(i) + "\n";
	}
	bll.close();

	tvID.setText(dataID);
	tvSubjectName.setText(dataNAME);
	tvMArk.setText(dataMARK);
	tvCoef.setText(dataFACTOR);
	if (IsNoData())
		tvSQLShow
				.setText("Щоб створити шаблон-стукніть по блискавці");

}

public boolean IsNoData() {

	bll.open();
	if (bll.getID().size() > 0) {
		bll.close();
		return false;// перевырка чи э даны в базы!
	} else {
		bll.close();
		return true;
	}

}

public void Count() {

	if (IsNoData()) {
		tvSQLShow.setText("Створіть для початку сесію");
		showDbData();
	} else {
		double count = 0;
		double rez = 0;
		double sumkof = 0;
		bll.open();
		for (int i = 0; i < bll.getMarks().size(); i++) {
			sumkof += Double.parseDouble(bll.getFactor().get(i));
			count += Double.parseDouble(bll.getFactor().get(i))
					* Double.parseDouble(bll.getMarks().get(i));// тут
																// рахуються
																// кофи!!!
		}
		bll.close();
		rez = count / sumkof;
		if (rez >= 71) {
			tvSQLShow.setTextColor(Color.BLUE);
			tvSQLShow.setTextSize(20);
			tvSQLShow.setText(String.format("Середній бал = %.2f:)",rez));
		} else {
			tvSQLShow.setTextColor(Color.RED);
			tvSQLShow.setTextSize(20);
			tvSQLShow.setText(String.format("Середній бал = %.2f:(",rez));
		}
		showDbData();

	}
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.aboutUs:
		Intent i = new Intent(MainActivity.this, About.class);
		startActivity(i);
		break;
	case R.id.exit:
		finish();
		break;
	case R.id.deleteAll:
		castDialog = new CastomDialogFactory(this, 4);
		castDialog.show();
		break;
	default:
		break;
	}
	return false;
}

@SuppressWarnings("deprecation")
@Override
protected Dialog onCreateDialog(int id) {
	if (id == DIALOG_CHANGE) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);

		adb.setTitle("Відкрити конвертер валют?");

		adb.setMessage("Для отримання актуального курсу валют,буде викоритано інтернет трафік!Використовується курс валют ПриватБанку");

		adb.setIcon(android.R.drawable.ic_dialog_info);

		adb.setPositiveButton("продовжити", this);

		adb.setNegativeButton("відмінити", this);

		return adb.create();
	}
	return super.onCreateDialog(id);
}

public void onClick(DialogInterface dialog, int which) {
	switch (which) {

	case Dialog.BUTTON_POSITIVE:
		dialog.dismiss();
		// ShowChange();
		castDialog = new CastomDialogFactory(this, 5);
		castDialog.show();
		break;
	case Dialog.BUTTON_NEGATIVE:
		dialog.dismiss();
		break;

	}

}

	
}


