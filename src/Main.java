import uitls.FileCopyClass;
import uitls.ProgressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
2) Реализуйте программу многопоточного копирования файла
блоками, с выводом прогресса на экран.
*/
public class Main {
    public static void main(String[] args) {
        String srcFile = "srcfolder\\poi-bin-5.0.0-20210120.zip";
        String destFile = "destfolder\\qwe.zip";
        ThreadGroup tg1 = new ThreadGroup("my1");

        File f = new File(srcFile);
        long lengthSrcfile = f.length();
        System.out.println("Length of file  f " + lengthSrcfile);
        ProgressListener progressListener = new ProgressListener(lengthSrcfile);
        // делю файл на три части
        long part1 = (lengthSrcfile) / 3;
        long part2 = (lengthSrcfile) * 2 / 3;
        long part3 = (lengthSrcfile) * 2 / 3 + 1; // для выполнения условия чтобы r>0 cработало
        System.out.println(part1 + " " + part2 + " " + part3);
        long[] partsdata = {0, part1, part2, part3};// массив из которого беру данные с какого места по какое копировать
        long end; // до которого копировать
        int k = 1;//индекс для end
        long timeStart = System.currentTimeMillis();// начинаем отчёт
        FileCopyClass c = null;
        List<FileCopyClass> list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                k = 2;
            }
            if (i == 2) {
                k = 3;
            }
            long start = partsdata[i];
            end = partsdata[k];

            c = new FileCopyClass(tg1, " ***Thread-" + i,
                    srcFile, destFile, start, end, part2, progressListener);
            list.add(c);
        }
        for (FileCopyClass cc : list) {
            cc.start();
        }
        for (FileCopyClass cc : list) {
            try {
                cc.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long time = System.currentTimeMillis() - timeStart;
        System.out.println();
        System.out.println("Writing time: " + time + " millisec");
        File f1 = new File("D:\\Java\\Catalogs\\Catalog1\\qwe.zip");
        System.out.println("Length of file f1  " + f1.length());
    }

}
