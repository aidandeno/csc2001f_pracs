
import java.util.ArrayList;
import java.util.List;

/**
 * Utility procedures for binary tree structures.
 *
 * @author Stephan Jamieson
 * @version 25/2/2015
 */
public class TreeUtils
{

    /**
     * Obtain the height value of the given node.
     *
     * @return 0 if <code>node==null</code>, otherwise
     * <code>node.getHeight()</code>.
     */
    public static int height(AVLTreeNode node)
    {
        if (node == null)
        {
            return 0;
        }
        else
        {
            return node.getHeight();
        }
    }

    /**
     * Determine whether the given tree structure contains the given key.
     */
    public static boolean contains(AVLTreeNode node, Integer key)
    {
        if (node.getKey() == (int) key)
        {
            return true;
        }

        if (isPlaceHolder(node))
        {
            return false;
        }

        if ((int) key < node.getKey())
        {
            if (node.hasLeft())
            {
                return contains(node.getLeft(), key);
            }
            return false;
        }
        else
        {
            if (node.hasRight())
            {
                return contains(node.getRight(), key);
            }
            return false;
        }
    }

    /**
     * Recursive implementation of insert on an AVLTreeNode structure.
     */
    public static AVLTreeNode insert(AVLTreeNode node, Integer key)
    {
        if (node == null)
        {
            node = new AVLTreeNode(key);
        }
        else if (node.getKey() == (int) key)
        {
            //duplicate key, do nothing
        }
        else if (key < node.getKey())
        {
            node.setLeft(insert(node.getLeft(), key));
            if (Math.abs(height(node.getLeft()) - height(node.getRight())) > 1)
            {
                node = rebalanceLeft(node, key);
            }
        }
        else
        {
            node.setRight(insert(node.getRight(), key));
            if (Math.abs(height(node.getLeft()) - height(node.getRight())) > 1)
            {
                node = rebalanceRight(node, key);
            }
        }

        node.setHeight(Math.max(height(node.getLeft()),
                height(node.getRight())) + 1);
        
        return node;
    }
    
    private static AVLTreeNode rebalanceLeft(AVLTreeNode node, Integer key)
    {
        if (key < node.getLeft().getKey())
        {
            return rotateWithLeftChild(node);
        }
        else
        {
            return doubleRotateWithLeftChild(node);
        }
    }
    
    private static AVLTreeNode rebalanceRight(AVLTreeNode node, Integer key)
    {
        if (key > node.getRight().getKey())
        {
            return rotateWithRightChild(node);
        }
        else
        {
            return doubleRotateWithRightChild(node);
        }
    }

    /**
     * Rotate binary tree node with left child. This is a single rotation for
     * case 1.
     */
    public static AVLTreeNode rotateWithLeftChild(AVLTreeNode k2)
    {
        AVLTreeNode k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        k2.setHeight(Math.max(height(k2.getLeft()), height(k2.getRight())) + 1);
        k1.setHeight(Math.max(height(k1.getLeft()), height(k2)) + 1);

        return k1;
    }

    /**
     * Rotate binary tree node with right child. This is a single rotation for
     * case 4.
     */
    public static AVLTreeNode rotateWithRightChild(AVLTreeNode k1)
    {
        AVLTreeNode k2 = k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        k1.setHeight(Math.max(height(k1.getLeft()), height(k1.getRight())) + 1);
        k2.setHeight(Math.max(height(k2.getRight()), height(k1)) + 1);

        return k2;
    }

    /**
     * Double rotate binary tree node: first rotate k3's left child with its
     * right child; then rotate node k3 with the new left child. This is a
     * double rotation for case 2.
     */
    public static AVLTreeNode doubleRotateWithLeftChild(AVLTreeNode k3)
    {
        k3.setLeft(rotateWithRightChild(k3.getLeft()));
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first rotate k1's right child with its
     * left child; then rotate node k1 with the new right child. This is a
     * double rotation for case 3.
     */
    public static AVLTreeNode doubleRotateWithRightChild(AVLTreeNode k1)
    {
        k1.setRight(rotateWithLeftChild(k1.getRight()));
        return rotateWithRightChild(k1);
    }

    /**
     * Obtain a list containing the root node of the given structure i.e. tNode
     * itself.
     */
    public static List<AVLTreeNode> levelZero(AVLTreeNode tNode)
    {
        List<AVLTreeNode> level = new ArrayList<AVLTreeNode>();
        level.add(tNode);
        return level;
    }

    /**
     * Given a list of nodes, obtain the next level.
     *
     * <p>
     * If the tree structure is incomplete, <code>AVLTreeNode.EMPTY_NODE</code>
     * is inserted as a place holder for each missing node.
     * </p>
     */
    public static List<AVLTreeNode> nextLevel(List<AVLTreeNode> level)
    {
        List<AVLTreeNode> nextLevel = new ArrayList<AVLTreeNode>();

        for (AVLTreeNode node : level)
        {
            nextLevel.add(node.hasLeft() ? node.getLeft() : AVLTreeNode.EMPTY_NODE);
            nextLevel.add(node.hasRight() ? node.getRight() : AVLTreeNode.EMPTY_NODE);
        }
        return nextLevel;
    }

    /**
     * Determine whether node is a place holder i.e.
     * <code>node==AVLTreeNode.EMPTY_NODE</code>
     */
    public static boolean isPlaceHolder(AVLTreeNode node)
    {
        return node == AVLTreeNode.EMPTY_NODE;
    }
}