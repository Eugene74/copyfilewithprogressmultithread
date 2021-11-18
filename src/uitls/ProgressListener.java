package uitls;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;

public class ProgressListener {
    private long lengthSrcfile;
    private AtomicLong percent = new AtomicLong(0);
    private double stepPrintprogess = 5d;


    public ProgressListener(long lengthSrcfile) {
        this.lengthSrcfile = lengthSrcfile;
    }

    public synchronized void setProgress(long r) {
        percent.getAndAdd(r);
        double x = (percent.get() * 100) / lengthSrcfile;
        if (x >= stepPrintprogess) {
            printProgress(x);
            stepPrintprogess += 5d;
        }
    }

    private void printProgress(double x) {
        DecimalFormat f = new DecimalFormat("##.0");
        System.out.print(" " + f.format(x) + "% ");
    }
}
