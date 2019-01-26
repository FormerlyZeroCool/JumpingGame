import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class PlayingCanvas extends Canvas
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player1;
	private static int refreshCount=0;
	private MainScreen mainScreen;
	private boolean paintComplete=false;
	private Color cbackground,cpanel,cplayer,cenemy,cPowerUp,cpanelSlippery;
	public PlayingCanvas(MainScreen m)
	{
		requestFocus();
		mainScreen=m;
		this.setSize(mainScreen.getXDim(), mainScreen.getYDim());
		player1=mainScreen.getPlayer();
		cbackground=new Color(30,30,180);
		cplayer=new Color(255, 255, 0);
		cenemy=new Color(0,255,0);
		cpanel=new Color(255, 0, 0);
		cpanelSlippery=new Color(0, 10, 90);
		cPowerUp=new Color(0,255,255);
		repaint();
	}
	@Override
	public void paint(Graphics g)
	{
		

		
			
			g.setColor(cbackground);//Create background
			g.fillRect(0, 0, mainScreen.getXDim(), mainScreen.getYDim());
			g.setColor(cpanel);//create game floor
			//g.fillRect(0, mainScreen.getYDim()-50, mainScreen.getXDim(), mainScreen.getYDim());
			//	pnlArray.get(0).drawPanel(g);
			
			int counter;
			
			for(Panel panels : JumpinMain.pnlArray)//create game floor
			{
				if(panels.isSlippery())
					g.setColor(cpanelSlippery);
				else
					g.setColor(cpanel);
				
				panels.drawPanel(g);
			}
			for(counter=0;counter<JumpinMain.cenemyArray.size();counter++)
			{
				if(JumpinMain.pnlArray.contains(JumpinMain.cenemyArray.get(counter).getPanel()))
				{
					g.setColor(cenemy);
					JumpinMain.cenemyArray.get(counter).reDraw(g);
				}
			}
			for(counter=0;counter<JumpinMain.powerUpArray.size();counter++)//draw powerups
			{
				if(JumpinMain.pnlArray.contains(JumpinMain.powerUpArray.get(counter).getPanel()))
				{
					g.setColor(cPowerUp);
					JumpinMain.powerUpArray.get(counter).reDraw(g);
				}
			}
			g.setColor(cplayer);//create avatar
			g.fillRect(player1.getPlayerXPos(), player1.getPlayerYPos(), player1.getPlayerWidth(), player1.getPlayerHeight());	
			if(JumpinMain.playerEnemyCollision)
			{
				g.setColor(cpanel);
				g.fillRect(player1.getPlayerXPos(), player1.getPlayerXPos(), player1.getPlayerWidth()/3, 0);
			}
			paintComplete=true;
			refreshCount++;
		
	}
	
	public int getXDim()
	{
		return mainScreen.getXDim();
	}
	public int getYDim()
	{
		return mainScreen.getYDim();
	}
	public boolean getIsLoaded() 
	{
		return paintComplete;
	}
	



}
