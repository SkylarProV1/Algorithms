
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresh;
    private double av, sd;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        thresh = new double[trials];
        for (int i = 0; i < trials; i++) {
            thresh[i] = perc(n);
        }
    }
    private double perc(int n){
        Percolation p = new Percolation(n);
        int row,col;
        int count = 0;
        while(!p.percolates()){
            row=StdRandom.uniform(n)+1;
            col=StdRandom.uniform(n)+1;
            if (!p.isOpen(row,col)){
                p.open(row,col);
                count++;
            }
        }
        return (double)count/(double)(n*n);
    }
    // sample mean of percolation threshold
    public double mean(){
        double count = 0;
        for(int i =0; i<thresh.length;i++){
            count+=thresh[i];
        }
        av=count/thresh.length;
        return av;
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        double diff=0;
        for (int i=0;i<thresh.length;i++){
            diff+=Math.pow((thresh[i]-av),2);
        }
        sd=Math.sqrt(diff/(thresh.length-1));
        return sd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return av - ( (1.96 * sd) / Math.sqrt(thresh.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return av + ( (1.96 * sd) / Math.sqrt(thresh.length));
    }

    // test client (see below)
    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        //Stopwatch clock = new Stopwatch();
        PercolationStats ps = new PercolationStats(N,T);
        //double time = clock.elapsedTime();

        System.out.println("mean:\t\t\t\t" + ps.mean());
        System.out.println("stddev:\t\t\t\t" + ps.stddev());
        System.out.println("95% confidence interval:\t\t" + ps.confidenceLo() + ", " + ps.confidenceHi());
        //System.out.println("elapsed time:\t\t\t" + time + "s");
    }
}
