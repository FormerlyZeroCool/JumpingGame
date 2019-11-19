import java.awt.Graphics;
import java.util.Random;

public class Panel 
{
	private MainScreen screen;
	private int xPos,floor,width,height;
	private boolean isCollapsable=false,collapsed=false,isSlippery;
	private int refreshCount=0;
	private boolean longConstructor;
	public Panel(MainScreen p,boolean slippery)//constructor for game floor
	{
		isSlippery=slippery;// not really used 
		screen=p;
		height=20;
		floor=screen.getYDim()+20;
		if(Math.random()>.5)
			isCollapsable=true;
		longConstructor=false;
		refreshPanel();
		
	}
	public Panel(MainScreen p,boolean slippery,int x,int y,int w, int h)//constructor for all other panels
	{

		isSlippery=slippery;
		screen=p;
		xPos=x;
		floor=y;
		width=w;
		height=h;
		longConstructor=true;
		
	}
	public boolean isOnCanvas()
	{
		if(screen.getYDim()<floor || 0>floor)
			return false;
		else
			return true;
	}
	private double rand(double min,double max)
	{
		return Math.random()*((max-min))+min;				
	}
	
	public void drawPanel(Graphics graphics)
	{
		if(refreshCount%32==0 && isCollapsable)//alternates between drawing original panel or shrunken panel every 32 frames or roughly half a second
		{
			collapsed=!collapsed;
			if(collapsed)
			{
				xPos=xPos+width/4;
				width=width/2;
			}
			else
			{
				width=width*2;
				xPos=xPos-width/4;
			}
			
		}
			
		graphics.fillRect(xPos, floor, width, height);//Draw Panel
		
		refreshCount++;
	}
	private Panel highestpnl;
	public void refreshPanel() //moves panel to random position above last created panel within normal jumping range of player
	{
		highestpnl=JumpinMain.getHighestPanel();
		xPos=(int) (screen.getXDim()*rand(.01,.7));
		width=(int)(screen.getXDim()*rand(.3,.5));
			int min=highestpnl.getYPos()-100;
			int max=min-60;
			if(max<0)
				max=0;
			if(min<0)
				min=0;
			floor=(int) (screen.getYDim()*rand(max/(screen.getYDim()*1.0+0.01),min/(screen.getYDim()*1.0)+0.01));
			max=(int) (highestpnl.getXPos()+highestpnl.getWidth()+Math.sqrt(150*150-((highestpnl.getYPos()-floor)*(highestpnl.getYPos()-floor))));
			min=(int) (highestpnl.getXPos()-Math.sqrt(150*150-((highestpnl.getYPos()-floor)*(highestpnl.getYPos()-floor))));
			if(min<0)
				min=0;
			if(max>screen.getXDim())
				max=screen.getXDim()-width;
			xPos= (int) (rand(min/(screen.getYDim()*1.0+0.01),max/(screen.getYDim()*1.0+0.01)*screen.getYDim()));

	}
	public int getYPos()
	{
		return floor;
	}
	public int getXPos()
	{
		return xPos;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void printDim()
	{
		System.out.println(xPos+","+floor+" "+width+" "+height);
	}
	public void scroll(int value)
	{
		floor+=value;
	}
	public int getScreenYDim()
	{
		return screen.getYDim();
	}
	public int getScreenXDim()
	{
		return screen.getXDim();
	}
	public boolean isSlippery() {
		return isSlippery;
	}
}
