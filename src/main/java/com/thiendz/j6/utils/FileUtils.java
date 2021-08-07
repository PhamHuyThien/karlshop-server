package com.thiendz.j6.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.thiendz.j6.dto.ResponseDTO;

public class FileUtils {
	private static final String[] IMG_FILE_SAVE_SUPPORT = { "png", "jpg", "jpeg", "gif" };

	public static ResponseDTO<String> saveStaticImage(MultipartFile multipartFile, String path, String newName) {
		ResponseDTO<String> resultModel = new ResponseDTO<>(0, "", "", "");
		if (multipartFile.isEmpty()) {
			resultModel.setMessage("File trống!");
			return resultModel;
		}
		String fileName = multipartFile.getOriginalFilename();
		String ext = getExtFile(fileName);
		if (!fileNameImgIsSupport(fileName)) {
			resultModel.setStatus(-1);
			resultModel.setMessage("file không hỗ trợ!");
			return resultModel;
		}
		String pathSave = getStaticPath(path).getAbsolutePath() + "\\" + newName + "." + ext;
		try {
			multipartFile.transferTo(new File(pathSave));
			resultModel.setStatus(1);
			resultModel.setMessage("Lưu thành công");
			resultModel.setData(path + "\\" + newName + "." + ext);
		} catch (IllegalStateException | IOException e) {
			resultModel.setStatus(-1);
			resultModel.setMessage("Lưu thất bại!");
		}
		return resultModel;
	}

	public static File getStaticPath(String path) {
		return new File("src\\main\\resources\\static" + path);
	}

	public static boolean fileNameImgIsSupport(String fileName) {
		for (String ext : IMG_FILE_SAVE_SUPPORT) {
			if (fileName.endsWith("." + ext)) {
				return true;
			}
		}
		return false;
	}

	public static String getExtFile(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public static String randomName() {
		int rand = Utils.RANDOM.nextInt(2000000000);
		return "file_" + rand;
	}

	public static String randomName(String ext) {
		int rand = Utils.RANDOM.nextInt(2000000000);
		return "file_" + rand + "." + ext;
	}
}
