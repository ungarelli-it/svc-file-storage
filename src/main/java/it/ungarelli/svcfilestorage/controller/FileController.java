package it.ungarelli.svcfilestorage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.ungarelli.svcfilestorage.service.FileService;

@Controller
@RequestMapping(value = "/file", produces = "application/json")
public class FileController {

	@Autowired
	private FileService s3Service;

	@GetMapping
	public ResponseEntity<List<String>> list() {
		return ResponseEntity.ok(s3Service.list());
	}

	@GetMapping(value = "/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody ResponseEntity<byte[]> get(@PathVariable("fileName") String fileName) throws IOException {
		return s3Service.get(fileName);
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestParam("file") MultipartFile file) {
		s3Service.save(file);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{fileName}")
	public ResponseEntity<?> delete(@PathVariable("fileName") String fileName) {
		s3Service.delete(fileName);
		return ResponseEntity.ok().build();
	}

}
