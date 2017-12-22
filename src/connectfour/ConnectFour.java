package connectfour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;


/* 
 * Provides a GUI for a Connect Four 'Board' Object
 */
public class ConnectFour extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static Board board;
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int innerX = 20, innerY = 40, 
			innerW = WIDTH - 40, innerH = HEIGHT - 60;

	/*
	 * Initialize new frame and board
	 */
	public static void main(String[] args) 
	{
		frame = new ConnectFour();
		frame.setTitle("Connect Four");
		frame.setSize(WIDTH,HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		board = new Board(frame, new AI2(), new AI2()); //hook two AIs up to board game
		
		frame.setVisible(true);
	}
	
	
	/*
	 * Draws the outlining for the board and invokes drawPieces(g)
	 */
	public void paint(Graphics g)
	{
		g.setColor(Color.BLUE);
		for (int r = 0; r <= Board.NUM_ROWS; r++)
		{
			g.drawLine(innerX, innerY + r * innerH / Board.NUM_ROWS, innerX + innerW, 
				innerY + r * innerH / Board.NUM_ROWS);
		}
		for (int c = 0; c <= Board.NUM_COLS; c++)
		{
			g.drawLine(innerX + c * innerW / Board.NUM_COLS, 
				innerY, innerX + c * innerW / Board.NUM_COLS, innerY + innerH);
		}
		drawPieces(g);
	}
	
	/*
	 * Draws all pieces on the board
	 */
	public static void drawPieces(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;

		for (int r = 0; r < Board.NUM_ROWS; r++)
		{
			for (int c = 0; c < Board.NUM_COLS; c++)
			{
				int piece = (board.getPiece(r, c));
				if (piece == Board.TEAM.YELLOW.ordinal()) 
				{
					g2.setColor(Color.YELLOW);
					g2.fill(new Ellipse2D.Double(innerX + 2 + c * innerW / Board.NUM_COLS,
							innerY + innerH + 2 - ((r+1) * innerH / Board.NUM_ROWS),
							innerW / Board.NUM_COLS - 4, innerH / Board.NUM_ROWS - 4));
				}
				else if (piece == Board.TEAM.RED.ordinal()) 
				{
					g2.setColor(Color.RED);
					g2.fill(new Ellipse2D.Double(innerX + 2 + c * innerW / Board.NUM_COLS,
							innerY + innerH + 2 - ((r+1) * innerH / Board.NUM_ROWS),
							innerW / Board.NUM_COLS - 4, innerH / Board.NUM_ROWS - 4));
				}
			}
		}
	}
	
}
