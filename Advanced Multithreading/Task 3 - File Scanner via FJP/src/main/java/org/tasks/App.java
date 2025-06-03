package org.tasks;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hello world!
 *
 */
public class App 
{
    //Run command mvn clean package
    //Then run the jar file with the command: java /target/task3-1.0-SNAPSHOT.jar /path/to/folder
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FolderScanner <folder_path>");
            System.exit(1);
        }

        File folder = new File(args[0]);
        if (!folder.isDirectory()) {
            System.err.println("The specified path is not a directory.");
            System.exit(1);
        }

        AtomicBoolean interrupted = new AtomicBoolean(false);

        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] spinner = {"|", "/", "-", "\\"};
                int spinnerIndex = 0;

                while (!Thread.interrupted()) {
                    System.out.print("\rScanning " + spinner[spinnerIndex++ % spinner.length]);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });

        Thread interruptThread = new Thread(() -> {
            try {
                System.in.read();
                interrupted.set(true);
            } catch (Exception ignored) {}
        });

        ForkJoinPool pool = new ForkJoinPool();
        FolderScanner.FolderScanTask task = new FolderScanner.FolderScanTask(folder, interrupted);

        progressThread.start();
        interruptThread.start();

        FolderScanner.FolderStatistics stats = pool.invoke(task);

        progressThread.interrupt();
        try {
            progressThread.join();
        } catch (InterruptedException ignored) {}

        if (interrupted.get()) {
            System.out.println("\nScanning interrupted!");
        } else {
            System.out.println("\n" + stats);
        }
    }
}
