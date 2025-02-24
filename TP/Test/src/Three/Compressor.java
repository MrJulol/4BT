package Three;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;

public class Compressor {
    private final ExecutorService executorService;
    private final Path sourceDir;
    private final Path zipFile;
    private final JProgressBar progressBar;

    public Compressor(Path sourceDir, Path zipFile, int threadCount, JProgressBar progressBar) {
        this.sourceDir = sourceDir;
        this.zipFile = zipFile;
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.progressBar = progressBar;
    }

    public void compress() throws IOException, InterruptedException {
        List<Path> files = Files.walk(sourceDir)
                .filter(Files::isRegularFile)
                .toList();

        progressBar.setMaximum(files.size());

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            zos.setLevel(Deflater.BEST_COMPRESSION); 
            List<Future<Object>> futures = files.stream()
                    .map(file -> executorService.submit(() -> {
                        compressFile(file, zos);
                        SwingUtilities.invokeLater(() -> progressBar.setValue(progressBar.getValue() + 1));
                        return null;
                    }))
                    .toList();

            for (Future<Object> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

    private void compressFile(Path file, ZipOutputStream zos) throws IOException {
        String zipEntryName = sourceDir.relativize(file).toString();
        synchronized (zos) {
            zos.putNextEntry(new ZipEntry(zipEntryName));
            Files.copy(file, zos);
            zos.closeEntry();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Compressor");

        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 100);

            JPanel panel = new JPanel();
            JProgressBar progressBar = new JProgressBar(0, 100);
            panel.add(progressBar);
            frame.add(panel);

            frame.setVisible(true);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Path sourceDir = fileChooser.getSelectedFile().toPath();
                Path zipFile = Paths.get(sourceDir.toString() + ".zip");

                int threadCount = Runtime.getRuntime().availableProcessors();

                Compressor compressor = new Compressor(sourceDir, zipFile, threadCount, progressBar);
                new Thread(() -> {
                    try {
                        compressor.compress();
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(frame, "Compression completed!");
                            frame.dispose();
                        });
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }
}