package com.junsu.cyr.flow.song;

import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.song.UnreleasedSongUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnlyUnreleasedSongUploadFlow {

    @ManagerOnly
    @Transactional
    public void onlyUnreleasedSongUpload(UnreleasedSongUploadRequest request,  Integer userId) {

    }
}
