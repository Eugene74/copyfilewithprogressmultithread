package uitls;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileCopyClass extends Thread {

    String src, dest;
    long end, n, n1;
    ProgressListener progressListener;

    public FileCopyClass(ThreadGroup tg1, String name, String src, String dest, long start, long end, long n1, ProgressListener progressListener) {
        super(tg1, name);
        this.src = src;
        this.dest = dest;
        this.end = end;
        this.n = start;
        this.n1 = n1;
        this.progressListener = progressListener;
    }

    public void run() {
        try {
            System.out.println("start");

            copyFile();
        } catch (IOException e) {
            return;
        }
    }

    public void copyFile() throws IOException {
        RandomAccessFile in = new RandomAccessFile(src, "r");
        try {
            RandomAccessFile out = new RandomAccessFile(dest, "rw");
            try {
                int b = 10024;// буфер обмена
                byte[] buf = new byte[b];
                int r;
                long currentFilepositon = 0;
                in.seek(n);
                out.seek(n);
                do {
                    r = in.read(buf, 0, buf.length);
                    if (r > 0) {
                        out.write(buf, 0, r);
                        progressListener.setProgress(r);
                    }
                    currentFilepositon = out.getFilePointer();
                    //как только end станет n1 сразу сработает условие r>0, то ест до конца файла копируем
                } while (end <= n1 ? currentFilepositon <= end : r > 0);

            } finally {
               // System.out.println("finish");
                out.close();
            }
        } finally {
            in.close();
        }
    }

}

