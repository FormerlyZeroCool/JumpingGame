import java.awt.Graphics;


public class CrawlingEnemy //exists as a class in the panel class
{
	private Panel panel;
	private int xPos,yPos,width=30,height=15,jumpHeight=60,deltaY,refreshCount=0;
	private boolean dxp,dyp,isJumping,jumps;
	public CrawlingEnemy(Panel p,boolean j)
	{
		jumps=j;
		panel=p;
		yPos=panel.getYPos()-height;
		xPos=(int) (Math.random()*panel.getWidth()+panel.getXPos());
	}
	public int getXPos()
	{
		return xPos;
	}
	public int getYPos()
	{
		return yPos;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void reDraw(Graphics g)
	{
		move();
		g.fillRect(xPos, yPos, width, height);
		refreshCount++;
	}
	public void move()
	{
		yPos=panel.getYPos()-height;
		if(xPos+width>panel.getWidth()+panel.getXPos())//Keeps object on platform when it shrinks
			xPos=panel.getWidth()+panel.getXPos()-width;
		if(xPos<panel.getXPos())
			xPos=panel.getXPos();
		setDeltaXPositive();
		if(dxp)
			xPos++;
		else
			xPos--;
		if(jumps && refreshCount%(60)==0)
		{
			isJumping=!isJumping;
			dyp=false;
		}
		
		if(isJumping)
		{
			
			setDeltaYPositive();
			if(dyp)
				deltaY+=2;
			else
				deltaY-=2;
		}
		else
		{
			deltaY=0;
		}

		yPos=panel.getYPos() + deltaY - height ;
		if(panel.getYPos()==yPos+height && isJumping)
		{
			yPos=panel.getYPos();
			isJumping=false;
		}
	}
	private void setDeltaXPositive()
	{
		
			if(xPos<=panel.getXPos())
			{
				dxp=true;
			}
			if(xPos>=panel.getXPos()+panel.getWidth()-width)
			{
				dxp=false;
			}
		
		
	}
	private void setDeltaYPositive()
	{
		if(!dyp && panel.getYPos()-yPos-height-deltaY!=0)
		if(panel.getYPos()-yPos-height-deltaY<=jumpHeight )
			dyp=false;
		else if(panel.getYPos()-yPos-height-deltaY>jumpHeight)
			dyp=true;
	}
	public Panel getPanel()
	{
		return panel;
	}
}
