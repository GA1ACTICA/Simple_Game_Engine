package Utils.JsonUtils;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Utils.ErrorManagement;
import Utils.JsonUtils.Adapters.ColorAdapter;

public abstract class JsonBacked<T> {

    static final Gson gson = new GsonBuilder().registerTypeAdapter(Color.class, new ColorAdapter())
            .setPrettyPrinting().create();

    protected T data;

    protected JsonBacked(T initialData) {
        this.data = initialData;
    }

    protected void succsessfullExportLog(T object, String path) {

    }

    protected void succsessfullImportLog(T data, String path) {

    }

    public T data() {
        return data;
    }

    /**
     * Very work in progress!
     * 
     * {@code .exportJSON(new GameStateData(), "conf.json");}
     * 
     * @param object
     * @param path
     */
    public void exportJSON(T object, String path) {

        try (FileWriter writer = new FileWriter(path)) {

            // Export to Json
            gson.toJson(object, writer);
            succsessfullExportLog(object, path);

        } catch (Exception e) {
            ErrorManagement.reportError(e, "Error exporting JSON file ('%s')".formatted(path));
        }
    }

    /**
     * Very work in progress!
     * 
     * {@code .importJSON(GameStateData.class, "conf.json");}
     * 
     * @param clazz
     * @param path
     */
    public void importJSON(Class<T> clazz, String path) {

        try (FileReader reader = new FileReader(path)) {

            // Import Json and set data
            data = gson.fromJson(reader, clazz);
            succsessfullImportLog(data, path);

        } catch (Exception e) {
            ErrorManagement.reportError(e, "Error importing JSON file ('%s')".formatted(path));
        }
    }
}
