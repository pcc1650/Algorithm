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
