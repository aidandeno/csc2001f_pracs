/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnbaid001
 */
public class TreeUtilsTest
{

    public TreeUtilsTest()
    {
    }

    /**
     * Test of similar method, of class TreeUtils.
     */
    @Test
    public void testSimilar()
    {
        SimpleBST tree1 = new SimpleBST();
        tree1.insert(25);
        tree1.insert(4);
        tree1.insert(12);
        tree1.insert(16);

        SimpleBST tree2 = new SimpleBST();
        tree2.insert(24);
        tree2.insert(3);
        tree2.insert(14);
        tree2.insert(18);

        SimpleBST tree3 = new SimpleBST();
        tree3.insert(1);

        SimpleBST tree4 = new SimpleBST();
        tree4.insert(9);

        SimpleBST tree5 = new SimpleBST();
        tree5.insert(2);
        tree5.insert(1);
        tree5.insert(3);
        tree5.insert(5);
        tree5.insert(4);
        tree5.insert(7);
        tree5.insert(6);

        SimpleBST tree6 = new SimpleBST();
        tree6.insert(12);
        tree6.insert(11);
        tree6.insert(13);
        tree6.insert(15);
        tree6.insert(14);
        tree6.insert(17);
        tree6.insert(16);

        assertTrue(tree1.similar(tree2));
        assertTrue(tree3.similar(tree4));
        assertTrue(tree5.similar(tree6));
        assertFalse(tree1.similar(tree3));
        assertFalse(tree2.similar(tree6));
        assertFalse(tree4.similar(tree5));
    }
}
