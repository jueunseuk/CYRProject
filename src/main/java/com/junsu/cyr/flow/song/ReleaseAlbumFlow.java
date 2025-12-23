package com.junsu.cyr.flow.song;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.song.AlbumUploadRequest;
import com.junsu.cyr.model.song.SongCreatorUploadRequest;
import com.junsu.cyr.model.song.SongUploadRequest;
import com.junsu.cyr.response.exception.code.AlbumExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.song.AlbumService;
import com.junsu.cyr.service.song.SongCreatorService;
import com.junsu.cyr.service.song.SongService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReleaseAlbumFlow {

    private final UserService userService;
    private final AlbumService albumService;
    private final S3Service s3Service;
    private final SongService songService;
    private final SongCreatorService songCreatorService;

    @ManagerOnly
    @Transactional
    public void releaseAlbum(AlbumUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        String imageUrl = null;
        if(request.getFile().isEmpty()) {
            throw new BaseException(AlbumExceptionCode.FAILED_TO_UPLOAD_ALBUM);
        }
        imageUrl = s3Service.uploadFile(request.getFile(), Type.ALBUM);

        Album album = albumService.createAlbum(request.getTitle(), imageUrl, request.getReleasedAt(), request.getIntroduction(), request.getAgency(), request.getPublisher());

        if(request.getSongs().isEmpty()) {
            throw new BaseException(AlbumExceptionCode.FAILED_TO_UPLOAD_ALBUM);
        }

        for(SongUploadRequest song : request.getSongs()) {
            Song newSong;
            if(song.getExistsInUnreleased()) {
                newSong = songService.getSongBySongId(song.getSongId());
                newSong.updateRelease();
                newSong.updateAlbum(album);
                newSong.updateSequence(song.getSequence());
                newSong.updateLink(song.getLink());
                newSong.updateLyrics(song.getLyrics());
            } else {
                newSong = songService.createReleasedSong(album, song.getTitle(), song.getLink(), song.getSequence(), song.getRepresentative(), song.getLyrics());
            }

            if(song.getRepresentative() != null || song.getRepresentative()) {
                newSong.updateRepresentative(true);
            }

            if(song.getSongCreators().isEmpty()) {
                throw new BaseException(AlbumExceptionCode.FAILED_TO_UPLOAD_ALBUM);
            }

            for(SongCreatorUploadRequest songCreator : song.getSongCreators()) {
                songCreatorService.createSongCreator(newSong, songCreator.getName(), songCreator.getType());
            }
        }
    }
}
