package battleship.gameObjects;

import java.io.*;

public class HighScores {
    private static final int HIGSCORESNUMBER = 10;
    private final String[] lines;
    private int[] scores;

    public HighScores() {
        lines = new String[10];
        readHighScores();
    }

    private void readHighScores() {
        FileReader reader = null;
        try {
            reader = new FileReader("./src/battleship/controller/view/resources/highScores.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert reader != null;
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {

            for (int i = 0; i < HIGSCORESNUMBER; i++) {
                lines[i] = bufferedReader.readLine();
            }

            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHighScore(int shipNumber, int numberOfPlayerShoots, int numberOfHits, long startTime, float saveTime, String nickName) {
        countScores();
        float shotsAccuracy = 0;
        if (numberOfPlayerShoots > 0) shotsAccuracy = (float) 100 * numberOfHits / numberOfPlayerShoots;
        String shotsAccuracy2f = String.format("%.2f", shotsAccuracy);
        long elapsedTimeMillis = System.currentTimeMillis() - startTime;
        float elapsedTimeMin = elapsedTimeMillis / (60 * 1000F);
        elapsedTimeMin += saveTime;
        int score = numberOfHits * shipNumber - (int) elapsedTimeMin;
        int scoreId;
        for (scoreId = 0; scoreId < HIGSCORESNUMBER; scoreId++) {
            if (scores[scoreId] < score) break;
            if (scoreId == 9) return;
        }
        String[] oldLines = lines.clone();
        for (int i = scoreId + 1; i < HIGSCORESNUMBER; i++) {
            lines[i] = oldLines[i - 1];
        }

        String[] nick = nickName.split(" ");

        String content = nick[0] + " " + numberOfHits + " " + numberOfPlayerShoots + " " + shotsAccuracy2f + " " + elapsedTimeMin + " " + shipNumber;
        lines[scoreId] = content;


        FileWriter writer = null;
        try {
            writer = new FileWriter("./src/battleship/controller/view/resources/highScores.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null;
        BufferedWriter bw = new BufferedWriter(writer);

        try {

            for (int i = 0; i < HIGSCORESNUMBER; i++) {
                if (lines[i] == null) break;
                bw.write(lines[i] + "\n");
            }

            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void countScores() {
        scores = new int[10];
        for (int i = 0; i < HIGSCORESNUMBER; i++) {
            while (lines[i] == null) {
                scores[i] = 0;
                ++i;
                if (i == 10) return;
            }
            String[] stats = lines[i].split(" ");
            scores[i] = Integer.parseInt(stats[1]) * Integer.parseInt(stats[5]) - (int) Float.parseFloat(stats[4]);
        }
    }

    public String[] getLines() {
        return lines;
    }
}

