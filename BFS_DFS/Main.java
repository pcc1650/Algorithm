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
// 332
class Solution {
    HashMap<String, PriorityQueue<String>> targets = new HashMap<>();
    List<String> route = new LinkedList<>();
    public List<String> findItinerary(String[][] tickets) {
        for(String[] ticket : tickets){
            if(targets.containsKey(ticket[0])){
                targets.get(ticket[0]).add(ticket[1]);
            }
            else{
                PriorityQueue<String> tempPQ = new PriorityQueue<>();
                tempPQ.add(ticket[1]);
                targets.put(ticket[0], tempPQ);
            }
        }
        DFS("JFK");
        return route;
    }
    private void DFS(String airport) {
        while(targets.containsKey(airport) && ! targets.get(airport).isEmpty())
            DFS(targets.get(airport).poll());
        route.add(0, airport);
    }
}
// 473
class Solution {
    public boolean makesquare(int[] nums) {
        if(nums == null || nums.length < 4)
            return false;
        int sum = 0;
        Arrays.sort(nums);
        int[] newNums = reverse(nums);
        for(int num : newNums)
            sum += num;
        if(sum % 4 != 0)
            return false;
        return dfs(newNums, new int[4], 0, sum / 4);
    }
    private boolean dfs(int[] nums, int[] sum, int index, int target) {
        if(index == nums.length) {
            if(sum[0] == target && sum[1] == target && sum[2] == target)
                return true;
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(nums[index] + sum[i] > target)
                continue;
            sum[i] += nums[index];
            if(dfs(nums, sum, index + 1, target))
                return true;
            sum[i] -= nums[index];
        }
        return false;
    }
    private int[] reverse(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
        return nums;
    }
}
// 491
public List<List<Integer>> findSubsequences(int[] nums) {
    Set<List<Integer>> res = new HashSet<>();
    List<Integer> holder = new LinkedList<>();
    findSequence(nums, 0, holder, res);
    List result = new LinkedList(res);
    return result;
}
private void findSequence(int[] nums, int index, List<Integer> holder, Set<List<Integer>> res) {
    if(holder.size() >= 2)
        res.add(new LinkedList(holder));
    for(int i = index; i < nums.length; i++) {
        if(holder.size() == 0 || holder.get(holder.size() - 1) <= nums[i]) {
            holder.add(nums[i]);
            findSequence(nums, i + 1, holder, res);
            holder.remove(holder.size() - 1);
        }
    }
}
// 542
public int[][] updateMatrix(int[][] matrix) {
    if(matrix.length == 0 || matrix[0].length == 0)
        return null;
    int m = matrix.length;
    int n = matrix[0].length;
    Queue<int[]> queue = new LinkedList<>();
    for(int i = 0; i < m; i++) {
        for(int j = 0; j < n; j++){
            if(matrix[i][j] == 0){
                queue.add(new int[]{i, j});
            }
            else{
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    while(!queue.isEmpty()) {
        int[] temp = queue.poll();
        for(int[] dir: directions) {
            int i = temp[0] + dir[0];
            int j = temp[1] + dir[1];
            if(i < 0 || i >= m || j < 0 || j >= n || matrix[i][j] <= matrix[temp[0]][temp[1]])
                continue;
            queue.add(new int[]{i, j});
            matrix[i][j] = matrix[temp[0]][temp[1]] + 1;
        }
    }
    return matrix;
}
// 638
public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
    return shopping(price, special, needs);
}
private int shopping(List<Integer> price, List<List<Integer>> special, List<Integer> needs){
    int j = 0;
    int res = noOffers(price, needs);
    for(List<Integer> s : special) {
        List<Integer> newNeeds = new LinkedList<>(needs);
        for(j = 0; j < needs.size(); j++) {
            int diff = needs.get(j) - s.get(j);
            if(diff < 0)
                break;
            newNeeds.set(j, diff);
        }
        if(j == needs.size()){
            res = Math.min(res, s.get(s.size() - 1) + shopping(price, special, newNeeds));
        }
    }
    return res;
}
private int noOffers(List<Integer> prices, List<Integer> needs) {
    int res = 0;
    for(int i = 0; i < prices.size(); i++)
        res += prices.get(i) * needs.get(i);
    return res;
}
// 576
class Solution {
    int M = 1000000007;
    public int findPaths(int m, int n, int N, int i, int j) {
        int[][][] memos = new int[m][n][N + 1];
        for(int[][] l : memos) {
            for(int[] sl : l){
                Arrays.fill(sl, -1);
            }
        }
        return findPaths(m, n, N, i, j, memos);
    }
    private int findPaths(int m, int n, int N, int i, int j, int[][][] memos) {
        if(i < 0 || j < 0 || i == m || j == n)
            return 1;
        if(N == 0)
            return 0;
        if(memos[i][j][N] >= 0)
            return memos[i][j][N];
        memos[i][j][N] = ((findPaths(m,n,N-1,i-1,j,memos)+findPaths(m,n,N-1,i,j + 1,memos))%M+(findPaths(m,n,N-1,i,j-1,memos)+findPaths(m,n,N-1,i + 1,j,memos))%M)%M;
        return memos[i][j][N];
    }
}
// 546
public int removeBoxes(int[] boxes) {
    int n = boxes.length;
    int[][][] dp = new int[n][n][n];
    return removeBoxesSub(boxes, 0, n - 1, 0, dp);
}
private int removeBoxesSub(int[] boxes, int i, int j, int k, int[][][] dp) {
    if(i > j)
        return 0;
    if(dp[i][j][k] > 0)
        return dp[i][j][k];
    for(; i + 1 <= j && boxes[i + 1] == boxes[i]; i++, k++);
    int res = (k + 1) * (k + 1) + removeBoxesSub(boxes, i + 1, j, 0, dp);
    for(int m = i + 1; m <= j; m++) {
        if(boxes[i] == boxes[m]){
            res = Math.max(res, removeBoxesSub(boxes, i + 1, m - 1, 0, dp) + removeBoxesSub(boxes, m, j, k + 1, dp));
        }
    }
    dp[i][j][k] = res;
    return res;
}
// 488
class Solution {
    int MAXCOUNT = 6;
    public int findMinStep(String board, String hand) {
        int[] handCount = new int[26];
        for(int i = 0; i < hand.length(); i++)
            handCount[hand.charAt(i) - 'A'] += 1;
        int res = helper(board + "#", handCount);
        return res == MAXCOUNT ? -1 : res;
    }
    private int helper(String s, int[] h) {
        s = removeConsecutive(s);
        if(s.equals("#"))
            return 0;
        int res = MAXCOUNT, need = 0;
        for(int i = 0, j = 0; j < s.length(); j++) {
            if(s.charAt(i) == s.charAt(j))
                continue;
            need = 3 - (j - i);
            if(h[s.charAt(i) - 'A'] >= need){
                h[s.charAt(i) - 'A'] -= need;
                res = Math.min(res, need + helper(s.substring(0, i) + s.substring(j), h));
                h[s.charAt(i) - 'A'] += need;
            }
            i = j;
        }
        return res;
    }
    private String removeConsecutive(String s) {
        for(int i = 0, j = 0; j < s.length(); j++) {
            if(s.charAt(i) == s.charAt(j))
                continue;
            if(j - i >= 3)
                return removeConsecutive(s.substring(0, i) + s.substring(j));
            else
                i = j;
        }
        return s;
    }
}
// 664
public int strangePrinter(String s) {
    int n = s.length();
    if(n == 0) return 0;
    int[][] dp = new int[n][n];
    
    for(int i = 0; i < n; i++)
        dp[i][i] = 1;
    
    for(int i = 1; i < n; i++) {
        for(int j = 0; j < n - i; j++){
            dp[j][j + i] = i + 1;
            for(int k = j + 1; k <= j + i; k++) {
                int temp = dp[j][k - 1] + dp[k][j + i];
                if(s.charAt(k - 1) == s.charAt(j + i))
                    temp -= 1;
                dp[j][j + i] = Math.min(dp[j][j + i], temp);
            }
        }
    }
    return dp[0][n - 1];
// 301
public List<String> removeInvalidParentheses(String s) {
    List<String> res = new LinkedList<>();
    remove(s, res, 0, 0, new char[]{'(', ')'});
    return res;
}
private void remove(String s, List<String> res, int last_i, int last_j, char[] par) {
    for(int stack = 0, i = last_i; i < s.length(); i++) {
        if(s.charAt(i) == par[0])
            stack += 1;
        if(s.charAt(i) == par[1])
            stack -= 1;
        if(stack >= 0)
            continue;
        for(int j = last_j; j <= i; j++) {
            if(s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])){
                remove(s.substring(0, j) + s.substring(j + 1), res, i, j, par);
            }
        }
        return;
    }
    String reversed = new StringBuilder(s).reverse().toString();
    if(par[0] == '(')
        remove(reversed, res, 0, 0, new char[]{')', '('});
    else
        res.add(reversed);
}
// 329
public int longestIncreasingPath(int[][] matrix) {
    if(matrix.length == 0)
        return 0;
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int[][] memo = new int[m][n];
    int max = 1;
    for(int i = 0; i < m; i++) {
        for(int j = 0; j < n; j++){
            max = Math.max(max, dfs(matrix, i, j, m, n, dirs, memo));
        }
    }
    return max;
}
private int dfs(int[][] matrix, int i, int j, int m, int n, int[][] dirs, int[][] memo) {
    if(memo[i][j] > 0)
        return memo[i][j];
    int max = 1;
    for(int[] dir : dirs) {
        int x = i + dir[0];
        int y = j + dir[1];
        if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j])
            continue;
        max = Math.max(max, 1 + dfs(matrix, x, y, m, n, dirs, memo));
    }
    memo[i][j] = max;
    return max;
}
// 102
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if(root == null)
        return res;
    queue.add(root);
    while(!queue.isEmpty()) {
        List<Integer> ans = new LinkedList<>();
        int size = queue.size();
        for(int i = 0; i < size; i++) {
            TreeNode tree = queue.poll();
            ans.add(tree.val);
            if(tree.left != null)
                queue.add(tree.left);
            if(tree.right != null)
                queue.add(tree.right);
        }
		// res.add(0, ans) if from bottom
        res.add(ans);
    }
    return res;
}
// 103
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    int zag = 0;
    if(root == null)
        return res;
    queue.add(root);
    while(!queue.isEmpty()) {
        List<Integer> ans = new LinkedList<>();
        int size = queue.size();
        for(int i = 0; i < size; i++) {
            TreeNode tree = queue.poll();
            if(zag % 2 == 1)
                ans.add(0, tree.val);
            else
                ans.add(tree.val);
            if(tree.left != null)
                queue.add(tree.left);
            if(tree.right != null)
                queue.add(tree.right);
        }
        res.add(ans);
        zag = zag == 0 ? 1 : 0;
    }
    return res;
}
// 130
public void solve(char[][] board) {
    if(board == null || board.length == 0 || board[0].length == 0)
        return;
    int m = board.length, n = board[0].length;
    Queue<int[]> queue = new LinkedList<>();
    for(int i = 0; i < m; i++){
        if(board[i][0] == 'O'){
            queue.add(new int[]{i, 0});
        }
        if(board[i][n - 1] == 'O'){
            queue.add(new int[]{i, n - 1});
        }
    }
    for(int j = 0; j < n; j++) {
        if(board[0][j] == 'O'){
            queue.add(new int[]{0, j});
        }
        if(board[m - 1][j] == 'O'){
            queue.add(new int[]{m - 1, j});
        }
    }
    while(!queue.isEmpty()){
        int[] point = queue.poll();
        int i = point[0];
        int j = point[1];
        if(board[i][j] != 'O')
            continue;
        else{
            board[i][j] = 'Y';
            if(i - 1 >= 0 && board[i - 1][j] == 'O')
                queue.add(new int[]{i - 1, j});
            if(i + 1 < m && board[i + 1][j] == 'O')
                queue.add(new int[]{i + 1, j});
            if(j - 1 >= 0 && board[i][j - 1] == 'O')
                queue.add(new int[]{i, j - 1});
            if(j + 1 < n && board[i][j + 1] == 'O')
                queue.add(new int[]{i, j + 1});
        }
    }
    for(int i = 0; i < m; i++) {
        for(int j = 0; j < n; j++) {
            if(board[i][j] == 'O')
                board[i][j] = 'X';
            if(board[i][j] == 'Y')
                board[i][j] = 'O';
        }
    }
}
// 310
// ArrayList is faster than LinkedList in this case
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if(n == 1){
        List<Integer> res = new ArrayList<>();
        res.add(0);
        return res;
    }
    List<List<Integer>> list = new ArrayList<>();
    for(int i = 0; i < n; i++)
        list.add(new ArrayList<Integer>());
    for(int i = 0; i < edges.length; i++) {
        int[] edge = edges[i];
        list.get(edge[0]).add(edge[1]);
        list.get(edge[1]).add(edge[0]);
    }
    List<Integer> leaves = new ArrayList<>();
    for(int i = 0; i < n; i++) {
        if(list.get(i).size() == 1)
            leaves.add(i);
    }
    while(n > 2){
        n -= leaves.size();
        List<Integer> newLeaves = new ArrayList<>();
        for(int i = 0; i < leaves.size(); i++) {
            int leave = leaves.get(i);
            int other = list.get(leave).get(0);
            list.get(other).removeIf(p -> p == leave);
            if(list.get(other).size() == 1)
                newLeaves.add(other);
        }
        leaves = newLeaves;
    }
    return leaves;
}
public int trapRainWater(int[][] heightMap) {
    class Cell {
        int x;
        int y;
        int h;
        public Cell(int x, int y, int h){
            this.x = x;
            this.y = y;
            this.h = h;
        }
    }
    if(heightMap == null || heightMap.length == 0 || heightMap[0].length == 0)
        return 0;
    int rows = heightMap.length;
    int cols = heightMap[0].length;
    Queue<Cell> minHeap = new PriorityQueue<>(rows * cols, (c1, c2) -> c1.h - c2.h);
    boolean[][] visited = new boolean[rows][cols];
    for(int i = 0; i < rows; i++) {
        visited[i][0] = true;
        visited[i][cols - 1] = true;
        minHeap.offer(new Cell(i, 0, heightMap[i][0]));
        minHeap.offer(new Cell(i, cols - 1, heightMap[i][cols - 1]));
    }
    for(int i = 0; i < cols; i++) {
        visited[0][i] = true;
        visited[rows - 1][i] = true;
        minHeap.offer(new Cell(0, i, heightMap[0][i]));
        minHeap.offer(new Cell(rows - 1, i, heightMap[rows - 1][i]));
    }
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int res = 0;
    while(!minHeap.isEmpty()){
        Cell cell = minHeap.poll();
        for(int[] dir: dirs) {
            int row = cell.x + dir[0];
            int col = cell.y + dir[1];
            if(row >= 0 && row < rows && col >= 0 && col < cols && !visited[row][col]){
                visited[row][col] = true;
                res += Math.max(0, cell.h - heightMap[row][col]);
                minHeap.offer(new Cell(row, col, Math.max(heightMap[row][col], cell.h)));
            }
        }
    }
    return res;
}
public int cutOffTree(List<List<Integer>> forest) {
    if(forest == null || forest.size() == 0 || forest.get(0).size() == 0)
        return -1;
    int m = forest.size();
    int n = forest.get(0).size();
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for(int i = 0; i < m; i++){
        for(int j = 0; j < n; j++) {
            if(forest.get(i).get(j) > 0)
                pq.add(new int[]{i, j, forest.get(i).get(j)});
        }
    }
    int[] start = new int[]{0, 0};
    int res = 0;
    while(!pq.isEmpty()){
        int[] tree = pq.poll();
        int step = minStep(forest, start, tree, m, n, dirs);
        if(step < 0)
            return -1;
        res += step;
        start[0] = tree[0];
        start[1] = tree[1];
    }
    return res;
}
private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n, int[][] dirs){
    int step = 0;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    queue.add(start);
    visited[start[0]][start[1]] = true;
    while(!queue.isEmpty()){
        int size = queue.size();
        for(int i = 0; i < size; i++){
            int[] curr = queue.poll();
            if(curr[0] == tree[0] && curr[1] == tree[1])
                return step;
            for(int[] dir: dirs) {
                int nr = curr[0] + dir[0];
                int nc = curr[1] + dir[1];
                if(nr < 0 || nr >= m || nc <0 || nc >=n || visited[nr][nc] || forest.get(nr).get(nc) == 0)
                    continue;
                visited[nr][nc] = true;
                queue.add(new int[]{nr, nc});
            }
        }
        step += 1;
    }
    return -1;
}
// add 475
public int findRadius(int[] houses, int[] heaters) {
    int res = 0;
    Arrays.sort(heaters);
    for(int house: houses) {
        int index = Arrays.binarySearch(heaters, house);
        if(index < 0) {
            index = -(index + 1);
        }
        int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
        int dist2 = index < heaters.length ? heaters[index] - house: Integer.MAX_VALUE;
        res = Math.max(res, Math.min(dist1, dist2));
    }
    return res;
}
// 441
public int arrangeCoins(int n) {
    int left = 0;
    int right = n;
    while(left <= right){
        long mid = left + (right - left) / 2;
        if((1 + mid) * mid >>> 1 <= n && (2 + mid) * (mid + 1) >>> 1 > n)
            return (int)mid;
        else if((1 + mid) * mid >>> 1 <= n)
            left = (int)mid + 1;
        else 
            right = (int)mid - 1;
    }
    return -1;
}
// 69
public int mySqrt(int x) {
    if(x == 0)
        return 0;
    if(x == 1)
        return 1;
    int left = 1; 
    int right = x / 2;
    while(left <= right) {
        long mid = left + (right - left) / 2;
        if(mid * mid <= x && (mid + 1) * (mid + 1) > x)
            return (int)mid;
        else if(mid * mid < x) {
            left = (int)mid + 1;
        }
        else
            right = (int)mid - 1;
    }
    return -1;
}
// 367
public boolean isPerfectSquare(int num) {
    if(num == 1)
        return true;
    int left = 1;
    int right = num / 2;
    while(left <= right) {
        long mid = left + (right - left) / 2;
        if(mid * mid == num)
            return true;
        else if(mid * mid > num) {
            right = (int)mid - 1;
        }
        else
            left = (int)mid + 1;
    }
    return false;
}
