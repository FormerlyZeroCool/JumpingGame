
import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class MainScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PlayingCanvas canvas;
	private Player player1;
	private int xDim=800,yDim=700;
	private JPanel jpnlTitle;
	private Label lblCount,lblInstruct,lblHighScore;
	private boolean resetFrame=false;
	private boolean isPaused=false;

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		setIgnoreRepaint(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 760);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		jpnlTitle = new JPanel();
		contentPane.add(jpnlTitle, BorderLayout.NORTH);
		JPanel pnlMain=new JPanel();
		
		lblCount=new Label("Awaiting player to start game, use wasd to control");
		lblInstruct=new Label("Press n to restart");
		lblHighScore=new Label("  High Score is: 0000");
		jpnlTitle.add(lblCount);
		jpnlTitle.add(lblInstruct);
		jpnlTitle.add(lblHighScore);
		contentPane.add(pnlMain);
		player1=new Player(this);
		canvas=new PlayingCanvas(this);
		pnlMain.add(((PlayingCanvas)canvas));
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
					
					if (e.getKeyCode()==e.VK_D || e.getKeyCode()==e.VK_RIGHT)
					{
						player1.setMovingRight(true);
					}
					if (e.getKeyCode()==e.VK_A || e.getKeyCode()==e.VK_LEFT)
					{
						player1.setMovingLeft(true);
					}
					if (e.getKeyChar()=='r')
					{
						JumpinMain.printPanelDim();
						System.out.println("player:");
						player1.printDim();
						System.out.println(JumpinMain.getGameFloor());
					}
					if(e.getKeyChar()=='s')
					{
						scrollScreen(-100);
					}
					if(e.getKeyChar()=='n')
					{
						restartGame();
						
					}
					if(e.getKeyChar()=='p')
					{
						isPaused=!isPaused;
						
					}
				}
				
			

			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==e.VK_D || e.getKeyCode()==e.VK_RIGHT )
				{
					player1.setMovingRight(false);
				}
				if (e.getKeyCode()==e.VK_A || e.getKeyCode()==e.VK_LEFT)
				{
					player1.setMovingLeft(false);
				}
			}
		});
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if((e.getKeyCode()==e.VK_W || e.getKeyCode()==e.VK_UP
						))
				{
					if(player1.getTimeInAir()==0)
						player1.setJumping(true);

				}
				
			}
		});
	}
	public void updateHighScore()
	{
		lblHighScore.setText("  High Score: "+JumpinMain.highscore);
	}
	public Player getPlayer()
	{
		return player1;
	}
	public int getXDim()
	{
		return xDim;
	}
	public int getYDim()
	{
		return yDim;
	}
	public void reDraw()
	{
		canvas.repaint();
		
	}
	public void updatelblPanelCount(String s)
	{
		lblCount.setText(s+" "+JumpinMain.panelsDrawn);
	}
	public void playerFall()
	{
		player1.fall();
	}
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return canvas.getIsLoaded();
	}
	public void scrollScreen(int value) 
	{
		player1.scroll(value);
		for(int j=0;j<JumpinMain.pnlArray.size();j++)
		{
			JumpinMain.pnlArray.get(j).scroll(value);
			if(JumpinMain.pnlArray.get(j).getYPos()>getYDim())
				JumpinMain.pnlArray.remove(j);
		}
	}
	public boolean reset() {
		if(resetFrame)
		{
			resetFrame=false;
			return !resetFrame;
		}
		return false;
	}
	public void restartGame() 
	{

		updateHighScore();
		for(int j=0;j<10;j++)
		clearPanels();
		JumpinMain.setPanelCount(0);
		lblCount.setText("Awaiting player to start game, use wasd to control");
	}
	public void clearPanels()
	{
		for(int j=0;JumpinMain.pnlArray.size()>j;j++)
		{
			if(JumpinMain.pnlArray.get(j).getYPos()!=JumpinMain.getGameFloor())
			{
				JumpinMain.pnlArray.remove(j);				
			}
		}
		JumpinMain.pnlArray.add(new Panel(this,false,0,650,getXDim(),getYDim()));
		player1.rePosition(getXDim()/4,getYDim()-player1.getPlayerHeight()-50);

	}
	public boolean isPaused() {
		// TODO Auto-generated method stub
		return isPaused;
	}
	public boolean getIsPlayerEnemyColliding()
	{
		return JumpinMain.playerEnemyCollision;
	}
	public PlayingCanvas getCanvas() 
	{
		return canvas;
	}
}
