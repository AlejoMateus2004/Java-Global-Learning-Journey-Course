package org.tasks;

import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class FolderScanner {
    static class FolderScanTask extends RecursiveTask<FolderStatistics> {
        private final File folder;
        private final AtomicBoolean interrupted;

        public FolderScanTask(File folder, AtomicBoolean interrupted) {
            this.folder = folder;
            this.interrupted = interrupted;
        }

        @Override
        protected FolderStatistics compute() {
            if (interrupted.get()) return new FolderStatistics();

            FolderStatistics stats = new FolderStatistics();
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (interrupted.get()) return stats;
                    if (file.isFile()) {
                        stats.incrementFileCount();
                        stats.addToTotalSize(file.length());
                    } else if (file.isDirectory()) {
                        stats.incrementFolderCount();
                        FolderScanTask task = new FolderScanTask(file, interrupted);
                        task.fork();
                        stats.combine(task.join());
                    }
                }
            }
            return stats;
        }
    }

    static class FolderStatistics {
        private long fileCount;
        private long folderCount;
        private long totalSize;

        public synchronized void incrementFileCount() {
            fileCount++;
        }

        public synchronized void incrementFolderCount() {
            folderCount++;
        }

        public synchronized void addToTotalSize(long size) {
            totalSize += size;
        }

        public long getFileCount() {
            return fileCount;
        }

        public long getFolderCount() {
            return folderCount;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public synchronized void combine(FolderStatistics other) {
            this.fileCount += other.fileCount;
            this.folderCount += other.folderCount;
            this.totalSize += other.totalSize;
        }

        @Override
        public String toString() {
            return String.format("Files: %d | Folders: %d | Total size: %d bytes", fileCount, folderCount, totalSize);
        }
    }
}