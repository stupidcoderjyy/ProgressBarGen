package pbg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {
    private int barWidth;
    private int barHeight;
    private String textColor;
    private String lineColor;
    private String fontFamily;
    private int fontSize;
    private String outPath;

    public void saveToJson(String filePath) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }
    }

    public static Config load(String filePath) throws Exception {
        Gson gson = new Gson();
        File file = new File(filePath);
        if (!file.exists()) {
            Config config = loadDefault();
            config.saveToJson(filePath);
            return config;
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Config.class);
        }
    }

    private static Config loadDefault() {
        Config config = new Config();
        config.barWidth = 2560;
        config.barHeight = 50;
        config.textColor = "#E0FFFFFF";
        config.lineColor = "#E0FFFFFF";
        config.fontFamily = "Source Han Sans CN";
        config.fontSize = 15;
        config.outPath = System.getenv("USERPROFILE") + File.separator + "Desktop";
        return config;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int bar_width) {
        this.barWidth = bar_width;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(int bar_height) {
        this.barHeight = bar_height;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    @Override
    public String toString() {
        return "Config{" +
                "barWidth=" + barWidth +
                ", barHeight=" + barHeight +
                ", textColor='" + textColor + '\'' +
                ", lineColor='" + lineColor + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", fontSize=" + fontSize +
                ", outPath='" + outPath + '\'' +
                '}';
    }
}
