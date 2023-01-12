package com.bluehair.hanghaefinalproject.common.service;


import com.bluehair.hanghaefinalproject.common.exception.InvalidRequestException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.MUSIC;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.INVALID_SOUNDSAMPLE;

public class MultipartFileConverter {
    public static final String tmpPath = System.getProperty("user.dir") + File.separator + "temp";

    // 변환이 정상적으로 안되었던 이유 노션에 정리
    static public void generateTempPath() {
        File dir = new File(tmpPath);
        if(!dir.exists()) {
            try {
                dir.mkdir();
            }
            catch(Exception e) {
                e.getStackTrace();
            }
        }
    }

    static public File convertMFileToFile(MultipartFile mFile) throws IOException {
        File file = File.createTempFile("temp_", mFile.getOriginalFilename()
                , new File(tmpPath));
        try{
            mFile.transferTo(file);
        } catch (IOException e) {
            throw new InvalidRequestException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE);
        }
        return file;
    }
    static public MultipartFile convertFiletoMFile(File file) throws IOException {
        FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        try (FileInputStream is = new FileInputStream(file)){
            IOUtils.copy(is, fileItem.getOutputStream());
        } catch (IOException ex) {
            throw new InvalidRequestException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE);
        }
        MultipartFile mFile = new CommonsMultipartFile(fileItem);
        fileItem.delete();
        return mFile;
    }
}
