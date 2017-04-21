package com.example.try_motercycle_hit_man.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.example.try_gameengine.framework.Sprite;
import com.example.try_motercycle_hit_man.utils.CommonUtil;

public class Hamster extends Sprite{
public static final int CAR_DISTANCE = 250;
	
	private boolean isCarStartFromLeft = true;
	
	public Hamster(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNeedCreateNewInstance(){
		boolean isNeedCreateNewInstance = false;
		if(isCarStartFromLeft && getX() >= CAR_DISTANCE){
			isNeedCreateNewInstance = true;
		}else if(!isCarStartFromLeft && getX() <= CommonUtil.screenWidth - CAR_DISTANCE){
			isNeedCreateNewInstance = true;
		}
		return isNeedCreateNewInstance; 
	}

	@Override
	public boolean isNeedRemoveInstance(){
		boolean isNeedRemoveInstance = false;
		if(isCarStartFromLeft && getX() >= CommonUtil.screenWidth){
			isNeedRemoveInstance = true;
		}else if(!isCarStartFromLeft && getX() <= 0){
			isNeedRemoveInstance = true;
		}
		return isNeedRemoveInstance; 
	}
	
	@Override
		public void drawSelf(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.drawSelf(canvas, paint);
			
			paint=new Paint();
			paint.setStyle(Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(this.getX(),this.getY(), 40, paint);
		}
}
