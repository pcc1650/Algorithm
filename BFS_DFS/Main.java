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
