/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// 617
public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if(t1 == null)
        return t2;
    if(t2 == null)
        return t1;
    TreeNode t = new TreeNode(t1.val + t2.val);
    t.left = mergeTrees(t1.left, t2.left);
    t.right = mergeTrees(t1.right, t2.right);
    return t;
}
// 100
public boolean isSameTree(TreeNode p, TreeNode q) {
    if(p == null && q == null)
        return true;
    if(p != null && q == null || p == null && q != null)
        return false;
    if(p.val == q.val){
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    else{
        return false;
    }
}
// 530
// This method aims to arbitry tree
public int getMinimumDifference(TreeNode root) {
    int res = Integer.MAX_VALUE;
    int val = root.val;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while(queue.size() != 0){
        TreeNode node = queue.poll();
        if(node.left != null) {
            queue.add(node.left);
            res = Math.min(res, Math.abs(val - node.left.val));
        }
        if(node.right != null) {
            queue.add(node.right);
            res = Math.min(res, Math.abs(val - node.right.val));
        }
    }
    
    int tempLeftMin = Integer.MAX_VALUE;
    int tempRightMin = Integer.MAX_VALUE;
    if(root.left != null) {
        tempLeftMin = getMinimumDifference(root.left);
    }
    if(root.right != null) {
        tempRightMin = getMinimumDifference(root.right);
    }
    res = Math.min(res, Math.min(tempLeftMin, tempRightMin));
    return res;
}
// 530 for BST
int min = Integer.MAX_VALUE;
Integer prev = null;

public int getMinimumDifference(TreeNode root) {
    if (root == null) return min;
    
    getMinimumDifference(root.left);
    
    if (prev != null) {
        min = Math.min(min, root.val - prev);
    }
    prev = root.val;
    
    getMinimumDifference(root.right);
    
    return min;
}
