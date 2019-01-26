import java.util.ArrayList;

public class JumpinMain {


	public static int highscore=0,panelsDrawn=0;
	public static int scrollAmount=12;//number of pixels scrolled when player reaches top portion of screen
	public static final int oScrollAmount=scrollAmount;//variable to save original
	public static ArrayList<Panel> pnlArray;// array of platforms in game used for checking collison
	public static ArrayList<PowerUp> powerUpArray;// array of current powerups in game used for checking collison
	public static ArrayList<CrawlingEnemy> cenemyArray;// array of enemies in game used for checking collison
	private static int refreshCount=0;//frame counter
	public static boolean playerEnemyCollision=false,playerPowerUpCollision=false;//boolean markers for collision
	public static Player player1;
	public static MainScreen frame;
	public static boolean mechanicready=false;
	private static int powerUpStatus=0,gameState=1;
	private static long refreshSleep=24;
	
	public static void main(String[] args) {
		
		//instantiate game object arrays
		pnlArray=new ArrayList<Panel>();
		cenemyArray=new ArrayList<CrawlingEnemy>();
		powerUpArray=new ArrayList<PowerUp>();
		//instatiate frame
					frame = getMainScreen();
					frame.setVisible(true);
					player1=frame.getPlayer();
		
					
					System.out.println("Updater Started Game listeners on");
				
					
					Thread mechanic=new Thread() {
						@Override
						public void run()
						{ 
							int greatestTime=0;
							int counter;//generic counter to iterate through for loops without declaring new int each time
							long startTime;//saves System time at begining of each frame
							String pStatus="";

							 int highJumpCounter=0,lowJumpCounter=0;//frame counters for powerups
								int prevstatus=0;//previous frame's powerup status
								boolean previousPlayerEnemyCollision=playerEnemyCollision;
							int j=0,k,airAcc=0;
							boolean wasJumping;
							while(true)//main game loop
							{
								startTime=System.currentTimeMillis();//get current system time
								if(gameState==0)//in dev idea is different game states will determine if the game is being played or if playeris in menu
								{
									
								}
								else//game being played
								{
									if(player1.getPlayerYPos()<frame.getYDim()/6)//if player is in the top 1/6 of the screen scroll
									{
										frame.scrollScreen(scrollAmount);
									}
									if(player1.getHighestPlayerY()>=frame.getYDim() && player1.getTimeInAir()==0 || player1.getHP()==0)//resets game
									{
										System.out.println(player1.getHighestPlayerY()+" "+frame.getYDim());
										//frame.(-10);
										powerUpStatus=0;
										frame.restartGame();//clears panel array, and  resets score
										player1.setJumpModifier(1);
										scrollAmount=oScrollAmount;
										player1.setHP(player1.getMaxHP());
										player1.setJumping(true);
										refreshCount=0;
										
									}	
									if(refreshCount==0)
									{
										pnlArray.add(new Panel(frame,false,0,650,frame.getXDim(),frame.getYDim()));
										panelsDrawn++;
									
								}
									while(getHighestPanel().getYPos()>120)
									{
										//System.out.println(getHighestPanel().getYPos());
										if(Math.random()<.1)
											pnlArray.add(new Panel(frame,true));
										else	
											pnlArray.add(new Panel(frame,false));
									if(Math.random()<(panelsDrawn/300.0)+(refreshCount/(60.0*720.0)))//remove plus 1 to reinstate logic
									{
										if(Math.random()<.25+ refreshCount/(60.0*1080.0))
											cenemyArray.add(new CrawlingEnemy(pnlArray.get(pnlArray.size()-1),true));
										else
											cenemyArray.add(new CrawlingEnemy(pnlArray.get(pnlArray.size()-1),false));
									}
									if(Math.random()<((10000.0)/(refreshCount+100000)+0.02))//remove plus 1 to reinstate logic
									{
										double rand=Math.random();
										if(rand<.25)	
											powerUpArray.add(new PowerUp(pnlArray.get(pnlArray.size()-1),1));
										else if(rand<.5)	
											powerUpArray.add(new PowerUp(pnlArray.get(pnlArray.size()-1),2));
										else if(rand<.75)	
											powerUpArray.add(new PowerUp(pnlArray.get(pnlArray.size()-1),3));
										else 	
											powerUpArray.add(new PowerUp(pnlArray.get(pnlArray.size()-1),4));
									}
									panelsDrawn++;
									}
									for(counter=0;counter<JumpinMain.cenemyArray.size();counter++)
									{
										if(!JumpinMain.pnlArray.contains(JumpinMain.cenemyArray.get(counter).getPanel()))
											JumpinMain.cenemyArray.remove(JumpinMain.cenemyArray.get(counter));
										
									}
									for(counter=0;counter<JumpinMain.powerUpArray.size();counter++)
									{
										if(!JumpinMain.pnlArray.contains(JumpinMain.powerUpArray.get(counter).getPanel()))
											JumpinMain.powerUpArray.remove(JumpinMain.powerUpArray.get(counter));
										
									}

									if(panelsDrawn>highscore)
										highscore=panelsDrawn;
									
									frame.updateHighScore();
									refreshCount++;
									
									//Player Movement Section

									wasJumping=false;
									//System.out.println(player1.getJumpTime()+" "+player1.isJumping());
									if(player1.getJumpTime()>15 || player1.isJumping()==false)//stops player from jumping after 15 frames also updates jumptime if not jumping, could be designed better
									{
										player1.setJumping(false);
										player1.setJumpTime(0);
									}
									if(player1.getHighestPlayerY()+1<getGameFloor())//checks if player is on top of a platform, and should stop falling
									{
										if(player1.getJumpTime()==14)//resets acceleration at top of jump
										{
											airAcc=1;
										}
										player1.updateTimeInAir(++airAcc);//updates falling velocity
										
										player1.fall();	//player falls according to saved velocity							
									}		
										if(player1.isJumping())//logic for jumping
										{
											player1.setJumpTime(player1.getJumpTime()+1);
											if(!wasJumping)
												k=0;
											
											if(player1.isJumping() && player1.isMovingRight())
											{
												player1.moveVector((int) (15*player1.getMoveModifier()),(int) (-20*player1.getJumpModifier()));
											}
											else if(player1.isJumping() && player1.isMovingLeft())
											{
												player1.moveVector((int) (-15*player1.getMoveModifier()),(int) (-20*player1.getJumpModifier()));
											}
											else
											{
												player1.moveVector(0,(int) (-20*player1.getJumpModifier()));	
											}
											
										}
										else 
										{
											
											if(player1.isMovingRight())
											{
												player1.moveVector((int) (15*player1.getMoveModifier()),0);
											}
											else if(player1.isMovingLeft())
											{
												player1.moveVector((int) (-15*player1.getMoveModifier()),0);
											}
											if(player1.getHighestPlayerY()+1==getGameFloor())
											{
												airAcc=0;
												player1.updateTimeInAir(airAcc);
											}
											
										
										player1.setOnSlipperyPanel(false);//unimplemented feature, slippery panels are supposed to cause x vel to change according to an acceleration modifier
									for(j=0;j<pnlArray.size();j++)
									{
										if(player1.isPlayerOnPanel(pnlArray.get(j).getYPos()) && pnlArray.get(j).isSlippery())
										{
											player1.setOnSlipperyPanel(true);
											
										}
									}
										wasJumping=player1.isJumping();
									}

									
								
								//End Player Movement Section
									
								//Collision Section
									
									if(playerEnemyCollision && !previousPlayerEnemyCollision)
									{
										player1.setHP(player1.getHP()-1);
										System.out.println("Hit! HP="+player1.getHP());
									}
									playerPowerUpCollision=false;
									int tmpstat=isPlayerPowerUpColliding().getPowerUpStatus();
									if(tmpstat==0)
									{
										playerPowerUpCollision=false;
									}
									else
									{
										powerUpStatus=tmpstat;
										playerPowerUpCollision=true;
									}
									if(playerPowerUpCollision)//Update player status according to powerup consumed
									{	
										System.out.println(powerUpStatus +" "+prevstatus);
										if(powerUpStatus==1 && player1.getHP()+1<=player1.getMaxHP())//add HP
										{
											player1.setHP(player1.getHP()+1);
											System.out.println("Health up! HP="+player1.getHP());
											powerUpStatus=0;
										}
										else if(powerUpStatus==2)//High Jump
										{
											player1.setJumpModifier(2);
											scrollAmount=26;
											highJumpCounter=0;
										}
										else if(powerUpStatus==3 && prevstatus!=3)//jump poison
										{
											player1.setJumpModifier(.7);
											scrollAmount=15;
											lowJumpCounter=0;
											System.out.println("Poison");
										}
										else if(powerUpStatus==4)
										{
											player1.setHP(player1.getHP()-1);
											powerUpStatus=0;
										}
									}
									if(highJumpCounter>60*10 && player1.getJumpModifier()==2)
									{
										player1.setJumpModifier(1);
										scrollAmount=oScrollAmount;
											powerUpStatus=0;
									}
									if(lowJumpCounter>60*10 && player1.getJumpModifier()==0.7)
									{
										player1.setJumpModifier(1);
										scrollAmount=oScrollAmount;
											powerUpStatus=0;
									}
									prevstatus=powerUpStatus;
									previousPlayerEnemyCollision=playerEnemyCollision;
									playerPowerUpCollision=false;
									playerEnemyCollision=false;
									isPlayerEnemyColliding();
									isPlayerPowerUpColliding();
									highJumpCounter++;
									lowJumpCounter++;
								
								}
							//End Collision Section
									
									
									if(player1.getHighestPlayerY()<frame.getYDim() && player1.getTimeInAir()!=0)
										
									while(frame.isPaused())
									{
										try {
											Thread.sleep(16);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									frame.reDraw();
									long endTime=System.currentTimeMillis();
									refreshSleep= ((16-(endTime-startTime)));
									if(refreshSleep>0)
									{
										try {
											Thread.sleep(refreshSleep);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										System.out.println(refreshSleep+" "+endTime+"-"+startTime+"="+(endTime-startTime));
									}
									if(greatestTime<endTime-startTime)
									{
										System.out.println(refreshSleep+" "+endTime+"-"+startTime+"="+(endTime-startTime));
										greatestTime=(int) (endTime-startTime);
									}
									double frameRate;
									if(refreshSleep>0)//calculates framerate, and prints to console is drops below 62.5
									{
										frameRate=1000./16;
									}
									else
									{
										frameRate=1000/((refreshSleep*-1)+16);
										System.out.println("Framerate: "+frameRate);
									}
									
									switch (powerUpStatus) 
									{
									case 0:
										pStatus="";
										break;
									case 1:
										pStatus="";
										break;
									case 2:
										pStatus="High Jump";
										break;
									case 3:
										pStatus="Low Jump";
										break;
									case 4:
										pStatus="";
										break;
									}
									frame.updatelblPanelCount(pStatus+" HP:"+player1.getHP()+" fr:"+frameRate+" Your current score: ");
								}

								
								
							}
						
							
						
					};
					mechanic.start();
					System.out.println(mechanic.getName());
}
		
	public static MainScreen getMainScreen()
	{
		if(frame==null)
			frame=new MainScreen();
		return frame;
	}

	public static void setPanelCount(int j)
	{
		panelsDrawn=j;
	}
	public static void printPanelDim()
	{
		for(int j=0;j<JumpinMain.pnlArray.size();j++)
		{
			JumpinMain.pnlArray.get(j).printDim();
		}
	}
	public static int getHighScore()
	{
			return highscore;
	}
	public static int getPanelDrawnCount()
	{
		return panelsDrawn;
	}
	public static void isPlayerEnemyColliding() 
	{
		for(int j=0;j<JumpinMain.cenemyArray.size() && !playerEnemyCollision;j++)
		{
			try {
					playerEnemyCollision=player1.getPlayerXPos()+player1.getPlayerWidth()>=JumpinMain.cenemyArray.get(j).getXPos()  && player1.getPlayerXPos()<=JumpinMain.cenemyArray.get(j).getXPos()+JumpinMain.cenemyArray.get(j).getWidth() && player1.getHighestPlayerY()>=JumpinMain.cenemyArray.get(j).getYPos() && player1.getPlayerYPos()<=JumpinMain.cenemyArray.get(j).getYPos()+JumpinMain.cenemyArray.get(j).getHeight();	
			}
			catch(NullPointerException n)
			{}
				
		}
	}
	public static PowerUp isPlayerPowerUpColliding() 
	{
		PowerUp temp;
		for(int j=0;j<JumpinMain.powerUpArray.size() && !playerPowerUpCollision;j++)
		{
			
			playerPowerUpCollision=player1.getPlayerXPos()+player1.getPlayerWidth()>=JumpinMain.powerUpArray.get(j).getXPos()  && player1.getPlayerXPos()<=JumpinMain.powerUpArray.get(j).getXPos()+JumpinMain.powerUpArray.get(j).getWidth() && player1.getHighestPlayerY()>=JumpinMain.powerUpArray.get(j).getYPos() && player1.getPlayerYPos()<=JumpinMain.powerUpArray.get(j).getYPos()+JumpinMain.powerUpArray.get(j).getHeight();	
			if(playerPowerUpCollision)
			{
				temp= powerUpArray.get(j);
				JumpinMain.powerUpArray.remove(j);
				return temp;
			}
				
		}
		return new PowerUp(new Panel(frame,false),0);
		
	}
	public static Panel getHighestPanel()
	{
		int index=0;
		if(!JumpinMain.pnlArray.isEmpty())
		{
			for(int j=0;j<JumpinMain.pnlArray.size();j++)
			{
				try {
				if(JumpinMain.pnlArray.get(index).getYPos()>JumpinMain.pnlArray.get(j).getYPos())
				{
					index=j;
				}
				}catch(Exception e) {};
			}
			if(index!=-1)
				return JumpinMain.pnlArray.get(index);
			else 
				return new Panel(frame,false,700,700,750,750);
		}
		else
		{
			return new Panel(frame,false,700,700,750,750);
		}
	}
	public static int getGameFloor()
	{
		int gameFloor=frame.getYDim()+50,index=-1;
		
		for(int j=0;j<pnlArray.size();j++)
		{
			try {
			if(JumpinMain.pnlArray.get(j).getYPos()>player1.getHighestPlayerY() && player1.getPlayerXPos()+player1.getPlayerWidth()>pnlArray.get(j).getXPos() && player1.getPlayerXPos()+(1/4)*player1.getPlayerWidth()<pnlArray.get(j).getXPos()+pnlArray.get(j).getWidth())
			{
				index=j;
			}
			}
			catch(Exception e)
			{
				
			};
			
		}
		if(index!=-1)
			gameFloor=JumpinMain.pnlArray.get(index).getYPos();
		return gameFloor;
	}

}
