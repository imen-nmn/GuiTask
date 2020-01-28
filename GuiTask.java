import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class GuiTask {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter the fileName");
            return;
        }
        String fileName = args[0];
        System.out.println("----- File : " + fileName);

        try {
            String output = executeJob(fileName);
            System.out.println("----- Output : \n" + output);
        } catch (Exception e) {
            System.out.println("----- Error when trying to read the file --- Details :  " + e);
        }
    }

    private static String executeJob(String fileName) throws Exception {
        String output;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            //Read the file and its elements appropriately
            String line = br.readLine();
            //Create a counter accessible by all threads, initialized by 0.
            Counter counter = new Counter();
            int totalThreadNumber = Integer.parseInt(line);
            System.out.println("----- Number of thread : " + totalThreadNumber);
            ExecutorService pool = Executors.newSingleThreadExecutor();
            GeneratorThread[] threads = new GeneratorThread[totalThreadNumber];
            //Create N threads, one assigned to each column.
            for (int i = 0; i < totalThreadNumber; i++) {
                threads[i] = new GeneratorThread(i + 1, counter);
            }
            while (line != null) {
                line = line.replace("|", "");
                int threadNumber = line.indexOf('X');
                if (threadNumber != -1) {
                    int generatedNumber = 0;
            //For each ‘X’ on each line from top to bottom, run the task in its thread.
                    pool.submit(threads[threadNumber]);
                }
                line = br.readLine();
            }
            pool.shutdown();
            pool.awaitTermination(60, TimeUnit.SECONDS);
            //Print the counter value in main thread
            String totalLine = "TOTAL: " + counter.getVal();
            sb.append(totalLine);
            output = sb.toString();
        } finally {
            br.close();
        }
        return output;
    }
}
