package connectfour;

import javax.swing.JFrame;

public class Board
{
	
	/* static constants */
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 7;
	public static enum TEAM {NONE, YELLOW, RED};
	private static int SLEEP_TIME = 1500;
		
	private int[][] pieces; //2D matrix storing piece information
	private int nextRows[]; //Stores the row a new piece in a column would be placed
	private boolean gameOver;
	private int pieceCount;
	
	private TEAM playerTurn; //The turn of the current player
	private AI player1; //yellow player
	private AI player2; //red player
	
	/*
	 * Initializes empty Board
	 */
	public Board(JFrame frame, AI p1, AI p2)
	{
		this.player1 = p1;
		this.player2 = p2;
		pieces = new int[NUM_ROWS][NUM_COLS];
		nextRows = new int[NUM_COLS];
		pieceCount = 0;
		playerTurn = TEAM.YELLOW;
		gameOver = false;
		new Thread() //create new anonymous thread
		{
			public void run() 
			{
				while (gameOver == false) 
				{
					try {
						Thread.sleep(SLEEP_TIME);
						frame.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					processTurn(); //keep processing until game is over
				}
			}
		}.start();//Start processing
	}
	
	/*
	 * Processes a turn. 
	 * The column for the new piece is determined by getColumn()
	 * If the chosen column is already full, the player is disqualified.
	 * Check for a win or draw, then swaps player turns
	 */
	private void processTurn()
	{
		int col = getColumn();
		System.out.println("Column " + col + " selected.");
		if (col >= NUM_COLS)
		{
			System.out.println("Invalid Move - Column Out of Bounds.");
			swapPlayerTurn();
			declareWinner(playerTurn);
		}
		if (columnFull(col))
		{
			System.out.println("Invalid Move - Column Full.");
			swapPlayerTurn();
			declareWinner(playerTurn);
		}
		else
		{
			pieces[nextRows[col]][col] = playerTurn.ordinal();
			if (checkWin(nextRows[col], col)) //returns if player has won
			{
				declareWinner(playerTurn);
			}
			nextRows[col]++; //increment nextRow indicator
			pieceCount++; //increment number of pieces by 1
			swapPlayerTurn();

			// check draw condition
			if (pieceCount == NUM_ROWS * NUM_COLS)
			{
				declareWinner(TEAM.NONE);
			}
		}
	}
	
	/*
	 * Declares the new winner
	 */
	private void declareWinner(TEAM winner)
	{
		if (winner == TEAM.NONE) 
		{
			System.out.println("The game ended in a Draw.");
		}
		else if (winner == TEAM.YELLOW)
		{
			System.out.println("The Yellow Player has Won.");
		}
		else if (winner == TEAM.RED)
		{
			System.out.println("The Red Player has Won.");
		}
			
		gameOver = true;
	}
	
	/*
	 * Returns an AI's selected column based on who's turn it is
	 */
	private int getColumn()
	{
		if (playerTurn == TEAM.YELLOW)
		{
			return player1.selectColumn(this, TEAM.YELLOW.ordinal());
		}
		else
		{
			return player2.selectColumn(this, TEAM.RED.ordinal());
		}
	}
	
	/*
	 * Passes the turn over to the other player
	 */
	private void swapPlayerTurn()
	{
		if (playerTurn == TEAM.YELLOW)
		{
			playerTurn = TEAM.RED;
		}
		else
		{
			playerTurn = TEAM.YELLOW;
		}
	}
	
	
	/*
	 * Returns the piece at a given row and column
	 * Returns -1 if index out of bounds
	 */
	public int getPiece(int row, int col)
	{
		if (row >= NUM_ROWS || col >= NUM_COLS)
		{
			return -1; // out of bounds
		}
		return pieces[row][col];
	}

	/*
	 * Returns the pieces matrix associated with the board
	 */
	public int[][] getPieces()
	{
		return pieces;
	}

	/*
	 * Returns the next available row for a piece, given a column
	 * Returns -1 if index out of bounds
	 */
	public int getNextRow(int col)
	{
		if (col >= NUM_COLS)
		{
			return -1; // out of bounds
		}
		return nextRows[col];
	}

	/*
	 * Returns whether the given column is already full
	 * Returns false if index out of bounds
	 */
	public boolean columnFull(int col)
	{
		if (col >= NUM_COLS)
		{
			return false; // out of bounds
		}
		return nextRows[col] >= NUM_ROWS;
	}
	
	
	/*
	 * Check if the last placed piece causes a win
	 */
	private boolean checkWin(int row, int col)
	{
		if (checkWinHelper(row, col, -1, 0 , 1) >= 4) //Check downwards |
		{
			return true;
		}
		else if ((checkWinHelper(row, col, 0, -1, 1) + checkWinHelper(row, col, 0, 1, 0)) >= 4) //check horz -
		{
			return true;
		}
		else if ((checkWinHelper(row, col, 1, 1, 1) + checkWinHelper(row, col, -1, -1, 0)) >= 4) //check /
		{
			return true;
		}
		else if ((checkWinHelper(row, col, -1, 1, 1) + checkWinHelper(row, col, 1, -1, 0)) >= 4) //check \
		{
			return true;
		}
		return false;
	}

	

	/*
	 * Returns how many pieces are in a row in a given direction
	 */ 
	private int checkWinHelper(int row, int col, int rowDir, int colDir, int count)
	{
		row = row + rowDir;
		col = col + colDir;

		if (row < 0 || row >= NUM_ROWS || col < 0 | col >= NUM_COLS)
		{
			return count; //out of bounds
		}
		else if (pieces[row][col] != playerTurn.ordinal()) //doesn't continue
		{
			return count;
		}
		else //continues
		{
			count++;
			if (count == 4) //reached 4
			{
				return 4;
			}
			else //not yet 4
			{
				return checkWinHelper(row, col, rowDir, colDir, count);
			}
		}
	}
	
}
