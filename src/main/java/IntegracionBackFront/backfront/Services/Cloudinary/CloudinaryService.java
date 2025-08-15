package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class CloudinaryService {

    //1. Definir el tamaño de las imagenes en MB
    // 5 MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2. Definimos las exztensiones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    //3. Atributo de Cloudinary
    private final Cloudinary cloudinary;

    //Contructor para inyeccion de depensias de Cloudinary<<
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //1. Verificar si el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacio.");
        }

        //2. Verificar si el tamaño excede el limite permido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamño del archivo no debe de ser mayor a 5 megas");
        }

        //3. Verificar la extension del archivo
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null){
            throw new IllegalArgumentException("Nombre del archivo invalido");
        }

        //4. Extraer y validar la extrension del archivo
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permiten archivos JPG, JPEG, PNG");
        }

        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El arvhivo debe de ser una imagen valida")
        }
    }
}