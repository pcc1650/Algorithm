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
    boolean flag = false;
    for(int i = 0; i < succs[index].size(); i++) {
        boolean[] flags = Arrays.copyOf(partialFlag, partialFlag.length);          
        if(partialFlag[succs[index].get(i)])
            return true;
        if(hasCycle(succs[index].get(i), succs, flags, globalFlag))
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
