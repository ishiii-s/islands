import java.util.*;
import java.io.*;

/******************************************************************************
* Name: Ishi Sood
* Block: D
* Date: 12/10/18
*
* Program #6 : Ponds and Islands
* 
* Description: Write a program that reads the file, prints the original map
* and then reprints the map with ponds (bodies of water) labeled ‘1’, ‘2’, 
* ‘3’, ... and islands (land masses) labeled ‘a’, ‘b’, ‘c’, ... . It should 
* also print the number of ponds and the number of islands
******************************************************************************/

public class PondsAndIslands 
{
	private static final String FILE_NAME = "map.txt";

	public static void main(String[] args) 
	{
		Scanner file = openTheFile(FILE_NAME);
		char [][] world = createWorld();
		
		//Use ASCII values to assign/update labels
		int labelLandInt = 'a';
		int labelWaterInt = '0';
		
		displayArray(world);
		
		//Runs through entire array and checks/does floodFillLand/Watrer if necessary
		for(int i = 0; i < world.length; i ++)
		{
			for(int j = 0; j < world[0].length; j ++)
			{
				//Only want to flood fill if it hasn't been updated
				if(world[i][j] == 'X')
				{
					floodFillLand(world, i, j, (char)labelLandInt);
					labelLandInt ++;
				}
				
				else if(world[i][j] == '.')
				{
					floodFillWater(world, i, j, (char)labelWaterInt);
					labelWaterInt ++;
				}
			}
			displayArray(world);
		}
		
		//figure out number of ponds and islands
		int numPonds = getNumPonds(labelWaterInt);
		int numIsles = getNumIslands(labelLandInt);
		
		System.out.println("Number of Ponds: " + numPonds + "\nNumber of Islands: " + numIsles);
			
	}

	/**
	 * Display the world grid
	 * @param world = 2D char array 
	 */
	public static void displayArray(char world [][])
	{
		System.out.println("");
		for(int row = 0; row < world.length; row ++)
		{
			for (int col = 0; col < world[0].length; col ++)
			{
				System.out.print(world[row][col]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * Read values from file and fill grid
	 * @return initialized grid
	 */
	public static char[][] createWorld()
	{
		Scanner file = openTheFile(FILE_NAME);
		int numRows = 0;
		int numCols = 0;
		
		if (file != null)
		{
			// Read in the number of rows and columns
			numRows = file.nextInt();
			numCols = file.nextInt();
			file.nextLine();
			char [][] world = new char[numRows][numCols];
			String row = "";
			
			//Read in the rows and columns themselves (read the map)
			for(int i = 0; i < numRows; i ++)
			{
				row = file.nextLine();
				
				for(int j = 0; j < numCols; j ++)
				{
					world[i][j] = row.charAt(j);
				}
			}	
			
			return world;
		}
		return null;
	}

	/**
	 * Open the request file. Leave file null if the open fails.
	 *
	 * @param filename the name of the file to open
	 * @return the newly opened file, or null if opening failed
	 */
	private static Scanner openTheFile(String fileName)
	{
		Scanner file = null;
		// Open the file. Note that Eclipse looks for the file in your
		// workspace inside your project folder (NOT in your src folder.)
		try
		{
			file = new Scanner(new File(fileName));
		}
		catch (IOException e)
		{
			// Something went wrong!
			System.out.println("File error - file not found");
		}
		return file;
	}

	/**
	 * Recursive method that fills in the ponds
	 * @param world = array of ponds/islands
	 * @param row = row position of array
	 * @param col = col position of array 
	 * @param label = char assigned to pond
	 */
	public static void floodFillWater(char [][] world, int row, int col, char label)
	{
		if (row < world.length && col < world[0].length && row > -1 && col > -1)
		{
			if(world[row][col] == '.')
			{
				//4 recursive calls: all grids in contact up and down
				world[row][col] = label;
				floodFillWater(world, row, col + 1, label);
				floodFillWater(world, row, col - 1, label);
				floodFillWater(world, row - 1, col, label);
				floodFillWater(world, row + 1, col, label);
			}
		}
		
	}

	/**
	 * Recursive methods that fills in the islands
	 * @param world = array of ponds/islands
	 * @param row = row position of array
	 * @param col = col position of array
	 * @param label = char assigned to island 
	 */
	public static void floodFillLand(char [][] world, int row, int col, char label)
	{
		if (row < world.length && col < world[0].length && row > -1 && col > -1)
		{
			if(world[row][col] == 'X')
			{
				//8 recursive calls: grids in contact up, down, and diagonally
				world[row][col] = label;
				floodFillLand(world, row, col + 1, label);
				floodFillLand(world, row, col - 1, label);
				floodFillLand(world, row - 1, col, label);
				floodFillLand(world, row + 1, col, label);
				floodFillLand(world, row - 1, col + 1, label);
				floodFillLand(world, row - 1, col - 1, label);
				floodFillLand(world, row - 1, col + 1, label);
				floodFillLand(world, row + 1, col - 1, label);
				
			}
		}

	}
	
	/**
	 * Tells you how many islands are in the grid
	 * @param label = int for char assigned to pond/island
	 * @return number of islands
	 */
	public static int getNumIslands(int label)
	{
		int check = 'a';
		System.out.println(label + " " + check);
		
		//Use ACII values to figure out number of Islands
		int numIslands = label - 'a';
		return numIslands;
	}
	
	/**
	 * Tells you how many ponds are in the grid
	 * @param label label = int for char assigned to pond/island
	 * @return number of ponds
	 */
	public static int getNumPonds(int label)
	{
		int check = '1';
		System.out.println(label + " " + check);
		
		//Use ACII values to figure out number of Ponds
		int numPonds = label - '0';
		return numPonds;
	}

}
