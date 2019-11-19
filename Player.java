
public class Player 
{
	
	private int playerHeight,playerWidth,xPos,yPos,timeInAir,oldFloor,HP=2,maxHP=3;
	private double jumpModifier=1,moveModifier=1;
	private boolean isJumping=false,movingRight=false,movingLeft=false,onSlipperyPanel;
	private int jumpTime=0;
	private MainScreen screen;
	
	public Player(MainScreen s)//only constructor used to setup player
	{
		screen=s;
		playerHeight=40;
		playerWidth=20;
		xPos=screen.getXDim()/4;
		yPos=screen.getYDim()-playerHeight-51;
		timeInAir=0;
	}
	public boolean isPlayerOnPanel(int panelY)
	{
		if(panelY==JumpinMain.getGameFloor() && yPos+playerHeight+1==panelY)
			return true;
			else
				return false;
	}
	public int getPlayerHeight()
	{
		return playerHeight;
	}
	public int getPlayerWidth()
	{
		return playerWidth;
	}
	public int getPlayerXPos()
	{
		return xPos;
	}
	public int getPlayerYPos()
	{
		return yPos;
	}
	public void jump(int height) 
	{
		height=100;
		yPos-=height;
		timeInAir=1;
	}
	public void fall()
	{
		if(getHighestPlayerY()<=JumpinMain.getGameFloor())
		{
			if(timeInAir!=0 && getHighestPlayerY()+timeInAir<JumpinMain.getGameFloor())
			{
				yPos+=timeInAir;
			}
			else if(oldFloor<JumpinMain.getGameFloor())
			{
				
			}
			else
			{
				//System.out.println("Landed: "+timeInAir+" "+screen.getGameFloor());
				yPos=JumpinMain.getGameFloor()-playerHeight-1;
			}
			oldFloor=JumpinMain.getGameFloor();	
		}
			
	}
	public void updateTimeInAir(int time)
	{
		timeInAir=time;
	}
	public int getTimeInAir()
	{
		return timeInAir;
	}
	public void setJumping(boolean b) 
	{
		isJumping=b;
	}
	public boolean isJumping()
	{
		return isJumping;
	}
	public int getHighestPlayerY()
	{
		return yPos+playerHeight;
	}
	public void moveRight(int amount) 
	{
		xPos+=amount;
	}
	public void moveLeft(int amount) 
	{
		xPos-=amount;
	}
	public void setMovingRight(boolean b) 
	{
		movingRight=b;
	}
	public boolean isMovingRight()
	{
		return movingRight;
	}
	public void setMovingLeft(boolean b) 
	{
		movingLeft=b;
	}
	public boolean isMovingLeft()
	{
		return movingLeft;
	}
	public void printDim()
	{
		System.out.println(xPos+","+yPos+" "+playerWidth+" "+playerHeight);
	}
	public void scroll(int value)
	{
		yPos+=value;
	}
	public void rePosition(int x, int y) 
	{
		yPos=y;
		xPos=x;
	}
	public void moveVector(int x, int y)
	{
		yPos+=y;
		xPos+=x;
	}
	public int getHP() 
	{
		return HP;
	}
	public void setHP(int hP) 
	{
		HP = hP;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public double getJumpModifier() {
		return jumpModifier;
	}
	public void setJumpModifier(double d) {
		this.jumpModifier = d;
	}
	public double getMoveModifier() {
		return moveModifier;
	}
	public void setMoveModifier(double moveModifier) {
		this.moveModifier = moveModifier;
	}
	public boolean isOnSlipperyPanel() {
		return onSlipperyPanel;
	}
	public void setOnSlipperyPanel(boolean onSlipperyPanel) {
		this.onSlipperyPanel = onSlipperyPanel;
	}
	public int getJumpTime() {
		return jumpTime;
	}
	public void setJumpTime(int jumpTime) {
		this.jumpTime = jumpTime;
	}
}
