import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ReadProperties {

    static ClassLoader classLoader = ReadProperties.class.getClassLoader();
    static File file = new File(classLoader.getResource("config.properties").getFile());

    static String configPath = file.getAbsolutePath();
    protected static FileInputStream fileInputStream;
    protected static Properties properties;

    static {
        try {
            fileInputStream = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String getProperty(String prop){return properties.getProperty(prop);}
}