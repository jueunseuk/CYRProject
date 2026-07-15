package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.repository.ImageRepository;
import com.junsu.cyr.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileUtil fileUtil;
    private final ImageRepository imageRepository;

    public Image uploadImage(MultipartFile file, Long targetId, Type imageType) {
        Image.validSize(file.getSize());

        String[] fileInfo  = fileUtil.storeFile(file, imageType);

        Image image = Image.builder()
                .originName(file.getOriginalFilename())
                .storedName(fileInfo[1])
                .path(fileInfo[0])
                .targetId(targetId)
                .type(imageType)
                .build();

        return imageRepository.save(image);
    }
}