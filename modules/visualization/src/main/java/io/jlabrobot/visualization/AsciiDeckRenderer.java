package io.jlabrobot.visualization;

import io.jlabrobot.core.Carrier;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Resource;

public class AsciiDeckRenderer {
    private static final int SCALE = 5; // mm per char
    private static final char EMPTY = '.';
    private static final char CARRIER_CHAR = '#';
    private static final char WELL_CHAR = 'o';

    public static String render(Carrier deck) {
        return render(deck, 600, 400); // ponytail: fixed size, add auto-bounds when needed
    }

    public static String render(Carrier deck, int widthMm, int heightMm) {
        int cols = widthMm / SCALE;
        int rows = heightMm / SCALE;
        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = EMPTY;
            }
        }

        renderResource(deck, grid, new Coordinate(0, 0, 0), 0);

        StringBuilder sb = new StringBuilder();
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = 0; c < cols; c++) {
                sb.append(grid[r][c]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static void renderResource(Resource resource, char[][] grid, Coordinate parentPos, int depth) {
        Coordinate absPos = parentPos.add(resource.getLocation());
        int gridX = (int) (absPos.x() / SCALE);
        int gridY = (int) (absPos.y() / SCALE);

        char symbol = depth > 1 ? WELL_CHAR : CARRIER_CHAR;

        if (gridY >= 0 && gridY < grid.length && gridX >= 0 && gridX < grid[0].length) {
            grid[gridY][gridX] = symbol;
        }

        if (resource instanceof Carrier carrier) {
            for (Resource child : carrier.getChildren()) {
                renderResource(child, grid, absPos, depth + 1);
            }
        }
    }
}
