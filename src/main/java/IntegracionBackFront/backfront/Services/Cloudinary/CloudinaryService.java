package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.tomcat.util.net.jsse.PEMFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    //1. Constante para definir el tamaño maximo permitido para los archivos (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2. Extensiones de archivos permitidas para subir a cloudinary
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".png", ".jpeg"};

    //3. Cliente de cloudinary inyectado como dependencia
    private final Cloudinary cloudinary;

    //3,1. Contructor para poder agregarle datos al objeto de cloudinary
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a la raiz de Cloudinary
    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder)throws IOException{
        //Mandamos a llamar el metodo que no ayuda a validar las imagenes
        validateImage(file);

        //Hacemos que proteja
        String originalFilename = file.getOriginalFilename();
        String fileExtensions = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        //Renombramos la imagen para poder proteger el sistema
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtensions;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,   //Carpeta de destino
                "public_id", uniqueFilename, //Nombre unico para el archivo
                "use_filename", false, //No usar el nombre original
                "unique_filename", false, //No genera nombre unico (ya lo hicimos)
                "overwrite", false, //Para no sobreescribir archivos exsistentes
                "resource_type", "auto",
                "quality", "auto:good"
        );

        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        return (String) uploadResult.get("secure_url");
    }

    //Validaciones para las imagenes
    private void validateImage(MultipartFile file) {
        //1. Verificamos si el archivo esta bacio
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo no puede estar vacio");

        //2. Verificar si el tamaño del archivo excede el limite permito
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo execede el limite permitido de 5MB");

        //3. Validar el nombre de la imagen y del archivo
        String originalFilename = file.getOriginalFilename();

        //3.1
        if (originalFilename == null) throw new IllegalArgumentException("Nombre del archivo no valido, osea esta en blanco pues ):<");

        //4. Validamos la extension del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        //4,1. Si la extension no esta dentro del arreglo,
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se permiten archivos: jpg, jpe, png");

        //5. Verificamos el nombre del archivo
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe de ser una imagen valida (:");
    }

    //Subir imagenes a una carpeta de Cloudinary
}