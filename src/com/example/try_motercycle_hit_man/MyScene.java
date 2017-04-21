package com.example.try_motercycle_hit_man;

import java.util.List;

import org.loon.framework.android.game.physics.LBody;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.physics.PhysicsBody;
import com.example.try_gameengine.physics.PhysicsFactory;
import com.example.try_gameengine.remotecontroller.RemoteController;
import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;
import com.example.try_gameengine.scene.AngryBirdActivity;
import com.example.try_gameengine.scene.Bird;
import com.example.try_gameengine.scene.DialogScene;
import com.example.try_gameengine.scene.EasyScene;
import com.example.try_gameengine.scene.MyRect;
import com.example.try_gameengine.utils.GameTimeUtil;
import com.example.try_motercycle_hit_man.model.Hamster;
import com.example.try_motercycle_hit_man.utils.BitmapUtil;
import com.example.try_motercycle_hit_man.utils.CommonUtil;

public class MyScene extends EasyScene{
//	private List<Sprite> fireballs = new CopyOnWriteArrayList<Sprite>();
//	Sprite player;
	private Sprite bg, bg2;
	private int move = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	boolean isPressLeftMoveBtn, isPressRightMoveBtn;
	private int gameTime;
	
	private GameTimeUtil fireballTimeUtil;
	private GameTimeUtil gameTimeUtil;

	private static final int INVALID_POINTER_ID = -1;

	private int mActivePointerId = INVALID_POINTER_ID;
	
//	private List<Sheep> sheeps = new ArrayList<Sheep>();
//	private EnemyManager enemyManager;
//	private Sprite crosshair;
	
	public MyScene(final Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		// TODO Auto-generated constructor stub
		RemoteController remoteController = RemoteController.createRemoteController();
		setRemoteController(remoteController);
		remoteController.setUpKyPosition(CommonUtil.screenWidth - remoteController.getUpKey().getWidth(), CommonUtil.screenHeight - remoteController.getUpKey().getHeight());
		remoteController.setLeftKyPosition(0, CommonUtil.screenHeight - remoteController.getUpKey().getHeight());
		remoteController.setRemoteContollerListener(new RemoteController.RemoteContollerListener() {
			
			@Override
			public void pressDown(
					List<com.example.try_gameengine.remotecontroller.RemoteController.CommandType> commandTypes) {
				// TODO Auto-generated method stub
				for(CommandType commandType : commandTypes){
					switch (commandType) {
					case UPKeyUpCommand:
						isPressRightMoveBtn = false;
						move = LEFT;
						break;
					case UPKeyDownCommand:
						isPressRightMoveBtn = true;
						move = RIGHT;			
						break;
					case LeftKeyDownCommand:
						isPressLeftMoveBtn = true;
						move = LEFT;			
						break;
					case LeftKeyUpCommand:
						isPressLeftMoveBtn = false;
						move = RIGHT;
						break;
					default:
						break;
					}
				}
			}
		});
		
		bg = new Sprite(BitmapUtil.bg, CommonUtil.screenWidth, CommonUtil.screenHeight, false);
		addAutoDraw(bg);
		bg.setPosition(0, 0);
		bg2 = new Sprite(BitmapUtil.bg, CommonUtil.screenWidth, CommonUtil.screenHeight, false);
		addAutoDraw(bg2);
		bg2.setPosition(bg.getWidth(), 0);
		Sprite flower = new Sprite(BitmapUtil.flower, BitmapUtil.flower.getWidth(), BitmapUtil.flower.getHeight(), false);
		addAutoDraw(flower);
		flower.setPosition(0, CommonUtil.screenHeight - flower.getHeight()*1.5f);
		Sprite cloud1 = new Sprite(BitmapUtil.cloud1, BitmapUtil.cloud1.getWidth(), BitmapUtil.cloud1.getHeight(), false);
		addAutoDraw(cloud1);
		cloud1.setPosition(CommonUtil.screenWidth/2.0f - cloud1.getWidth()/2.0f, 0);
		Sprite cloud2 = new Sprite(BitmapUtil.cloud2, BitmapUtil.cloud2.getWidth(), BitmapUtil.cloud2.getHeight(), false);
		addAutoDraw(cloud2);
		cloud2.setPosition(0, 0);
		Sprite cloud3 = new Sprite(BitmapUtil.cloud3, BitmapUtil.cloud3.getWidth(), BitmapUtil.cloud3.getHeight(), false);
		addAutoDraw(cloud3);
		cloud3.setPosition(CommonUtil.screenWidth - cloud1.getWidth(), 0);
		
		Hamster hamster = new Hamster(0, 0, false);
		hamster.setBitmapAndFrameWH(BitmapUtil.hamster, 150, 150);
//		hamster.
		PhysicsBody physicsBody = PhysicsFactory.bodyWithCircleOfRadius(50);
		physicsBody.setPosition(5, 5);
//		physicsBody.setVelocity(0, 10);
		hamster.setPhysicsBody(physicsBody, world);
		addAutoDraw(hamster);
//		crosshair = new Crosshair(CommonUtil.screenWidth/2, CommonUtil.screenHeight/2, false);
	}

	GameView gameview;
	
	@Override
	public GameView initGameView(Activity activity, IGameController gameController,
			IGameModel gameModel) {
		// TODO Auto-generated method stub
		
		class MyGameView extends GameView{

			public MyGameView(Context context, IGameController gameController,
					IGameModel gameModel) {
				super(context, gameController, gameModel);
				// TODO Auto-generated constructor stub
			}
			
		}
		
		return gameview = new MyGameView(activity, gameController, gameModel);
	}

	@Override
	public void process() {
		
		bg.setX(bg.getX()-5);
		bg2.setX(bg2.getX()-5);
		
		if(bg2.getX() < 0){
			bg.setX(bg2.getX());
			bg2.setX(bg.getX() + bg.getWidth());
		}
		
		tickTime();
		
		world.step(timeStep, velocityIterations,positionIterations);// ���z�@�ɶi������

		/**�M�����z�@�ɤ���body�A�N���z�@�ɼ����X���Ȧ^�X���ù��A
		 * ����bird�Mrect���Ѽ�
		 * */
//		Body body = world.getBodyList();	
		for (int i = 1; i < world.getBodyCount(); i++) {
			LBody body = world.getBodyList().get(i);
			if ((body.getUserData()) instanceof MyRect) {
				MyRect rect = (MyRect) (body.getUserData());
				rect.setX(body.getPosition().x * RATE - (rect.getWidth()/2));
				rect.setY(body.getPosition().y * RATE - (rect.getHeight()/2));
				rect.setAngle((float)(body.getAngle()*180/Math.PI));
			}
			else if ((body.getUserData()) instanceof Bird) {
					Bird bird = (Bird) (body.getUserData());
					bird.setX(body.getPosition().x * RATE );
					bird.setY(body.getPosition().y * RATE );
					bird.setAngle((float)(body.getAngle()*180/Math.PI));
			}
			else if ((body.getUserData()) instanceof Sprite) {
				Sprite bird = (Sprite) (body.getUserData());
				bird.setX(body.getPosition().x * RATE );
				bird.setY(body.getPosition().y * RATE );
//				bird.setAngle((float)(body.getAngle()*180/Math.PI));
			}
			else // body.m_userData==null�ɡA�Nbody�P���A��ܳQ����
			{
				world.destroyBody(body);
			}
//			body = body.m_next;
		}
		
		/**�o�g�p���A�B�u���@���A�o�g�L��A����A��ʤF*/
		if(bird.getIsReleased()&&!bird.getApplyForce())
		{
			/**�o�g�ɤ~�Ыؤ@��body*/
			Body birdBody=createCircle(bird.getX(),bird.getY(),bird.getR(),false);
			
			/**�]�mbullet�ݩʬ�true,�_�h�t�׹L�֮ɥi��|����V�{�H */
			birdBody.setBullet(true);
			
			/**�o�g�O�q����*/
			float forceRate=200f;
			
			/**�ھھ�ֵ����שM���׳]�m�o�g�O*/
			float angle=(float) Math.atan2(bird.getY()-AngryBirdActivity.startY,bird.getX()-AngryBirdActivity.startX);
			float forceX=-(float) (Math.sqrt(Math.pow(bird.getX()-AngryBirdActivity.startX, 2))*Math.cos(angle));
			float forceY=-(float) (Math.sqrt(Math.pow(bird.getY()-AngryBirdActivity.startY, 2))*Math.sin(angle));
		
			Vector2 force=new Vector2(forceX*forceRate,forceY*forceRate);
			
			/**��body���Χ@�ΤO */
			birdBody.applyForce(force, birdBody.getWorldCenter());

			/**�]�m�w�g�@�ιL�O�A�o�g��A����A��ʤF */
			bird.setApplyForce(true);
		}
	}
	
	private void tickTime(){
		if(gameTimeUtil.isArriveExecuteTime()){
			gameTime++;
		}
	}

	private void showGameOverDialog(){
		final Layer bgLayer = new Layer(BitmapUtil.gameover, BitmapUtil.gameover.getWidth(), BitmapUtil.gameover.getHeight(), false);
		bgLayer.setPosition(0, bgLayer.getHeight());
//		final Sprite restartButton = new Sprite(BitmapUtil.restartBtn01, 350, 200, false);
		final ButtonLayer restartButton = new ButtonLayer(0, 0, false);
		restartButton.setBitmapAndAutoChangeWH(BitmapUtil.restartBtn01);
		restartButton.setButtonBitmap(BitmapUtil.restartBtn01, BitmapUtil.restartBtn02, BitmapUtil.restartBtn01);
		restartButton.setPosition(CommonUtil.screenWidth/2.0f - restartButton.getWidth()/2.0f, CommonUtil.screenHeight/4.0f*3);
		restartButton.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				((MainActivity)context).sceneManager.previousWithExistedScenes();
			}
		});
		final LabelLayer labelLayer = new LabelLayer("hello", 0, 0, false);
		labelLayer.setTextSize(100);
		labelLayer.setPosition(500, 500);
		final DialogScene dialogScene = new DialogScene(context, "c");
		dialogScene.setDialogSceneDraw(new DialogScene.DialogSceneDrawListener() {
			
			@Override
			public void draw(Canvas canvas) {
				// TODO Auto-generated method stub
//				canvas.drawColor(Color.RED);
				canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				canvas.drawRect(new Rect(100, 100, 300, 300), paint);
				paint.setColor(Color.BLACK);
				paint.setAlpha(150);
				canvas.drawRect(new RectF(0, 0, CommonUtil.screenWidth, CommonUtil.screenHeight ), paint);
				
				bgLayer.drawSelf(canvas, null);
				
				restartButton.drawSelf(canvas, null);
				
				labelLayer.drawSelf(canvas, null);
			}
		});
		dialogScene.setDialogSceneTouchListener(new DialogScene.DialogSceneTouchListener() {
			
			@Override
			public void onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				float x = event.getX();
				float y = event.getY();
				
				LayerManager.getInstance().onTouchSceneLayers(event, dialogScene.getLayerLevel());
				

			}
		});
		dialogScene.setIsNeedToStopTheActiveScene(false);
		((MainActivity)context).sceneManager.addScene(dialogScene);
		((MainActivity)context).sceneManager.startLastScene();
		
		dialogScene.addAutoDraw(restartButton);
//		dialogScene.start();
	}
	
	public void drawEnemis(Canvas canvas){
//		enemyManager.drawEnemies(canvas);
	}
	
	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		sprite.drawSelf(canvas, null);
//		LayerManager.drawLayers(canvas, null);
		LayerManager.getInstance().drawSceneLayers(canvas, null, sceneLayerLevel);
		
		Paint paint = new Paint();
		paint.setTextSize(50);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(gameTime+"", 100, 100, paint);
//		player.drawSelf(canvas, null);
		
	}

	int count =0;
	float x = 0;
	float y = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			x = event.getX();
			y = event.getY();
			++count;
			if(count%2==1){
//				enemyManager.startMoveEnemies();
			}else{
//				Enemy enemy = enemyManager.getEnemies().get(0);
//				if(enemy.getC()!=null)
//				enemy.getC().pause();
			}
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float dx = event.getX() - x;
			float dy = event.getY() - y;
			
			x = event.getX();
			y = event.getY();
			
//			enemyManager.moveEnemies((int)dx, (int)dy);
//			crosshair.move(dx, dy);
		}
		
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			/**�p�����o�g���I����*/
			if(bird.isPressed(event)&&!bird.getIsReleased())
			{
				bird.setIsPressed(true);
			}
		}
		else if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			/**�p�����o�g�ɩ�� */
			if(bird.getIsPressed())
			{
				bird.move(event);
			}
		}
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(bird.getIsPressed())
			{
				bird.setIsReleased(true);
				bird.setIsPressed(false);
			}

		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void beginContact(Contact arg0) {
		// TODO Auto-generated method stub
		super.beginContact(arg0);
		
		
	}
	
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		super.postSolve(arg0, arg1);
		
		/**�I���ƥ��˴��A�ѼƬO�ոեX�Ӫ� */
		if(arg1.getNormalImpulses()[0]>5)
		{
			if ( (arg0.getFixtureA().getBody().getUserData())instanceof MyRect)
			{

				MyRect rect=(MyRect)(arg0.getFixtureA().getBody().getUserData());

				/**�u���o�X�������|�Q���� */
				if(rect.getType()==Type.stone
				||rect.getType()==Type.wood
				||rect.getType()==Type.pig
				||rect.getType()==Type.glass)
				{
					arg0.getFixtureA().getBody().setUserData(null);
				}
			}
			
			if ( (arg0.getFixtureB().getBody().getUserData())instanceof MyRect)
			{
				
				MyRect rect=(MyRect)(arg0.getFixtureB().getBody().getUserData());

				if(rect.getType()==Type.stone
				||rect.getType()==Type.wood
				||rect.getType()==Type.pig
				||rect.getType()==Type.glass)
				{
					arg0.getFixtureB().getBody().setUserData(null);
				}
			}
		
		}
	}
	
	@Override
	public void beforeGameStart() {
		// TODO Auto-generated method stub
		fireballTimeUtil = new GameTimeUtil(1000);
		gameTimeUtil = new GameTimeUtil(1000);
	}

	@Override
	public void arrangeView(Activity activity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActivityContentView(Activity activity) {
		// TODO Auto-generated method stub
		activity.setContentView(gameview);
	}

	@Override
	public void afterGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

}
