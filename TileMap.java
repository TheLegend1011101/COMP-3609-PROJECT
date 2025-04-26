import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TileMap {
    private int[][] map;
    private int tileWidth;
    private int tileHeight;

    public TileMap(String filePath, int tileWidth, int tileHeight) throws IOException {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        loadMap(filePath);
    }

    private void loadMap(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rows = 0;

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            if (map == null) {
                map = new int[100][tokens.length]; // Assuming max 100 rows
            }
            for (int i = 0; i < tokens.length; i++) {
                map[rows][i] = Integer.parseInt(tokens[i]);
            }
            rows++;
        }
        br.close();
    }

    public int[][] getMap() {
        return map;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}