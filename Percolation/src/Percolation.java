

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {

    private static final int CLOSED=0;
    private static final int OPEN = 1;
    private int openSiteNumber;
    private final int size;
    private int grid[][];

    private final WeightedQuickUnionUF perc;
    private final WeightedQuickUnionUF full;
    // создает сетку n-на-n, при этом все сайты изначально блокируются

    public Percolation(int n){
        size = n;
        openSiteNumber = 0;

        perc = new WeightedQuickUnionUF(n*n+2);
        full = new WeightedQuickUnionUF(n*n+1);

        grid = new int[n][n];

        for(int i=0;i<n;i++ ) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = CLOSED;
            }
        }

    }
    // открывает сайт (row, col), если он еще не открыт
        public  void open (int row, int col ) {
            if (row < 1 || row > size || col < 1 || col > size) throw new IndexOutOfBoundsException();

            if (isOpen(row, col)) {
                return;
            } else {
                openSiteNumber++;
                grid[row - 1][col - 1] = OPEN;
                if (col != 1)
                    con(row, col, row, (col - 1));
                if (col != size)
                    con(row, col, row, (col + 1));
                if (row != 1) {
                    con(row, col, (row - 1), col);
                }
                else{
                    perc.union(g2p(row,col),0);
                    full.union(g2p(row,col),0);
                }
                if (row!=size)
                    con(row,col,(row+1),col);
                else
                    perc.union(g2p(row,col),(size*size+1));

            }
        }
        // сайт (строка, столбец) открыт?
        public boolean isOpen (int row, int col){
            if (row < 1 || row > size || col < 1 || col > size) throw new IndexOutOfBoundsException();
                if (grid[(row-1)][(col-1)]==OPEN)
                    return true;
                else
                    return false;
        }
        // заполнен ли сайт (row, col)?
        public boolean isFull (int row, int col){
            if (row < 1 || row > size || col < 1 || col > size) throw new IndexOutOfBoundsException();
            return full.connected(0,g2p(row,col));

        }
        // возвращает количество открытых сайтов
        public int numberOfOpenSites (){
            return openSiteNumber;

        } // система просачивается?
        public boolean percolates (){
            return perc.connected(0,size*size+1);
        } // тестовый клиент (необязательно)

        //преобразует координаты сетки в индекс структуры UF
        private int g2p(int row, int col){
            return (row-1)*size + (col-1) + 1;
        }
        private void con(int pi, int pj, int qi, int qj) {
            //убедитесь, что оба сайта открыты
            if( isOpen(pi,pj) && isOpen(qi,qj) ) {
                perc.union(g2p(pi,pj),g2p(qi,qj));
                full.union(g2p(pi,pj),g2p(qi,qj));
            }
        }
        public static void main (String [] args){
            int n = StdIn.readInt();
            int row, col;
            Percolation percolation = new Percolation(n);
            while (!StdIn.isEmpty()) {
                row = StdIn.readInt();
                col = StdIn.readInt();
                percolation.open(row, col);
            }

            StdOut.print(percolation.percolates());
        }
}

