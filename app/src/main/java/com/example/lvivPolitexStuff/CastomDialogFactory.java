package com.example.lvivPolitexStuff;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.lviv.PolitexStuff.R;

public class CastomDialogFactory implements OnClickListener, TextWatcher,OnItemSelectedListener {
	int position1,position2;//змінні для отримання позицій листів з назвами валют
	double Ebuy,ESale,$Buy,$Sale; //курси
	String[] paths = { "Гривня", "Доллар", "Євро" };
	Spinner spinner1,spiner2;
	private static final String URLQery = "https://privat24.privatbank.ua/p24/accountorder?oper=prp&PUREXML&exchange&country=ua";//запит до приватБанку
	public EditText edEditID, edEditName, edEditMark, edEditFactor, nameADD,markADD, factorADD, deleteID,edList,edConvertSum;
	private TextView tvConvertTitle,tvConvertRezalt;
	private ImageView deleteYes, deleteNO, EditEgree, EditDissegree, YesAdd,NoADD,ListYes,ListNo,deleteALLYES,DeleteALLNO,imConvertor;
	private Dialog dialog;
	private Activity activity;
	private BLL bll;
	private String temp = "";
	private long l = 0;
	Thread th;
	TextWatcher tw;//для нагляду за полем конвертера
     
	public CastomDialogFactory(Activity activity, int dialogType) {// пять
																	// параметра
																	// можна
																	// передавати
																	// в
																	// конструктор
																	// 0-діалог
		this.activity = activity;                                 // видалення,1-дыалолг
		Ebuy=ESale=$Buy=$Sale=0;                                                  // додаванян,2-дыалог,3 діалог швидкого додавання
		bll = new BLL(activity);
		// редагування
		
		switch (dialogType) {
		case 0:
			initDelete();
			break;
		case 1:
			initAdd();
			break;
		case 2:
			initEdit();
			break;
		case 3:
			initListItems();
			break;
		case 4:
			InitDeleteAll();
			break;
		case 5:
			initConvert();
			break;
			
			
			
		default:
			break;
		}
		/*
		if (dialogType == 0)
			initDelete();
		else if (dialogType == 1)
			initAdd();
		else if (dialogType == 2)
			initEdit();
		else if (dialogType == 3)
			initListItems();
		else  if (dialogType==4)
			InitDeleteAll();
		else if (dialogType==5){
			initConvert();
		}
		*/	

	}


    
    	private void initConvert(){
    		
    		dialog = new Dialog(activity);
    		dialog.setTitle("Конвертер валют");
    		dialog.setContentView(R.layout.converter);
    		
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, paths);
    		
    		tvConvertTitle = (TextView) dialog.findViewById(R.id.tvConvertTitle);
    		spinner1 = (Spinner)dialog.findViewById(R.id.spFirst);
    		spiner2 = (Spinner)dialog.findViewById(R.id.spSecond);
    		tvConvertRezalt=(TextView)dialog.findViewById(R.id.tvConvertRezalt);
    		edConvertSum = (EditText)dialog.findViewById(R.id.edConvertSum);
    		imConvertor=(ImageView)dialog.findViewById(R.id.imConverter);
    		imConvertor.setOnClickListener(this);
    		spinner1.setAdapter(adapter);
    		spinner1.setSelection(1);//міняю при старті на долар
    		spinner1.setOnItemSelectedListener(this);
    		spiner2.setAdapter(adapter);
    		spiner2.setOnItemSelectedListener(this);	
    		
			
    		new ParsMoney().execute(URLQery);
    		 tw = new TextWatcher() {//створює обєкт якийц слідкує за зміною тексту
				
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					 position1 = spinner1.getSelectedItemPosition();//беруться значення ліста1
		    		 position2 = spiner2.getSelectedItemPosition();
		    		
		    			 
		    		 if(edConvertSum.length()>0){
		    			//======== початок блока перевірок напряму
		    		 
		    		 	if(position1==1 && position2==0 ){
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=$Buy;
						tvConvertRezalt.setText(String.format("%.2fгрн", temp));
					}
					else if (position1==2 && position2==0)
					{
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=Ebuy;
						tvConvertRezalt.setText(String.format("%.2fгрн", temp));
					}
					else if(position1==0 && position2==1)
					{
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=1/$Sale;
						tvConvertRezalt.setText(String.format("%.2f$", temp));
					}
					else if(position1==0 && position2==2)
					{
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=1/ESale;
						tvConvertRezalt.setText(String.format("%.2fEuro", temp));
					}
					else if(position1==1 && position2==2)
					{
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=$Buy/ESale;
						tvConvertRezalt.setText(String.format("%.2fEuro", temp));
					}
					else if(position1==2 && position2==1)
					{
						double temp=0;
						temp=Double.parseDouble(edConvertSum.getText().toString());
						temp*=Ebuy/$Sale;
						tvConvertRezalt.setText(String.format("%.2f$", temp));
					}
					else{
						tvConvertRezalt.setText("Змініть Напрям");//в параметрі передавати %s бо буде еррор
	    			 }
					//=============тут кінець блока перевірок
				}
		    		 else{
		    			 tvConvertRezalt.setText("");
		    		 }
		    		 
				}
				
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			};
    		edConvertSum.addTextChangedListener(tw);//підписуюсь на обєкс слідкування змін у текстовому полі!!створений вище!
    }
	private void InitDeleteAll() {
		// TODO Auto-generated method stub
		dialog = new Dialog(activity);
		dialog.setTitle("\tВидалити все?");
		dialog.setContentView(R.layout.dialogdeleteall);
		// ===
		// ======
		deleteALLYES = (ImageView) dialog.findViewById(R.id.iDeleteAllYEs);
		DeleteALLNO = (ImageView) dialog.findViewById(R.id.iDeleteAllNo);
		// ===
		deleteALLYES.setOnClickListener(this);
		DeleteALLNO.setOnClickListener(this);
	}

	private void initListItems() {
		dialog = new Dialog(activity);
		dialog.setTitle("Швидко створити сесію");
		dialog.setContentView(R.layout.dialoglis);
		// ===
		edList = (EditText) dialog.findViewById(R.id.edList);
		// ======
		ListYes = (ImageView) dialog.findViewById(R.id.iListYes);
		ListNo = (ImageView) dialog.findViewById(R.id.iListNo);
		// ===
		ListNo.setOnClickListener(this);
		ListYes.setOnClickListener(this);
		
	}

	private void initDelete() {
		dialog = new Dialog(activity);
		dialog.setTitle("Видалити ");
		dialog.setContentView(R.layout.dialogdelete);
		// ===
		deleteID = (EditText) dialog.findViewById(R.id.edDeletID);
		// ======
		deleteYes = (ImageView) dialog.findViewById(R.id.DeleteYes);
		deleteNO = (ImageView) dialog.findViewById(R.id.DleteNO);
		// ===
		deleteNO.setOnClickListener(this);
		deleteYes.setOnClickListener(this);

	}

	private void initAdd() {

		dialog = new Dialog(activity);
		dialog.setTitle("Додати");
		dialog.setContentView(R.layout.dialogaddcoolstuff);
		// ===========
		nameADD = (EditText) dialog.findViewById(R.id.AddName);
		markADD = (EditText) dialog.findViewById(R.id.AddMark);
		factorADD = (EditText) dialog.findViewById(R.id.AddFactor);
		// ==
		YesAdd = (ImageView) dialog.findViewById(R.id.AddYes);
		NoADD = (ImageView) dialog.findViewById(R.id.AddNo);

		// =====
		YesAdd.setOnClickListener(this);
		NoADD.setOnClickListener(this);
		// =============

	}

	private void initEdit() {
		// TODO Auto-generated method stub
		dialog = new Dialog(activity);
		dialog.setTitle("Редагувати");
		dialog.setContentView(R.layout.dialogedit);

		edEditID = (EditText) dialog.findViewById(R.id.edEditID);
		edEditName = (EditText) dialog.findViewById(R.id.edEditName);
		edEditMark = (EditText) dialog.findViewById(R.id.edEditMark);
		edEditFactor = (EditText) dialog.findViewById(R.id.edEditFactor);
		// =====
		EditEgree = (ImageView) dialog.findViewById(R.id.EditAgree);
		EditDissegree = (ImageView) dialog.findViewById(R.id.EditDessegree);
		// ======
		EditEgree.setEnabled(false);// ныколи не ініціалізуувати в конструкторі
									// бо бля буде нулл пойнтер ексепшин я на ту
									// байду 3 години потратьив!!\\\\\
		// ===обробка
		edEditID.addTextChangedListener(this);
		EditEgree.setOnClickListener(this);//
		EditDissegree.setOnClickListener(this);

	}

	public void show() {
		dialog.show();

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.AddYes:
			
			if (nameADD.getText().length() > 0&& markADD.getText().length() > 0 && factorADD.getText().length() > 0) {

				// ======
				try
				{
			    l = Long.parseLong(markADD.getText().toString());
				bll.open();
				bll.creatEntry(nameADD.getText().toString(), markADD.getText().toString() , factorADD.getText().toString());
				bll.close();
				Toast to = Toast.makeText(activity,"Запис успішно занесений в базу даних",Toast.LENGTH_SHORT);
				to.show();
				dialog.dismiss();
				restartA();
				}
				catch(Exception e)
				{
					Toast to1 = Toast.makeText(activity,e.toString(),Toast.LENGTH_LONG);
					to1.show();
					bll.close();
				}

			} else {
				Toast to = Toast.makeText(activity,"Помилка!Були заповнені не всі поля",Toast.LENGTH_LONG);
				to.show();
			}
			break;
		case R.id.AddNo:
			dialog.dismiss();
			break;

		case R.id.EditAgree:
			l = Long.parseLong(edEditID.getText().toString());
			bll.open();
			bll.updeteEntry(l, edEditName.getText().toString(), edEditMark.getText().toString(), edEditFactor.getText().toString());
			bll.close();
			Toast to = Toast.makeText(activity,"Дані успішно редаговані",Toast.LENGTH_SHORT);
			to.show();
			dialog.dismiss();
			restartA();
			break;
		case R.id.EditDessegree:dialog.dismiss();break;
		
		case R.id.DeleteYes:
			if(deleteID.getText().length()>0)
			{	
			l = Long.parseLong(deleteID.getText().toString());
			try{
			 bll.open();
			 bll.deleteEntry(l);
			 bll.close();
			 Toast t2 = Toast.makeText(activity,"Дані успішно видалені",Toast.LENGTH_SHORT);
				t2.show();
				restartA();;
				
			}
			catch(Exception e)
			{
				Toast t1 = Toast.makeText(activity,"Помилка!Можливо некоректне id",Toast.LENGTH_LONG);
				t1.show();
			}
			}
			else{
				Toast too = Toast.makeText(activity,"Помилка!Ви не ввели id",Toast.LENGTH_LONG);
				too.show();
			}
			break;
		case R.id.DleteNO:
			dialog.dismiss();
			

			break;
		case R.id.iListYes:
			
			if(edList.getText().length()>0)
			{
				l=Long.parseLong(edList.getText().toString());
				if(l<=16)//перевырка на символи
				{
				activity.deleteDatabase(BLL.getDatabaseName());
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				bll.open();
				for(int i =1;i<=l;i++)
				bll.creatEntry(String.format("Предмет%d", i),"71","4");
				bll.close();
				Toast too = Toast.makeText(activity,"Нову сесію успішно створено",Toast.LENGTH_SHORT);
				too.show();
				restartA();
				}
				else{
					Toast too = Toast.makeText(activity,"Помилка!Введено быльше 16 предметів",Toast.LENGTH_LONG);
					too.show();
				}
			}
			else{
				Toast too = Toast.makeText(activity,"Помилка!Ви не вказали число предметів",Toast.LENGTH_LONG);
				too.show();
			}

			break;
		case R.id.iListNo:
			dialog.dismiss();

			break;
		case R.id.iDeleteAllYEs:
			activity.deleteDatabase(BLL.getDatabaseName());
			restartA();
			
			break;
         case R.id.iDeleteAllNo:
			 dialog.dismiss();
			break;
         case R.id.imConverter:
        	 position1 = spinner1.getSelectedItemPosition();//беруться значення ліста1
    		 position2 = spiner2.getSelectedItemPosition();
    		 
    		 if(position1!=position2){
    			 spinner1.setSelection(position2);
    			 spiner2.setSelection(position1);
    			 tw.onTextChanged(null, 0, 0, 0);
    			 Toast too = Toast.makeText(activity,"Напрям змінено",Toast.LENGTH_SHORT);
 				 too.show();
    		 }
        	 
        	 
			break;
		default:
			break;
		}
	}
	private  void restartA(){
		 th = new Thread(){//тут стартує новий прочес і перезапускає нову актівіті ,так як я не знайшов кращого способо оновити дані!=((треба оптимізувати!
				public void run(){
				Intent id = new Intent("com.lvivPolitexStaff.Main");                                     
				activity.startActivity(id);
				activity.finish();}};
				th.start();
	}

	public void afterTextChanged(Editable s) {

	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			temp = edEditID.getText().toString();
			// temp.trim();
			l = Long.parseLong(temp);
			try {
				bll.open();
				edEditName.setText(bll.getSubjectName(l));
				edEditMark.setText(bll.getMarks(l));
				edEditFactor.setText(bll.getFactor(l));
				EditEgree.setEnabled(true);
				bll.close();//finally походу не так працює як в С#,треба закривати базу в трай!!Я добре не розібрався з синтаксисом джава!!дає курсор ексепшин!

			} catch (Exception e) {
				edEditName.setText(null);
				edEditMark.setText(null);
				edEditFactor.setText(null);
			} finally {
				bll.close();
			}
		} else if (s.length() == 0) {
			EditEgree.setEnabled(false);
			edEditName.setText(null);
			edEditMark.setText(null);
			edEditFactor.setText(null);
		}

	}



	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
			tw.onTextChanged(null, 0, 0, 0);//викликаю метод зміни текстового поля таким чином логіка повторюється автоматично!
	}



	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	//клас успадковується від AsyncTask так як інтернет зєднення проходить в іншому процесі!
	public class ParsMoney extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			
			try{
				
				

			    	URL url = new URL(params[0]);//get запит у браезері API приватбанку!!!обовязково перевірити в браузері які є атрибути!
			    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			    	DocumentBuilder db = dbf.newDocumentBuilder();//створення парсера
			    	Document doc = db.parse(new InputSource(url.openStream()));
			    	doc.getDocumentElement().normalize();
			    //==========================

			        NodeList nodeList = doc.getElementsByTagName("exchangerate");//тут створюється колекція з вказаними вузлами ,якщо не працює перевіряти відповідь в браузері
			        
			        //===============
			        Node temp, n,atr$Sale,atr$Buy,atrEurSale,atrEurBuy=null;//змінні для розпарсювання
			        NamedNodeMap attribut=null;//дял парсингу атрибутів конкретної строки
			        
			        
			        
			        for(int i=0;i<nodeList.getLength();i++){
			        	
			        	n = nodeList.item(i);
			        	attribut=n.getAttributes();
			        	temp=attribut.getNamedItem("ccy");
			        	if(temp.getNodeValue().equals("EUR")){
			        		 atrEurBuy  = attribut.getNamedItem("buy");
			        		 atrEurSale  = attribut.getNamedItem("sale");
			        		 
			        		 Ebuy = Double.parseDouble(atrEurBuy.getNodeValue());//заноситься значення	
			        ESale=Double.parseDouble(atrEurSale.getNodeValue());
			        	}
			        	else if(temp.getNodeValue().equals("USD")){
			        		
			        		atr$Buy  = attribut.getNamedItem("buy"); 
						     atr$Sale  = attribut.getNamedItem("sale");
			        		
			        $Buy = Double.parseDouble(atr$Buy.getNodeValue());//заноситься значення	
			        $Sale=Double.parseDouble(atr$Sale.getNodeValue());
			        	}
			             
			        }
			        
			        
			        //===============
			        /*старий код!НЕ буВ Універсальним,так як приватбанк часто міняє відповідь api 
			         * 
			         * 
			         * 
			         * 
			    	Node node = nodeList.item(1);//тут вибирається конкретний вузол з колекції!дивитисьт номер вузла в браузері(!!!інакше буде ексепшин)
			    	NamedNodeMap attributes = node.getAttributes();
			    	//=================================
			    	Node atrEurBuy  = attributes.getNamedItem("buy"); //получаєм значення атрибута buy  і sale для євро
			    	Node atrEurSale  = attributes.getNamedItem("sale");
			    	
			    	
			    	Ebuy = Double.parseDouble(atrEurBuy.getNodeValue());//заноситься значення	
			        ESale=Double.parseDouble(atrEurSale.getNodeValue());
			        //============
			        node=nodeList.item(2);//міняєм вузол на вузол доллара
			        NamedNodeMap attributes1 = node.getAttributes();//створюэм новий нодмап і беремо атрибути!
			        Node atr$Buy  = attributes1.getNamedItem("buy"); //получаєм значення атрибута buy  і sale для баксиків=)
			    	Node atr$Sale  = attributes1.getNamedItem("sale");
			    	$Buy = Double.parseDouble(atr$Buy.getNodeValue());//заноситься значення	
			        $Sale=Double.parseDouble(atr$Sale.getNodeValue());
			        */
			       return "$|"+String.format("Купівля: %.2f", $Buy)+String.format(",Продаж: %.2f", $Sale)+
			    		  "\nE|"+String.format("Купівля: %.2f", Ebuy)+String.format(",Продаж: %.2f", ESale);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				return null;
			}
			
		      
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			if(result==null)
			{
		     Toast t = Toast.makeText(activity, "Помилка!Перевірте інтернет зєднання,або перевірте наявність нової версії", Toast.LENGTH_LONG);
		     t.show();
			  dialog.dismiss();	
			  
			}
			tvConvertTitle.setText(result);
		}


		

	}

}