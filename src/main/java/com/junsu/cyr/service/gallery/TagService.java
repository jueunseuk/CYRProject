package com.junsu.cyr.service.gallery;

import com.junsu.cyr.domain.gallery.Tag;
import com.junsu.cyr.repository.TagRepository;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag getOrCreateTag(String name) {
        if (name == null || name.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NOT_FOUND_TAG_NAME);
        }

        Tag tag = tagRepository.findByName(name);
        if (tag != null) {
            return tag;
        }

        tag = Tag.builder().name(name).build();
        try {
            return tagRepository.save(tag);
        } catch (DataIntegrityViolationException e) {
            return tagRepository.findByName(name);
        }
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAllByOrderByNameAsc();
    }
}
