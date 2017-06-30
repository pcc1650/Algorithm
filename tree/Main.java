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
