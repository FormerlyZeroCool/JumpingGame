import java.awt.Graphics;

public class PowerUp 
{
private int xPos,yPos,width,height;
public final int HP=1,JUMP=2,JUMPPOISON=3,POISON=4,NOTHING=0;//status options
private int powerUpStatus;

private Panel panel;
public PowerUp(Panel p,int pStat)
{
	double minXP,maxXP;
	panel=p;
	powerUpStatus=pStat;
	height=20;
	width=20;
	yPos=panel.getYPos()-height;
	maxXP= ((panel.getXPos()+panel.getWidth())/(1.0*panel.getScreenXDim()));
	minXP= (panel.getXPos()/(1.0* panel.getScreenXDim()));
	xPos=(int) (rand(minXP,maxXP)*panel.getScreenYDim());
	
}
public int getPowerUpStatus()
{
	return powerUpStatus;
}
public Panel getPanel()
{
	return panel;
}
public void reDraw(Graphics g)
{
	yPos=panel.getYPos()-height;
	if(xPos+width>panel.getWidth()+panel.getXPos())//Keeps object on platform when it shrinks
		xPos=panel.getWidth()+panel.getXPos()-width;
	if(xPos<panel.getXPos())
		xPos=panel.getXPos();
	g.fillRect(xPos, yPos, width, height);
}
public int getXPos() {
	return xPos;
}

public void setXPos(int xPos) {
	this.xPos = xPos;
}

public int getYPos() {
	return yPos;
}

public void setYPos(int yPos) {
	this.yPos = yPos;
}

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}
private double rand(double min,double max)
{
	return Math.random()*((max-min))+min;				
}

}
