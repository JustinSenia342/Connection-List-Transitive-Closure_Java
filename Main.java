/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 2/9/2017
 * Class: COSC 314
 * Project: #1, Part 2
 */
 
import java.util.*;
import java.io.*;

public class Main {
    
    //Declaring random variable outside of main so it can be used by all methods
    public static Random randGen;
    
    //main method for Project 1 
    public static void main(String[] args) throws IOException{
        
        //declaring variable "n" to be used as a universal "size" variable
        int n;
        
        
        //FOR COSC 314 PROJECT #1, PART 2:
        //This part of the project takes an external list of connections between a group,
        //reads the first number to determine matrix size, populates the matrix with the 
        //associated information and then calculates the transitive closure for the data set
        
        //Declaring file in location and file out location, also initializing printwriter
        //so that results can be logged in a text file.
        File inFileFour = new File("file4.txt");
        File outFileTwo = new File("Output.txt");
        PrintWriter pWriter = new PrintWriter(outFileTwo);
        
        //Creating scanner to user for reading information from external file
        //(Passed: inFileFour, with stored location of input file)
        Scanner fIn = new Scanner(inFileFour);
        
        n = fIn.nextInt(); //reading in first file number, setting as matrix size
        fIn.nextInt(); //discarding unecessary 2nd number (the total # of connections)
        
        //creating matrix based off of external file's size declaration
        int[][] fMatrixFour = new int[n][n];
        fMatrixFour = popMatrix(n); //initializing all matrix fields to zero
        
        //reads in data pairs, puts a 1 in corresponding matrix location
        //until it runs out of int values in file
        while (fIn.hasNextInt()){
            fMatrixFour[fIn.nextInt() - 1][fIn.nextInt() - 1] = 1;
        }
        
        //adds in a 1 at all matrix locations that are self referrential
        //if test is measuring dissention of information, it's only logical
        //that someone knows the information if they can tell it to someone else
        for (int x = 0; x < n; x++){
            fMatrixFour[x][x] = 1;
        }
        
        //outputting properly formatted compiled matrix
        pWriter.println("COSC 314 Project #1, Part 2: Original Matrix for file4");
        //(passed: fMatrixFlour= properly populated input matrix, n= size, pWriter= printwriter)
        printMatrix(fMatrixFour, n, pWriter);
        
        //doing transitive closure on matrix with warshalls algorithm, then setting to original pointer
        //(passed: fMatrixFour= properly populated input matrix, n= size)
        fMatrixFour = transClosureMatrix(fMatrixFour, n);
        pWriter.println("COSC 314 Project #1, Part 2: Transitive Closure Matrix for file4");
        //(passed: fMatrixFour= trans closure matrix, n= size, pWriter= printwriter)
        printMatrix(fMatrixFour, n, pWriter);
        
		//prompt that notifies user that the program is ending
		System.out.println("\nAlgortihm Finished, Ending Program...");
		
        //closing fIn and pWriter for data protection
        fIn.close();
        pWriter.close();
        
    }
    
    //Transitive closure algorithm
    //Uses warshalls algorithm to calculate transitive closure and reurns a
    //matrix containing the results
    //(passed: R= matrix, nElements= length of one side of matrix)
    public static int[][] transClosureMatrix(int[][] R, int nElements){
        
        //creating matrix pointer, assigning passed matrix to new pointer 
        int[][] A = R;
        //creating & initializing a matrix of equal size to matrix A,
        //(passed: nElements= length of one side of matrix)
        int[][] B = popMatrix(nElements);
        
        //3 nested for loops that use warshall's algorithm to bitwise Or and And
        //the proper elements of a single matrix in order to find the transitive closure
        //over multiple iterations
        for (int k = 0; k < nElements; k++){
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    B[i][j] = bitwiseOr(A[i][j], bitwiseAnd(A[i][k], A[k][j]));
            
            //copying the values of matrix B to matrix  A so loop can reset and 
            //go onto next iteration of calculations (if it hasn't reached the last iteration yet
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    A[i][j] = B[i][j];
        }
        
        //return matrix A with completed transitive closure
        return A;
    }
    
    //bitwise AND
    //takes two values, if they are both 1, returns a 1, otherwise returns 0
    //(passed: d= 1st value to be compared, m= 2nd value to be compared)
    public static int bitwiseAnd(int d, int m){
        
        if (d == 1 && m == 1){
            return 1;
        } 
        else 
            return 0;
    }
    
    //bitwise OR
    //takes two values, if they are both 0, returns a 0, otherwise returns 1
    //(passed: v= 1st value to be compared, b= 2nd value to be compared)
    public static int bitwiseOr(int v, int b){
        
        if (v == 0 && b == 0){
            return 0;
        } 
        else 
            return 1;
    }
    
    //creates a matrix populated by all zero's based on input parameter value, returns created matrix
    //(passed: nPop= size of on side of desired matrix to be created)
    public static int[][] popMatrix(int nPop){
        
        //create new matrix based on input param size
        int[][] zeroMatrix = new int[nPop][nPop];
        
        //populate new matrix with all zero's
        for (int t = 0; t < nPop; t++){
            for (int y = 0; y < nPop; y++){
                zeroMatrix[t][y] = 0;
            }
        }
        
        //returns new matrix filled with all zero's
        return zeroMatrix;
    }
    

    //prints the supplied matrix to an external file
    //(passed: toBePrinted= matrix to be printed, nPrint= length of matrix side,
    //(passed(cont): pW= printwriter with preconfigured file location attached.
    public static void printMatrix(int[][] toBePrinted, int nPrnt, PrintWriter pW){
        
        //iterates through matrix, and prints matrix in proper form
        for (int d = 0; d < nPrnt; d++){
            for (int f = 0; f < nPrnt; f++){
                pW.print(toBePrinted[d][f]);
            }
            pW.println("");
        }
        pW.println("\n");
    }
}
