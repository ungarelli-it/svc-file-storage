package it.ungarelli.svcfilestorage.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Value("${local.destination}")
	private String destination;

	public void save(MultipartFile file) {
		try {
			var filePath = Paths.get(destination, file.getOriginalFilename());
			if (!Files.exists(filePath.getParent())) {
				Files.createDirectories(filePath.getParent());
			}
			Files.copy(file.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ResponseEntity<byte[]> get(String fileName) throws IOException {
		byte[] content = Files.readAllBytes(Paths.get(destination, fileName));
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/" + (fileName.split("\\.")[1])));
		headers.setContentDispositionFormData(fileName, fileName);
		return new ResponseEntity<>(content, headers, HttpStatus.OK);
	}

	public List<String> list() {
		List<String> resultado = new ArrayList<>();
		Stream<Path> s = null;
		try {
			s = Files.list(Paths.get(destination));
			s.forEach(item -> {
				resultado.add(item.getFileName().toString());
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return resultado;
	}

	public void delete(String fileName) {
		try {
			Files.delete(Paths.get(destination, fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
