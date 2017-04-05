
import java.util.ArrayList;
import java.util.List;

/**
 * Class of utilities that can be used on Binary Search Trees.
 *
 * Includes a method to check whether an element is empty or not, a method to
 * create a list of the root node in a tree, a method for inputting an level on
 * a tree and outputting the next level, and a method for checking whether two
 * trees have similar structures.
 *
 * @author Aidan de Nobrega - DNBAID001
 * @since 01/03/2015
 * @see BinaryTreeNode
 */
public class TreeUtils
{
    /**
     * Determine whether node is a place holder i.e.
     * <code>node==BinaryTreeNode.EMPTY_NODE</code>
     *
     * @param node an element of a Binary Search Tree.
     * @return true if node is placeholder and false otherwise.
     */
    public static boolean isPlaceHolder(BinaryTreeNode node)
    {
        return node == BinaryTreeNode.EMPTY_NODE;
    }

    /**
     * Creates a single-element list containing a Binary Search Tree's root
     * node.
     *
     * @param tNode root node of Binary Search Tree
     * @return single-element list containing a Binary Search Tree's root node.
     */
    public static List<BinaryTreeNode> levelZero(BinaryTreeNode tNode)
    {
        ArrayList<BinaryTreeNode> rootList = new ArrayList<BinaryTreeNode>();

        rootList.add(tNode);

        return rootList;
    }

    /**
     * Given a list of nodes, obtain the next level. If the tree structure is
     * incomplete, BinaryTreeNode.EMPTY_NODE is inserted as a place holder for
     * each missing node.
     *
     * @param level list of nodes at a specific level
     * @return list of nodes in the next level
     */
    public static List<BinaryTreeNode> nextLevel(List<BinaryTreeNode> level)
    {
        ArrayList<BinaryTreeNode> nextLevelList = new ArrayList<BinaryTreeNode>();

        for (BinaryTreeNode element : level)
        {
            //Adds left node (or placeholder) to list.
            if (element.hasLeft())
            {
                nextLevelList.add(element.getLeft());
            }
            else
            {
                nextLevelList.add(BinaryTreeNode.EMPTY_NODE);
            }
            //Adds right node (or placeholder) to list.
            if (element.hasRight())
            {
                nextLevelList.add(element.getRight());
            }
            else
            {
                nextLevelList.add(BinaryTreeNode.EMPTY_NODE);
            }
        }

        return nextLevelList;
    }

    @SuppressWarnings("null")
    /**
     * Determine whether one tree node structure is similar (has the same
     * structure) to another.
     *
     * @param treeStructOne root of first tree
     * @param treeStructTwo root of second tree
     * @return true if trees are similar, false otherwise
     */
    public static boolean similar(BinaryTreeNode one, BinaryTreeNode two)
    {
        //empty trees are equal
        if (one == null && two == null)
        {
            return true;
        }

        //empty tree is not equal to a non-empty one
        if ((one == null && two != null)
                || (one != null && two == null))
        {
            return false;
        }
        
        //if heights are not equal, don't even bother with the rest
        if (one.getHeight() == two.getHeight())
        {
            //recursive checking. upper-level conditional statements are to suppress
            //asserts in getLeft() and getRight() methods.
            if (!one.hasLeft() && !two.hasLeft() && !one.hasRight() && !two.hasRight())
            {
                return true;
            }

            if (one.hasLeft() && two.hasLeft() && one.hasRight() && two.hasRight())
            {
                return similar(one.getLeft(), two.getLeft())
                        && similar(one.getRight(), two.getRight());
            }
            
            if ((one.hasLeft() && two.hasLeft()) && (!one.hasRight() && !two.hasRight()))
            {
                return similar(one.getLeft(), two.getLeft());
            }
            
            if ((!one.hasLeft() && !two.hasLeft()) && (one.hasRight() && two.hasRight()))
            {
                return similar(one.getRight(), two.getRight());
            }
            //if none of the above conditions are met
            return false;
        }
        //heights are different
        return false;
    }
}
