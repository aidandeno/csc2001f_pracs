
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Prints textual representation of trees to an output designated by the
 * constructor's argument.
 *
 * @author Aidan de Nobrega - DNBAID001
 * @since 01/03/2015
 * @see SimpleTreeWriter
 * @see TreeUtils
 * @see BinaryTreeNode
 */
public class SimpleTreeWriterImpl implements SimpleTreeWriter
{
    /**
     * Output location of write() method is set when an object is instantiated.
     */
    private PrintStream outputLocation;

    /**
     * Simple constructor.
     *
     * @param output Output location of write() method.
     */
    public SimpleTreeWriterImpl(PrintStream output)
    {
        setDestination(output);
    }

    @Override
    /**
     * Used by constructor to set the destination of the write() method's
     * output.
     *
     */
    public void setDestination(PrintStream stream)
    {
        this.outputLocation = new PrintStream(stream);
    }

    @Override
    /**
     * Creates and prints textual representation of a Binary Search Tree to a
     * destination set at instantiation.
     *
     * @param tree Root node of Binary Search Tree to be printed.
     */
    public void write(BinaryTreeNode tree)
    {
        //height of tree for iteration
        int height = 0;
        //for padding of output
        int textWidth = 0;
        
        //a single-element list containing the root node of the tree
        ArrayList<BinaryTreeNode> levelList = (ArrayList<BinaryTreeNode>) TreeUtils.levelZero(tree);
        
        if (tree != null)
        {
            height = tree.getHeight();
            //all spacing and values have to be consistent according to the width
            //of the largest value in the BST
            textWidth = tree.getLargest().toString().length();

        }
        //printing is done i times where i is the number of levels in the tree
        for (int i = 0; i < height; i++)
        {
            //the number of spaces before the first value in each level
            int initialSpaces = textWidth * (int) ((Math.pow(2, height - i) - 1) / 2);
            //the number of spaces between values on a level
            int internalSpaces = textWidth * (int) (Math.pow(2, height - i) - 1);

            //initial spaces are printed
            for (int j = 0; j < initialSpaces; j++)
            {
                outputLocation.print(" ");
            }

            Iterator<BinaryTreeNode> it;
            for (it = levelList.iterator(); it.hasNext();)
            {
                BinaryTreeNode element = it.next();

                //if the element is a placeholder, whitespace is printed in order
                //to maintain tree structure. Otherwise, the element itself is printed
                //formatted to designated text width.
                if (TreeUtils.isPlaceHolder(element))
                {
                    for (int j = 0; j < textWidth; j++)
                    {
                        outputLocation.print(" ");
                    }
                }
                else
                {
                    outputLocation.printf("%" + textWidth + "s", element.getItem());
                }

                //if there are more elements in the list, internalSpaces are printed.
                //Otherwise a new line is printed.
                if (it.hasNext())
                {
                    {
                        for (int j = 0; j < internalSpaces; j++)
                        {
                            outputLocation.print(" ");
                        }
                    }
                }
                else
                {
                    outputLocation.println();
                    
                    //everything from here in this 'else' clause is for printing
                    //branches. The algo is very similar to the one above.
                    for (int j = 0; j < initialSpaces; j++)
                    {
                        outputLocation.print(" ");
                    }

                    Iterator<BinaryTreeNode> it2;
                    for (it2 = levelList.iterator(); it2.hasNext();)
                    {
                        BinaryTreeNode element2 = it2.next();

                        if (TreeUtils.isPlaceHolder(element2))
                        {
                            for (int j = 0; j < textWidth; j++)
                            {
                                outputLocation.print(" ");
                            }
                        }
                        else //branches are printed here
                        {
                            if (element2.hasLeft() && element2.hasRight())
                            {
                                outputLocation.printf("%-" + textWidth + "s", "/ \\");
                            }
                            else if (element2.hasLeft())
                            {
                                outputLocation.printf("%-" + textWidth + "s", "/ ");
                            }
                            else if (element2.hasRight())
                            {
                                outputLocation.printf("%" + textWidth + "s", " \\");
                            }
                            else
                            {
                                outputLocation.printf("%" + textWidth + "s", " ");
                            }
                        }

                        //if there are more elements in the list, internalSpaces are printed.
                        //Otherwise a new line is printed.
                        if (it2.hasNext())
                        {
                            {
                                for (int j = 0; j < internalSpaces; j++)
                                {
                                    outputLocation.print(" ");
                                }
                            }
                        }
                        else
                        {
                            outputLocation.println();
                        }

                    }
                }
            }
            //Printing moves on to next level.
            levelList = (ArrayList<BinaryTreeNode>) TreeUtils.nextLevel(levelList);
        }
    }
}
