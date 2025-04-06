import java.util.*;
import java.io.*;

public class Solution {
    enum Color { RED, GREEN}


    // Abstract Tree class ( base for TreeNode and TreeLeaf)
    abstract static class Tree {
        private int value;
        private int depth;
        private Color color;
        public Tree( int value, Color color, int depth) {
            this.value = value;
            this.color = color;
            this.depth = depth;
        }
        public abstract void accept(TreeVis visitor);
        public int getValue() {return value;}
        public Color getColor() {return color;}
        public int getDepth() {return depth;}
    }

    // Internal Node class
    static class TreeNode extends Tree {
        private List<Tree> children = new ArrayList<>();
        public TreeNode(int value, Color color, int depth) { super(value, color, depth); }
        public void addChild(Tree child) {
            children.add(child);
        }
        @Override 
        public void accept(TreeVis visitor) {
            visitor.visitNode(this);
            for(Tree child:children) child.accept(visitor);
        }
    }

    // Leaf Node class
    static class TreeLeaf extends Tree {
        public TreeLeaf(int value, Color color, int depth) {super(value,color,depth);}
        
        @Override
        public void accept(TreeVis visitor) {
            visitor.visitLeaf(this);
        }
    }

    // Visitor Abstract class
    static abstract class TreeVis {
            public abstract int getResult();
            public abstract void visitNode(TreeNode node);
            public abstract void visitLeaf(TreeLeaf leaf);
    }

    // Visitor 1 : Sum of values in leaves
    private static class SumInLeavesVisitor extends TreeVis {
        private int sum = 0;
        
        @Override
        public int getResult() {return sum;}
        
        @Override
        public void visitNode(TreeNode node){}
        
        @Override
        public void visitLeaf(TreeLeaf leaf) {sum += leaf.getValue();}
    }


    // Visitor 2 : Product of values of red nodes 
    private static class ProductOfRedNodesVisitor extends TreeVis {
        private long product = 1;
        public static final int MOD = 1000000007;
        
        @Override
        public int getResult() {return (int)product;}
        
        @Override
        public void visitNode(TreeNode node) {
            if (node.getColor() == Color.RED) {
                product = (product * node.getValue()) % MOD;
            }
        }
        
        @Override
        public void visitLeaf(TreeLeaf leaf) {
            if(leaf.getColor() == Color.RED) {
                product = (product * leaf.getValue()) % MOD;
            }
        }
    }

    // Visitor 3 : Fancy Visitor (even-depth non-leaf sum vs green leaf sum difference)
    private static class FancyVisitor extends TreeVis {
        private int evenDepthNonLeafSum = 0;
        private int greenLeafSum = 0;
        
        @Override
        public int getResult() { 
            long diff = evenDepthNonLeafSum - greenLeafSum;
            return (int) Math.abs(diff);
        }
        
        @Override
        public void visitNode(TreeNode node) {
            if(node.getDepth() % 2 == 0) 
                evenDepthNonLeafSum += node.getValue();
        }
        
        @Override
        public void visitLeaf(TreeLeaf leaf) {
            if(leaf.getColor() == Color.GREEN) {
                greenLeafSum += leaf.getValue();
            }
        }
    }

    // Data structures to hold input values and colors 
    private static int[] values;
    private static Color[] colors;
    private static Map<Integer, List<Integer>> adjList;
    
    
    /**
    * Read the input
    * @return the root of the constructed tree
    */
    
    public static Tree solve() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        values = new int[n];
        for(int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
        }
        colors = new Color[n];
        for (int i = 0; i < n; i++ ) {
            int colorCode = scanner.nextInt();
            colors[i] = (colorCode == 0) ? Color.RED : Color.GREEN;
        }
        
        // Initialize adjacency list
        adjList = new HashMap<>();
        for(int i = 0; i < n - 1; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            
            // Add edge u -> v
            adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adjList.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        scanner.close();
        
        // Build the tree starting from root node 
        if (n == 1) {
            // One node means it's a terminal leaf that doesn't have children and the depth defaults to zero anyway 
            return new TreeLeaf(values[0], colors[0], 0);
        }
        // Create root as node - Root node is the base and would have the initial index value of zero 
        // Since we verified the Terminal Node before, this would mean that the Root does have child nodes
        TreeNode root = new TreeNode(values[0], colors[0], 0);
        
        // Recursively Build children 
        for (int neighbour : adjList.get(1)) {
            // Remove after getting the value of the node
            adjList.get(neighbour).remove(Integer.valueOf(1));
            createTree(root, neighbour);
        }
        return root;
    }
    
    // Helper function to build the tree
    private static void createTree(TreeNode parent, int nodeId) {
        List<Integer> neighbours = adjList.get(nodeId);
        
        if (neighbours != null && !neighbours.isEmpty()) {
            TreeNode node = new TreeNode(values[nodeId - 1], colors[nodeId - 1], parent.getDepth() + 1);
            parent.addChild(node);
            
            // Recursievly check for each neighbor 
            for (int neighbor : neighbours) {
                adjList.get(neighbor).remove(Integer.valueOf(nodeId));
                createTree(node, neighbor);
            }
        } else {
            TreeLeaf leaf = new TreeLeaf(values[nodeId - 1], colors[nodeId - 1], parent.getDepth() + 1);
            parent.addChild(leaf);
        }
    }

    public static void main(String args[]) {
        // Build Tree from input
        Tree root = solve();
        
        // Create Visitors 
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();
        
        // Traverse the tree for visitors
        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);
        
        System.out.println(vis1.getResult());
        System.out.println(vis2.getResult());
        System.out.println(vis3.getResult());
        
        
    }

}               

    

                                    


        
         
