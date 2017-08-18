// Integet & Character
// Character.forDigit(i, radix)
// Character.digit(ch, radix)
public char[][] updateBoard(char[][] board, int[] click) {
    if(board == null || board[0] == null)
        return board;
    int x = click[0];
    int y = click[1];
    int xLength = board.length;
    int yLength = board[0].length;
    if(board[x][y] == 'M'){
        board[x][y] = 'X';
        return board;
    }
    int count = 0;
    List<Node> ls = new LinkedList<>();
    for(int i = -1; i < 2; i++){
        for(int j = -1; j < 2; j++) {
            if(x + i >= 0 && x + i < xLength  && y + j >= 0 && y + j < yLength){
                if(board[x + i][y + j] == 'M')
                {
                    count += 1;
                }
                else if(board[x + i][y + j] == 'E'){
                    Node node = new Node(x + i, y + j);
                    ls.add(node);
                }
            }
        }
    }
    if(count > 0) {
		// or board[x][y] = (char)(count + '0');
        char temp = Character.forDigit(count, 10);
        board[x][y] = temp;
    }
    else {
        board[x][y] = 'B';
        for(int i = 0; i < ls.size(); i++) {
            Node node = ls.get(i);
            int[] temp = new int[]{node.x, node.y};
            updateBoard(board, temp);
        }
    }
    return board;
}
private class Node {
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    int x;
    int y;
}

// 207 BFS
public boolean canFinish(int numCourses, int[][] prerequisites) {
    int matric[][] = new int[numCourses][numCourses];
    int inDegree[] = new int[numCourses];
    int count = 0;
    for(int i = 0; i < prerequisites.length; i++) {
        int pre = prerequisites[i][1];
        int suc = prerequisites[i][0];
        matric[pre][suc] = 1;
        inDegree[suc] += 1;
    }
    Queue<Integer> courses = new LinkedList<>();
    for(int i = 0; i < numCourses; i++)
        if(inDegree[i] == 0)
            courses.offer(i);
        
    while(!courses.isEmpty()) {
        int course = courses.poll();
        count += 1;
        for(int i = 0; i < numCourses; i++) {
            if(matric[course][i] != 0){
                if(--inDegree[i] == 0)
                    courses.offer(i);
            }
        }
    }
    return count == numCourses;
}
// 207 DFS
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] succs = new LinkedList[numCourses];
    boolean[] globalFlag = new boolean[numCourses];
    for(int i = 0; i < numCourses; i++) {
        succs[i] = new LinkedList<>();
    }
    for(int i = 0; i < prerequisites.length; i++) {
        int pre = prerequisites[i][1];
        int suc = prerequisites[i][0];
        succs[pre].add(suc);
    }
    for(int i = 0; i < numCourses; i++) {
        boolean[] partialFlag = new boolean[numCourses];
        if(hasCycle(i, succs, partialFlag, globalFlag))
            return false;
        else
            globalFlag[i] = true;
    }
    return true;
}
private boolean hasCycle(int index, List<Integer>[] succs, boolean[] partialFlag, boolean[] globalFlag) {
    if(globalFlag[index])
        return false;
    partialFlag[index] = true;
    for(int i = 0; i < succs[index].size(); i++) {
        // Should have a deep copy of current state
        boolean[] flags = Arrays.copyOf(partialFlag, partialFlag.length);          
        if(partialFlag[succs[index].get(i)] || hasCycle(succs[index].get(i), succs, flags, globalFlag))
            return true;
    }
    return false;
}
// 210 BFS
public int[] findOrder(int numCourses, int[][] prerequisites) {
    int[][] matrix = new int[numCourses][numCourses];
    int[] inDegree = new int[numCourses];
    int[] res = new int[numCourses];
    int index = 0;
    Queue<Integer> courses = new LinkedList<>();
    int count = 0;
    for(int i = 0; i < prerequisites.length; i++) {
        int pre = prerequisites[i][1];
        int suc = prerequisites[i][0];
        matrix[pre][suc] = 1;
        inDegree[suc] += 1;
    }
    
    for(int i = 0; i < numCourses; i++) {
        if(inDegree[i] == 0)
            courses.offer(i);
    }
    
    while(!courses.isEmpty()) {
        int course = courses.poll();
        res[index++] = course;
        count += 1;
        for(int i = 0; i < numCourses; i++) {
            if(matrix[course][i] == 1)
                if(--inDegree[i] == 0)
                    courses.offer(i);
        }
    }
    
    return count == numCourses ? res : new int[0];
}
// 394
public String decodeString(String s) {
    Stack<Integer> count = new Stack<>();
    Stack<String> result = new Stack<>();
    int i = 0;
    result.push("");
    while(i < s.length()) {
        char ch = s.charAt(i);
        if(ch >= '0' && ch <= '9') {
            String numStr = Character.toString(ch);
            int index = i + 1;
            while(s.charAt(index) >= '0' && s.charAt(index) <= '9'){
                numStr += Character.toString(s.charAt(index++));
            }
            int num = Integer.parseInt(numStr);
            count.push(num);
            i = index - 1;
        }
        else if(ch == '[') {
            result.push("");
        }
        else if(ch == ']') {
            int num = count.pop();
            String str = result.pop();
            String tempStr = "";
            for(int j = 0; j < num; j++)
                tempStr += str;
            result.push(result.pop() + tempStr);
        }
        else {
            result.push(result.pop() + ch);
        }
        i++;
    }
    return result.pop();
}
// 547
public int findCircleNum(int[][] M) {
    int count = 0;
    boolean[] flags = new boolean[M.length];
    for(int i = 0; i < M.length; i++) {
        if(flags[i])
            continue;
        findFriends(i, flags, M);
        count += 1;
    }
    return count;
}
private void findFriends(int start, boolean[] flags, int[][] M) {
    for(int i = 0; i < M.length; i++) {
        if(start == i || flags[i])
            continue;
        if(M[start][i] == 1){
            flags[i] = true;
            findFriends(i, flags, M);
        }
    }
}
// 515
public List<Integer> largestValues(TreeNode root) {
    List<Integer> res = new LinkedList<>();
    findLargestValues(root, 0, res);
    return res;
}
private void findLargestValues(TreeNode root, int row, List<Integer> res) {
    if(root == null)
        return;
    if(row >= res.size())
        res.add(root.val);
    else if(root.val > res.get(row))
        res.set(row, root.val);
    findLargestValues(root.left, row + 1, res);
    findLargestValues(root.right, row + 1, res);
}
//513
public int findBottomLeftValue(TreeNode root) {
    int[] res = new int[]{0, 0};
    res[0] = 1;
    res[1] = root.val;
    findValue(root, 1, res);
    return res[1];
}
private void findValue(TreeNode root, int row, int[] res) {
    if(root == null)
        return;
    if(root.left == null && row > res[0]) {
        res[0] = row;
        res[1] = root.val;
    }
    findValue(root.left, row + 1, res);
    findValue(root.right, row + 1, res);
}
// 513 level order
public int findBottomLeftValue(TreeNode root) {
    int res = 0;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while(!queue.isEmpty()) {
        int size = queue.size();
        for(int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if(i == 0)
                res = node.val;
            if(node.left != null)
                queue.add(node.left);
            if(node.right != null)
                queue.add(node.right);
        }
    }
    return res;
}
// 129
public int sumNumbers(TreeNode root) {
    return sum(root, 0);
}

public int sum(TreeNode n, int s){
    if (n == null) return 0;
    if (n.right == null && n.left == null) return s*10 + n.val;
    return sum(n.left, s*10 + n.val) + sum(n.right, s*10 + n.val);
}
// 199
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if(root == null)
        return res;
    queue.add(root);
    while(!queue.isEmpty()) {
        int size = queue.size();
        for(int i = 0; i < size; i++) {
            TreeNode temp = queue.poll();
            if(i == size - 1)
                res.add(temp.val);
            if(temp.left != null)
                queue.add(temp.left);
            if(temp.right != null)
                queue.add(temp.right);
        }
    }
    return res;
}
// 200
public int numIslands(char[][] grid) {
    if(grid.length == 0 || grid[0].length == 0)
        return 0;
    int res = 0;
    boolean[][] flags = new boolean[grid.length][grid[0].length];
    for(int i = 0; i < grid.length; i++) {
        for(int j = 0; j < grid[0].length; j++) {
            if(flags[i][j]){
                continue;
            }
            if(grid[i][j] == '1'){
                res += 1;
                flags[i][j] = true;
                identifyIsland(i, j, grid, flags);
            }
        }
    }
    return res;
}
private void identifyIsland(int i, int j, char[][] grid, boolean[][] flags) {
    if(i - 1 >= 0 && grid[i - 1][j] == '1' && !flags[i - 1][j]) {
        flags[i - 1][j] = true;
        identifyIsland(i - 1, j, grid, flags);
    }
    if(i + 1 < grid.length && grid[i + 1][j] == '1' && !flags[i + 1][j]) {
        flags[i + 1][j] = true;
        identifyIsland(i + 1, j, grid, flags);
    }
    if(j - 1 >= 0 && grid[i][j - 1] == '1' && !flags[i][j - 1]) {
        flags[i][j - 1] = true;
        identifyIsland(i, j - 1, grid, flags);
    }
    if(j + 1 < grid[0].length && grid[i][j + 1] == '1' && !flags[i][j + 1]) {
        flags[i][j + 1] = true;
        identifyIsland(i, j + 1, grid, flags);
    }
}
// 257
public List<String> binaryTreePaths(TreeNode root) {
    List<String> res = new LinkedList<>();
    String path = "";
    findAllPaths(root, path, res);
    return res;
}
private void findAllPaths(TreeNode root, String path, List<String> res) {
    if(root == null) return;
    if(root.left == null && root.right == null) {
        String finalPath = path + root.val;
        res.add(finalPath);
        return;
    }
    String leftPath = path + root.val + "->";
    String rightPath = path + root.val + "->";
    findAllPaths(root.left, leftPath, res);
    findAllPaths(root.right, rightPath, res);
}
// 112 
public boolean hasPathSum(TreeNode root, int sum) {
    if(root == null)
        return false;
    if(root.left == null && root.right == null && sum == root.val)
        return true;
    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
}
// 113 
public List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> res = new LinkedList<>();
    if(root == null)
        return res;
    List<Integer> path = new LinkedList<>();
    calculate(root, sum, path, res);
    return res;
}
private void calculate(TreeNode root, int sum, List<Integer> path, List<List<Integer>> res) {
    if(root.left == null && root.right == null) {
        if(root.val == sum){
            path.add(root.val);
            List<Integer> newPath = new LinkedList<>(path);
            res.add(newPath);
            path.remove(path.size() - 1);
        }
    }
    else{
        if(root.left != null){
            path.add(root.val);
            calculate(root.left, sum - root.val, path, res);
            path.remove(path.size() - 1);
        }
        if(root.right != null){
            path.add(root.val);
            calculate(root.right, sum - root.val, path, res);
            path.remove(path.size() - 1);
        }
    }
}
// 108
public TreeNode sortedArrayToBST(int[] nums) {
    if(nums==null || nums.length == 0)
        return null;
    int len = nums.length;
    return toBST(nums, 0, len - 1);
}
private TreeNode toBST(int[] nums, int start, int end) {
    if(start > end)
        return null;
    TreeNode root = new TreeNode(nums[(start + end) / 2]);
    root.left = toBST(nums, start, (start + end) / 2 - 1);
    root.right = toBST(nums, (start + end) / 2 + 1, end);
    return root;
}
// 105
public TreeNode buildTree(int[] preorder, int[] inorder) {
   if(preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0)
       return null;
   return binaryTree(preorder, inorder);
}
private TreeNode binaryTree(int[] preorder, int[] inorder) {
   if(preorder.length == 0 || inorder.length == 0)
       return null;
   TreeNode root = new TreeNode(preorder[0]);
   int index = -1;
   for(int i = 0 ; i < inorder.length; i++)
       if(inorder[i] == preorder[0])
           index = i;
   int[] leftInOrder = Arrays.copyOfRange(inorder, 0, index);
   int[] leftPreOrder = Arrays.copyOfRange(preorder, 1, index + 1);
   int[] rightInOrder = Arrays.copyOfRange(inorder, index + 1, inorder.length);
   int[] rightPreOrder = Arrays.copyOfRange(preorder, index + 1, preorder.length);
   root.left = binaryTree(leftPreOrder, leftInOrder);
   root.right = binaryTree(rightPreOrder, rightInOrder);
   return root;
}
// 106
public TreeNode buildTree(int[] inorder, int[] postorder) {
    if(inorder.length == 0 || postorder.length == 0)
        return null;
    return toBinaryTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
}
private TreeNode toBinaryTree(int[] inorder, int[] postorder, int inStart, int inEnd, int postStart, int postEnd){
    if(inStart > inEnd || postStart > postEnd)
        return null;
    TreeNode root = new TreeNode(postorder[postEnd]);
    int index = -1;
    for(int i = 0; i < inorder.length; i++) {
        if(inorder[i] == postorder[postEnd])
            index = i;
    }
    root.left = toBinaryTree(inorder, postorder, inStart, index - 1, postStart, postStart + index - inStart - 1);
    root.right = toBinaryTree(inorder, postorder, index + 1, inEnd, postStart + index - inStart, postEnd - 1);
    return root;
}
// 104
public int maxDepth(TreeNode root) {
    int[] result = new int[1];
    findMaxDepth(root, result, 0);
    return result[0];
}
private void findMaxDepth(TreeNode root, int[] result, int currDep) {
    if(root == null)
        return ;
    currDep += 1;
    if(currDep > result[0])
        result[0] = currDep;
    findMaxDepth(root.left, result, currDep);
    findMaxDepth(root.right, result, currDep);
}
// 101
public boolean isSymmetric(TreeNode root) {
    if(root == null)
        return true;
    return symmetric(root.left, root.right);
}
private boolean symmetric(TreeNode left, TreeNode right) {
    if(left == null || right == null)
        return left == right;
    if(left.val != right.val)
        return false;
    return symmetric(left.left, right.right) && symmetric(left.right, right.left);
}
// 98
public boolean isValidBST(TreeNode root) {
    return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
private boolean isBST(TreeNode root, long min, long max) {
    if(root == null)
        return true;
    if(root.val >= max || root.val <= min)
        return false;
    return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
}
// 99
TreeNode first = null;
TreeNode second = null;
TreeNode prev = new TreeNode(Integer.MIN_VALUE);
public void recoverTree(TreeNode root) {
    traverse(root);
    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}
private void traverse(TreeNode root) {
    if(root == null)
        return ;
    traverse(root.left);
    if(first == null && prev.val >= root.val)
        first = prev;
    if(first != null && prev.val >= root.val)
        second = root;
    prev = root;
    traverse(root.right);
}
// 110
// Top-down
public boolean isBalanced(TreeNode root) {
    if(root == null)
        return true;
    int left = depth(root.left);
    int right = depth(root.right);
    return Math.abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
}
private int depth(TreeNode root) {
    if(root == null)
        return 0;
    return Math.max(depth(root.left), depth(root.right)) + 1;
}
// Bottom-up
public boolean isBalanced(TreeNode root) {
    return dfs(root) != -1;
}
private int dfs(TreeNode root) {
    if(root == null)
        return 0;
    int left = dfs(root.left);
    if(left == -1)
        return -1;
    int right = dfs(root.right);
    if(right == -1)
        return -1;
    if(Math.abs(left - right) > 1)
        return -1;
    return Math.max(left, right) + 1;
}
// 111
public int minDepth(TreeNode root) {
    if(root == null)
        return 0;
    int[] result = new int[1];
    result[0] = Integer.MAX_VALUE;
    findMinDepth(root, 1, result);
    return result[0];
}
private void findMinDepth(TreeNode root, int curr, int[] result) {
    if(root.left == null && root.right == null)
        if(curr < result[0]){
            result[0] = curr;
            return ;
        }
    if(root.left != null)
        findMinDepth(root.left, curr + 1, result);
    if(root.right != null)
        findMinDepth(root.right, curr + 1, result);
}
// 111 more elegant
public static int minDepth(TreeNode root) {
	if (root == null)	return 0;
	if (root.left == null)	return minDepth(root.right) + 1;
	if (root.right == null) return minDepth(root.left) + 1;
	return Math.min(minDepth(root.left),minDepth(root.right)) + 1;
}
// 114
public class Solution {
    TreeNode prev = null;
    public void flatten(TreeNode root) {
        if(root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }
}
// 116 117
public void connect(TreeLinkNode root) {
    if(root == null)
        return;
    Queue<TreeLinkNode> queue = new LinkedList<>();
    queue.add(root);
    while(!queue.isEmpty()){
        int len = queue.size();
        Stack<TreeLinkNode> stack = new Stack<>();
        for(int i = 0; i < len; i++) {
            TreeLinkNode node = queue.poll();
            if(node.left != null)
                queue.add(node.left);
            if(node.right != null)
                queue.add(node.right);
            stack.push(node);
        }
        TreeLinkNode last = null;
        while(!stack.isEmpty()){
            TreeLinkNode node = stack.pop();
            node.next = last;
            last = node;
        }
    }
}
// 117
public void connect(TreeLinkNode root) {
    TreeLinkNode head = null;
    TreeLinkNode prev = null;
    TreeLinkNode cur = root;
    while(cur != null) {
        while(cur != null) {
            if(cur.left != null) {
                if(prev == null) {
                    head = cur.left;
                }
                else {
                    prev.next = cur.left;
                }
                prev = cur.left;
            }
            if(cur.right != null) {
                if(prev == null) {
                    head = cur.right;
                }
                else {
                    prev.next = cur.right;
                }
                prev = cur.right;
            }
            cur = cur.next;
        }
        cur = head;
        head = null;
        prev = null;
    }
}
// 124
public class Solution {
    int maxValue;
    public int maxPathSum(TreeNode root) {
        if(root == null)
            return 0;
        maxValue = Integer.MIN_VALUE;
        findMaxValue(root);
        return maxValue;
    }
    private int findMaxValue(TreeNode root) {
        if(root == null)
            return 0;
        int left = Math.max(0, findMaxValue(root.left));
        int right = Math.max(0, findMaxValue(root.right));
        maxValue = Math.max(maxValue, left + right + root.val);
        return Math.max(left, right) + root.val;
    }
}
// 133
public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
    return cloneTheGraph(node, map);
}
private UndirectedGraphNode cloneTheGraph(UndirectedGraphNode node, HashMap<Integer, UndirectedGraphNode> map) {
    if (node == null)
        return null;
    if(map.containsKey(node.label)) {
        return map.get(node.label);
    }
    else {
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
        map.put(node.label, clone);
        for(int i = 0; i < node.neighbors.size(); i++) {
            clone.neighbors.add(cloneTheGraph(node.neighbors.get(i), map));
        }
        return clone;
    }
}
