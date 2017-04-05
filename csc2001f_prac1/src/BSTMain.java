
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main method takes in two sets of comma-separated integers from the command
 * line and creates appropriate Binary Search Trees from the values. Each tree
 * is then printed to the console as a textual representation and to two
 * separate .out files.
 *
 * The trees are inspected to see whether they have similar tree structures and
 * an appropriate message is printed.
 *
 * @author Aidan de Nobrega - DNBAID001
 * @since 01/03/2015
 * @see SimpleBST
 * @see SimpleTreeWriterImpl
 * @see TreeUtils
 */
public class BSTMain
{
    public static void main(String args[]) throws FileNotFoundException
    {
        System.out.print("Enter a comma separated sequence of node values: ");
        Scanner scan1 = new Scanner(System.in);
        //Comma is used as delimiter for Scanner
        scan1 = new Scanner(scan1.nextLine()).useDelimiter("\\s*,\\s*");

        //First input is converted to Binary Search Tree
        SimpleBST T1 = new SimpleBST();

        while (scan1.hasNextInt())
        {
            T1.insert(scan1.nextInt());
        }

        System.out.print("Enter a comma separated sequence of node values: ");
        Scanner scan2 = new Scanner(System.in);
        scan2 = new Scanner(scan2.nextLine()).useDelimiter("\\s*,\\s*");

        //Second input is converted to Binary Search Tree
        SimpleBST T2 = new SimpleBST();

        while (scan2.hasNextInt())
        {
            T2.insert(scan2.nextInt());
        }

        //Trees are printed to console
        System.out.println("Tree One: ");
        SimpleBST.print(T1, new SimpleTreeWriterImpl(System.out));
        System.out.println("Tree Two: ");
        SimpleBST.print(T2, new SimpleTreeWriterImpl(System.out));

        //Tree structures are checked for similarity and the appropriate message
        //is printed
        if (T1.similar(T2))
        {
            System.out.println("The trees are similar.");
        }
        else
        {
            System.out.println("The trees are not similar.");
        }

        //Textual representations of trees are printed in separate .out files
        SimpleBST.print(T1, new SimpleTreeWriterImpl(new PrintStream(new File("T1.out"))));
        SimpleBST.print(T1, new SimpleTreeWriterImpl(new PrintStream(new File("T2.out"))));
    }
}
