// MazeSolver by Carter Techel
/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        Stack<MazeCell> st = new Stack<MazeCell>();
        MazeCell current = maze.getEndCell();
        st.push(current);
        // Fill the stack with the answetr to the maze
        while (!current.equals(maze.getStartCell()));
        {
            current = current.getParent();
            st.push(current);
        }
        st.add(maze.getStartCell());
        while (!st.isEmpty())
        {
            solution.add(st.pop());
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> st = new Stack<MazeCell>();
        MazeCell last = maze.getStartCell();
        st.push(last);
        // While you have not finsihed it find the touching MazeCell's
        while (last != maze.getEndCell())
        {
            // The next maze cell is north, east, south, then west. Find each one
            // add each cell in order to the stack using the helper method
            MazeCell north = new MazeCell(last.getRow(), last.getCol() + 1);
            solveMazeDFSHelper(north.getRow(), north.getCol(), north, st);
            MazeCell east = new MazeCell(last.getRow() + 1, last.getCol());
            solveMazeDFSHelper(east.getRow(), east.getCol(), east, st);
            MazeCell south = new MazeCell(last.getRow(), last.getCol() - 1);
            solveMazeDFSHelper(south.getRow(), south.getCol(), south, st);
            MazeCell west = new MazeCell(last.getRow() - 1, last.getCol());
            solveMazeDFSHelper(west.getRow(), west.getCol(), west, st);
            last = st.pop();
        }
        // use getSolution method to return the final path
        return getSolution();
    }

    // helper for DFS
    public void solveMazeDFSHelper(int row, int col, MazeCell current, Stack st)
    {
        // If the cell is valid add it to the stack and update correctly
        if (maze.isValidCell(row, col))
        {
            // add the cell to the stack, set the cells parent cell, and make it explored
            st.push(maze.getCell(row, col));
            maze.getCell(row, col).setParent(current);
            maze.getCell(row, col).setExplored(true);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> q = new LinkedList<MazeCell>();
        MazeCell last = maze.getStartCell();
        q.add(last);
        // The next maze cell is north, east, south, then west. Find each one
        // add each cell in order to the queue using the helper method
        while (last != maze.getEndCell())
        {
            MazeCell north = new MazeCell(last.getRow(), last.getCol() + 1);
            solveMazeBFSHelper(last, north, q);
            MazeCell east = new MazeCell(last.getRow() + 1, last.getCol());
            solveMazeBFSHelper(last, east, q);
            MazeCell south = new MazeCell(last.getRow(), last.getCol() - 1);
            solveMazeBFSHelper(last, south, q);
            MazeCell west = new MazeCell(last.getRow() - 1, last.getCol());
            solveMazeBFSHelper(last, west, q);
            last = q.remove();
        }
        // use getSolution to return the quickest final path
        return getSolution();
    }

    // helper for BFS
    public void solveMazeBFSHelper(MazeCell parent, MazeCell neighbor, Queue<MazeCell> q)
    {
        // If the next cell is valid add it to the queue and update correctly
        if (maze.isValidCell(neighbor.getRow(), neighbor.getCol()))
        {
            // add the next cell to the queue and update it's instance variable
            q.add(neighbor);
            neighbor.setParent(parent);
            neighbor.setExplored(true);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
