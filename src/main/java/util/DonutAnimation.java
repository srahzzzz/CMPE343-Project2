package util;

/**
 *  a colorful spinning ASCII donut animation for a fixed duration.
 * to be shown when the user chooses to terminate the program.
 * @author sarah nauman
 */
public class DonutAnimation {

    /**
     * @param durationMillis how long to run the animation in milliseconds
     */
    public static void play(long durationMillis) {
        final long startTime = System.currentTimeMillis();

        double A = 1.0;
        double B = 1.0;

        // Cycle through some bright colors to make the donut "colorful"
        String[] colors = new String[] {
            ColorUtils.BRIGHT_CYAN,
            ColorUtils.BRIGHT_MAGENTA,
            ColorUtils.BRIGHT_YELLOW,
            ColorUtils.BRIGHT_GREEN,
            ColorUtils.BRIGHT_BLUE
        };

        int frame = 0;

        while (System.currentTimeMillis() - startTime < durationMillis) {
            // Clear screen and move cursor to top-left (same approach as BaseMenu.clearConsole)
            System.out.print("\u001B[2J\u001B[H");

            A += 0.07;
            B += 0.03;

            double cA = Math.cos(A);
            double sA = Math.sin(A);
            double cB = Math.cos(B);
            double sB = Math.sin(B);

            final char[] b = new char[1760];
            final double[] z = new double[1760];

            for (int k = 0; k < 1760; k++) {
                b[k] = k % 80 == 79 ? '\n' : ' ';
                z[k] = 0;
            }

            for (double j = 0; j < 6.28; j += 0.07) {
                double ct = Math.cos(j);
                double st = Math.sin(j);
                for (double i = 0; i < 6.28; i += 0.02) {
                    double sp = Math.sin(i);
                    double cp = Math.cos(i);
                    double h = ct + 2; // R1 + R2 * cos(theta)
                    double D = 1 / (sp * h * sA + st * cA + 5); // 1/z
                    double t = sp * h * cA - st * sA;

                    int x = (int) (40 + 30 * D * (cp * h * cB - t * sB));
                    int y = (int) (12 + 15 * D * (cp * h * sB + t * cB));
                    int N = (int) (8 *
                            ((st * sA - sp * ct * cA) * cB -
                                    sp * ct * sA -
                                    st * cA -
                                    cp * ct * sB));
                    int o = x + 80 * y;
                    if (y < 22 && y >= 0 && x >= 0 && x < 79 && D > z[o]) {
                        z[o] = D;
                        b[o] = ".,-~:;=!*#$@".charAt(Math.max(N, 0));
                    }
                }
            }

            // Pick a color for this frame (advance faster to switch colors more quickly)
            String color = colors[(frame * 3) % colors.length];
            System.out.print(color);
            System.out.print(String.valueOf(b));
            System.out.print(ColorUtils.RESET);

            frame++;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}


