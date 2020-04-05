package project.databases;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class DataManager extends DataConstants {

    public void write(Object object, String filePath) {
        String json = new Gson().toJson(object);
        Writer fw = null;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(new File(filePath)),
                StandardCharsets.UTF_8);
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T read(String filePath, Type type) {
        Gson gson = new Gson();
        BufferedReader buffered = null;
        try {
            buffered = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            return gson.fromJson(buffered, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
