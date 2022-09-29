package fr.isika.cda14.efund.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.file.UploadedFile;

public class FileUpload {
	public static void doUpload(UploadedFile file, String filePath) {
		try {
			
			/* Récupération du InputStream qui va permettre de copier le fichier Upload vers un fichier sur le disque */
			InputStream myInputStream = file.getInputStream(); 
			
			/* Récupération du chemin d'accès du dossier image (/img/) et création d'un fichier */
			ServletContext servletContext = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
			File newFile = new File(servletContext.getRealPath("img") + filePath);
			newFile.createNewFile();
			
			/* Récupération du Path pour la copy */
			Path newPath = newFile.toPath();
			
			Files.copy(myInputStream, newPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
