package runninghorse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility to load ASCII-art animation frames from a single text file on the classpath.
 */
public final class FrameFileLoader {

    /**
     * Default separator line that marks the beginning of a new frame.
     */
    public static final String DEFAULT_FRAME_SEPARATOR = "--FRAME--";

    private FrameFileLoader() {
        // utility class
    }

    /**
     * Load frames from a UTF-8 text resource on the classpath.
     *
     * @param resourcePath path passed to {@link Class#getResourceAsStream(String)},
     *
     * @return immutable list of frame strings, in order.
     * @throws IOException if the resource cannot be found or read
     */
    public static List<String> loadFramesFromResource(String resourcePath) throws IOException {
        return loadFramesFromResource(resourcePath, DEFAULT_FRAME_SEPARATOR);
    }

    /**
     * Load frames from a UTF-8 text resource on the classpath, using a custom separator line.
     *
     * @param resourcePath   path passed to {@link Class#getResourceAsStream(String)},
     *
     * @param frameSeparator line that marks the start of a new frame (compared using {@link String#equals(Object)}).
     * @return immutable list of frame strings, in order.
     * @throws IOException if the resource cannot be found or read
     */
    public static List<String> loadFramesFromResource(String resourcePath, String frameSeparator) throws IOException {
        if (resourcePath == null) {
            throw new IllegalArgumentException("resourcePath must not be null");
        }
        if (frameSeparator == null || frameSeparator.isEmpty()) {
            throw new IllegalArgumentException("frameSeparator must not be null or empty");
        }

        InputStream in = FrameFileLoader.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IOException("Resource not found on classpath: " + resourcePath);
        }

        List<String> frames = new ArrayList<>();
        StringBuilder currentFrame = null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(frameSeparator)) {
                    // Start a new frame
                    if (currentFrame != null && currentFrame.length() > 0) {
                        frames.add(currentFrame.toString());
                    }
                    currentFrame = new StringBuilder();
                } else {
                    if (currentFrame == null) {
                        // If file doesn't start with separator, implicitly start first frame
                        currentFrame = new StringBuilder();
                    }
                    currentFrame.append(line).append('\n');
                }
            }
        }

        if (currentFrame != null && currentFrame.length() > 0) {
            frames.add(currentFrame.toString());
        }

        return Collections.unmodifiableList(frames);
    }
}






