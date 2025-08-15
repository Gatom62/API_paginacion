package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//Tienden a ejecutarse automaticamente las clases que tienen esta anotacion cuando la API esta comenzando a correr
@Configuration
public class CloudinaryConfig {

    private String cludeName;
    private String apiKey;
    private String apiSecret;

    //Sirve para inyectarlo en las clases que vamos a utilizar¿
    @Bean
    public Cloudinary cloudinary () {

        //Cargando todas las variables del archivo .env
        Dotenv dotenv = Dotenv.load();

        //Crear un MAP para almacenar la configuracion
        Map<String, String> config = new HashMap<>();

        //Obteniendo las credenciales desde las variables de entorno
        config.put("cloud_name", dotenv.get("CLAUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLAUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLAUDINARY_API_SECRET"));

        //Retornar una nueva instancia de cloudinary con la configuracion de carga
        return new Cloudinary(config);
    }
}