package util;

import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public final class WeltSetup {
    /**
     * The world width
     */
    private int width;

    /**
     * The world height
     */
    private int height;

    private String title;

    private int speed;

    private int randomeValue;

    private ArrayList<String> cellPath;

    private ArrayList<ActorPosition> actors;

    private boolean isDark;

    private int offsetStartToX;

    private int offsetStartToY;

    private int offsetXToEnd;

    private int offsetYToEnd;

    private String levelScreen;

    private boolean mute;


    /**
     * Constructor to be used by the Builder.
     */


    public WeltSetup() {
    }

    /**
     * Tries to load the specified file (or files) either relative to the class,
     * relative to the package root or relative to the project root.
     *
     * @param fileName The filename of the world setup file, possibly relative to the
     *                 clazz. Wildcards (? or *) may be used.
     * @param clazz    The class used to get the relative path to the file or
     *                 <code>null</code> if the file should be retrieved relative to
     *                 the jar root or project root.
     * @return the files or an empty list if none could be found.
     */
    public static File findMatchingFiles(String fileName, Class<?> clazz) {
        try {
            // Option 1: try to get file relative to class
            if (clazz != null) {
                File dir = new File(clazz.getResource(".").toURI());
                List<File> files = WeltSetup.scan(dir, fileName);
                if (!files.isEmpty()) {
                    return files.get(0);
                }
            }

            // Option 2: try to get file relative to package root (may be inside jar)
            File dir2 = new File(Thread.currentThread().getContextClassLoader().getResource(".").toURI());
            List<File> files2 = WeltSetup.scan(dir2, fileName);
            if (!files2.isEmpty()) {
                return files2.get(0);
            }

            // Option 3: try to get file relative to project root (outside of jar)
            File dir3 = new File(".");
            List<File> files3 = WeltSetup.scan(dir3, fileName);
            if (!files3.isEmpty()) {
                // Note: only get the first match
                return files3.get(0);
            }
        } catch (URISyntaxException e) {
            // do nothing
            e.printStackTrace();
        }


        return null;
    }

    /**
     * Scans through the directory using wild-card patterns. All files
     * matching the patterns are returned.
     * <ul>
     * <li>Use ? for one or no unknown character</li>
     * <li>Use * for zero or more unknown characters</li>
     * </ul>
     *
     * @param dir      the directory
     * @param patterns the patterns that should be matched containing wild-cards.
     * @return
     */
    public static List<File> scan(File dir, String... patterns) {
        List<File> result = new ArrayList<File>();
        if (!dir.isDirectory()) {
            return result;
        }

        List<String> convertedPatterns = new ArrayList<String>();
        for (String p : patterns) {
            p = p.replace(".", "\\.");
            p = p.replace("?", ".?");
            p = p.replace("*", ".*");
            convertedPatterns.add(p);
        }

        File[] filesInDir = dir.listFiles();
        for (File currentFile : filesInDir) {
            if (matches(currentFile, convertedPatterns)) {
                result.add(currentFile);
            }
        }
        return result;
    }

    /**
     * Returns true if the filename of the file matches one of the patterns.
     *
     * @param file
     * @param convertedPatterns
     * @return
     */
    private static boolean matches(File file, List<String> convertedPatterns) {
        for (String pattern : convertedPatterns) {
            if (file.getName().matches(pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Read all lines from a file.
     *
     * @param file the file to read from
     * @return the lines from the file as a {@code List}.
     * @throws IOException if an I/O error occurs reading from the file.
     */
    public static List<String> readAllLines(File file) throws IOException {
        return readAllLines(new FileInputStream(file));
    }

    /**
     * Read all lines from an InputStream.
     *
     * @param stream the stream to read from
     * @return the lines from the stream as a {@code List}.
     * @throws IOException if an I/O error occurs reading from the stream.
     */
    public static List<String> readAllLines(InputStream stream) throws IOException {
        List<String> result = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));

            for (; ; ) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                result.add(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    public static WeltSetup createWorldSetup(List<String> list) {
        return createWorldSetup(list.stream().reduce("", (a, b) -> a + "\n" + b));
    }

    public static WeltSetup createWorldSetup(String s) {
        Gson gson = new Gson();

        return gson.fromJson(s, WeltSetup.class);
    }

    public static void saveWorldSetup(WeltSetup v) {
        Gson gson = new Gson();
        String content = gson.toJson(v);
        File file = findMatchingFiles("WeltSetup.json", WeltSetup.class);
        try {
            writeToFile(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes to the specified file.
     *
     * @param file    The file to write to
     * @param content The content to write.
     * @throws IOException if an I/O error occurs writing to the file.
     */
    public static void writeToFile(File file, String content) throws IOException {
        FileWriter fstream = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fstream);
        try {
            out.write(content);
            out.flush();
        } finally {
            // Close the output stream
            if (out != null) {
                out.close();
            }
        }
    }
    
    public boolean isMute() {
        return mute;
    }
    
    public void setMute(boolean mute) {
        this.mute = mute;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getRandomeValue() {
        return randomeValue;
    }
    
    public void setRandomeValue(int randomeValue) {
        this.randomeValue = randomeValue;
    }
    
    public ArrayList<String> getCellPath() {
        return cellPath;
    }
    
    public void setCellPath(ArrayList<String> cellPath) {
        this.cellPath = cellPath;
    }
    
    public ArrayList<ActorPosition> getActors() {
        return actors;
    }
    
    public void setActors(ArrayList<ActorPosition> actor) {
        this.actors = actor;
    }
    
    public boolean isDark() {
        return isDark;
    }
    
    public void setDark(boolean dark) {
        isDark = dark;
    }
    
    public int getOffsetStartToX() {
        return offsetStartToX;
    }
    
    // windowWidth = offsetStartToX + width + offsetXToEnd;
    // nettoHeight = offsetStartToY + height + offsetYToEnd;
    
    public void setOffsetStartToX(int offsetStartToX) {
        this.offsetStartToX = offsetStartToX;
    }
    
    public int getOffsetStartToY() {
        return offsetStartToY;
    }
    
    public void setOffsetStartToY(int offsetStartToY) {
        this.offsetStartToY = offsetStartToY;
    }
    
    public int getOffsetXToEnd() {
        return offsetXToEnd;
    }
    
    public void setOffsetXToEnd(int offsetXToEnd) {
        this.offsetXToEnd = offsetXToEnd;
    }
    
    public int getOffsetYToEnd() {
        return offsetYToEnd;
    }
    
    public void setOffsetYToEnd(int offsetYToEnd) {
        this.offsetYToEnd = offsetYToEnd;
    }
    
    public String getLevelScreen() {
        return levelScreen;
    }
    
    public void setLevelScreen(String levelScreen) {
        this.levelScreen = levelScreen;
    }
    
    public int getOuterWidth() {
        return offsetStartToX + width + offsetXToEnd;
    }
    
    public int getOuterHeight() {
        return offsetStartToY + height + offsetYToEnd;
    }
    
    @Override
    public String toString() {
        return "WeltSetup{" +
                "width=" + width + "\n" +
                ", height=" + height + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", speed=" + speed + "\n" +
                ", randomeValue=" + randomeValue + "\n" +
                ", cellPath=" + cellPath + "\n" +
                ", isDark=" + isDark + "\n" +
                ", offsetStartToX=" + offsetStartToX + "\n" +
                ", offsetStartToY=" + offsetStartToY + "\n" +
                ", offsetXToEnd=" + offsetXToEnd + "\n" +
                ", offsetYToEnd=" + offsetYToEnd + "\n" +
                ", actors=" + actors + "\n" +
                ", levelScreen='" + levelScreen + '\'' +
                '}';
    }
    
    public class ActorPosition {
        
        private String actor;
        
        private int x;
        
        private int y;
        
        public ActorPosition() {
        
        }
        
        public String getActor() {
            return actor;
        }
        
        public void setActor(String actor) {
            this.actor = actor;
        }
        
        public int getX() {
            return x;
        }
        
        public void setX(int x) {
            this.x = x;
        }
        
        public int getY() {
            return y;
        }
        
        public void setY(int y) {
            this.y = y;
        }
        
        @Override
        public String toString() {
            return "ActorPosition{" +
                    "actor='" + actor + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }


}
