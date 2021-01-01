package stickman;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JSONFileExtractor {
    private JSONParser parser = new JSONParser();
    private JSONObject configFile;

    public JSONFileExtractor(String JSONFilePath) {
        try {
            URI uri = new URI(this.getClass().getResource("/" + JSONFilePath).toString());
            this.configFile = (JSONObject) parser.parse(new FileReader(new File(uri.getPath())));
        }
        catch (IOException | ParseException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getStickmanConfig() {
        return (JSONObject) this.configFile.get("stickman");
    }

    public JSONObject getCloudConfig() {
        return (JSONObject) this.configFile.get("cloud");
    }

    public JSONObject getLevelConfig() {
        return (JSONObject) this.configFile.get("level");
    }

    public JSONObject getEnemyConfig() {
        return (JSONObject) this.configFile.get("enemy");
    }

    public JSONObject getMushroomConfig() {
        return (JSONObject) this.configFile.get("mushroom");
    }

    public JSONObject getGrassBlockConfig() {
        return (JSONObject) this.configFile.get("grassBlock");
    }

    public JSONObject getPlatformConfig() {
        return (JSONObject) this.configFile.get("platform");
    }

    public JSONObject getFlagConfig() {
        return (JSONObject) this.configFile.get("flag");
    }

}
