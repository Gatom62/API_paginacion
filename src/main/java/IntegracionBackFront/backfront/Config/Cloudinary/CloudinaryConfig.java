package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//Hacemos que la clase de ejecute automaticamente cuando la APi comienze a funcionar
@Configuration
public class CloudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    //Creamos un metodo que se va auto ejecutar cuando la APi comienze a funcionar
    @Bean
    public Cloudinary cloudinary(){
        //Crear un objeto para leer las variables del archivo .env
        Dotenv dotenv = Dotenv.load();

        //Crear un map para almacenar la configuracion necesario para cloudinary
        Map<String, String> config = new HashMap<>();

        //Hacemos que funcionen las credenciales de cloudinary
        config.put("cloud_name", dotenv.get("CLAUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLAUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLAUDINARY_API_SECRET"));

        //Retornamos las configuraciones para que funcionan
        return new Cloudinary(config);
    }
}