package com.example.lvivPolitexStuff;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;
import com.lviv.PolitexStuff.R;


//клас анімацї 
public class Splash extends View {

	Bitmap picture;
	float cPictureY,cPictureX,cTitleTextX,cTitleTextY;
	
	public Splash(Context context)
	{
		super(context);//при успадкуванні View треба виставляти в конструкторі !!!!
		
		picture = BitmapFactory.decodeResource(getResources(), R.drawable.logotup);
		
	    cPictureY=cPictureX=cTitleTextX=cTitleTextY=0;
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE);
		
		Paint titleText= new Paint();
		titleText.setARGB(100, 3, 19, 140);
		titleText.setTextAlign(Align.CENTER);
		titleText.setTextSize(50);
		//===============
		
		Paint logoText= new Paint();
		logoText.setARGB(100, 0, 0, 0);
		logoText.setTextAlign(Align.CENTER);
		logoText.setTextSize(30);
		//=============
		
		//canvas.drawText("Rovenskyy develop", canvas.getWidth()/2, 300, titleText);
		//canvas.drawText("persistence is the key to success", canvas.getWidth()/2, 340, logoText);
		
		if (canvas.getHeight()<=800){//це для пристроїв з розширенням 480*800(lg p990(4дюйми) наприклад)
		
		canvas.drawBitmap(picture, 20, cPictureY, null);
	
		if(cPictureY<canvas.getHeight()/2-canvas.getHeight()/6){
			
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
			cPictureY+=40;
			invalidate();
		}
		else
		{
			cPictureY=canvas.getHeight()/2-canvas.getHeight()/6;
		}
		//invalidate();
		}
		
		
	else if(canvas.getHeight()>800 && canvas.getHeight()<=1280){//це для пристроїв з розширення 800*1280(google nexus 7 (7-дюймів) наприклад)
		
		canvas.drawBitmap(picture, canvas.getWidth()/2-175, cPictureY, null);
		if(cPictureY<canvas.getHeight()/2){
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
				cPictureY+=60;
				invalidate();
			}
			else
			{
				cPictureY=canvas.getHeight()/2;
			}
	}	
		
	}

	

	
}