package connectfour;

import java.util.ArrayList;
import java.util.Random;

// AI responsible for selecting moves (i.e., picking columns)
public class AI2 implements AI
{
	/*
	 * Selects the column to place a piece in
	 * 
	 * Inputs:
	 *  {Board} board - Represents the state of the game
	 *  {int} team - the team for this AI
	 * 
	 * Output:
	 *  {int} - An integer within the domain: [0, Consts.NUM_COLS-1]
	 * 
	 * Note: Choosing a column that is full will result in disqualification
	 */
	public int selectColumn(Board board, int team)
	{
		//Example: pick random valid column
		ArrayList<Integer> validColumns = new ArrayList<Integer>();

		for (int c = 0; c < Board.NUM_COLS; c++)
		{
			if (board.columnFull(c) == false)
			{
				validColumns.add(c); //Add column if valid (i.e. not full)
			}
		}

		Random random = new Random();
		int randomNumber = random.nextInt(validColumns.size());

		return validColumns.get(randomNumber);
	}
}
