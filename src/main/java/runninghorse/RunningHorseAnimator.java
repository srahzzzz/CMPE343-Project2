package runninghorse;

import util.ColorUtils;

import java.util.List;

/**
 * Simple terminal-based ASCII horse animator.
 */
public final class RunningHorseAnimator {

    private final List<String> frames;
    private final long frameDelayMillis;
    private final boolean clearEachFrame;
    private final boolean useCursorRestore;
    private final String horseColor;
    private volatile boolean running = true;

    /**
     * Create a new animator that clears the screen before each frame.
     *
     * @param frames           ordered list of ASCII frames; each entry is printed as-is.
     * @param frameDelayMillis delay between frames in milliseconds
     * @param horseColor       ANSI color code for the entire horse
     */
    public RunningHorseAnimator(List<String> frames, long frameDelayMillis, String horseColor) {
        this(frames, frameDelayMillis, true, false, horseColor);
    }

    /**
     * Create a new animator with optional screen clearing.
     *
     * @param frames           ordered list of ASCII frames
     * @param frameDelayMillis delay between frames
     * @param clearEachFrame   whether to clear the terminal before each frame
     * @param horseColor       ANSI color code for the entire horse
     */
    public RunningHorseAnimator(List<String> frames, long frameDelayMillis, boolean clearEachFrame, String horseColor) {
        this(frames, frameDelayMillis, clearEachFrame, false, horseColor);
    }

    /**
     * Create a new animator with optional screen clearing and cursor restore behavior.
     *
     * @param frames           ordered list of ASCII frames
     * @param frameDelayMillis delay between frames
     * @param clearEachFrame   whether to clear the terminal before each frame
     * @param useCursorRestore if true, uses ANSI save
     * @param horseColor       ANSI color code for the entire horse
     */
    public RunningHorseAnimator(List<String> frames,
                                long frameDelayMillis,
                                boolean clearEachFrame,
                                boolean useCursorRestore,
                                String horseColor) {
        if (frames == null || frames.isEmpty()) {
            throw new IllegalArgumentException("frames must not be null or empty");
        }
        if (frameDelayMillis < 0) {
            throw new IllegalArgumentException("frameDelayMillis must be >= 0");
        }
        if (horseColor == null) {
            throw new IllegalArgumentException("horseColor must not be null");
        }
        this.frames = frames;
        this.frameDelayMillis = frameDelayMillis;
        this.clearEachFrame = clearEachFrame;
        this.useCursorRestore = useCursorRestore;
        this.horseColor = horseColor;
    }


    public void stop() {
        this.running = false;
    }


    private void clearScreen() {
        System.out.print("\u001b[H\u001b[2J");
        System.out.flush();
    }


    public void runLoop() {
        runForDuration(Long.MAX_VALUE);
    }

    /**
     * Run the animation loop for a fixed duration in milliseconds, cycling through frames until
     *
     * <p>This method blocks the calling thread.</p>
     *
     * @param durationMillis how long to run the animation for
     */
    public void runForDuration(long durationMillis) {
        long start = System.currentTimeMillis();
        int index = 0;


        System.out.print(ColorUtils.RESET);


        if (useCursorRestore) {
            System.out.print("\u001B[s"); // save cursor position
        }

        while (running && !frames.isEmpty() &&
                (durationMillis == Long.MAX_VALUE || System.currentTimeMillis() - start < durationMillis)) {
            if (useCursorRestore) {
                // Restore cursor to saved position for each frame so animation stays in place.
                System.out.print("\u001B[u");
            } else if (clearEachFrame) {
                clearScreen();
            }
            String colored = colorizeFrame(frames.get(index));
            System.out.print(colored);
            System.out.flush();
            try {
                Thread.sleep(frameDelayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            index++;
            if (index == frames.size()) {
                index = 0;
            }
        }
        if (clearEachFrame && !useCursorRestore) {
            clearScreen();
        }

        // Reset colors at the end so nothing leaks after the animation.
        System.out.print(ColorUtils.RESET);
    }

    /**
     * Apply ANSI colors to the raw ASCII frame.
     *
     * <p>The entire horse is 1 colour
     * based on the user's role ( yellow for Manager, magenta for Junior, etc.).</p>
     */
    private String colorizeFrame(String raw) {
        StringBuilder sb = new StringBuilder(raw.length() * 4);
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {

                sb.append(c);
            } else {

                sb.append(horseColor).append(c).append(ColorUtils.RESET);
            }
        }
        return sb.toString();
    }
}


