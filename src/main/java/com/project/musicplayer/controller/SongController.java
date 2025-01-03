package com.project.musicplayer.controller;

import com.project.musicplayer.dto.ApiResponseDTO;
import com.project.musicplayer.dto.record.RecordPreviewDTO;
import com.project.musicplayer.dto.record.UpdateRecordDTO;
import com.project.musicplayer.dto.song.NewSongsDTO;
import com.project.musicplayer.dto.song.TrackPreviewDTO;
import com.project.musicplayer.dto.song.UpdateSongDTO;
import com.project.musicplayer.model.RecordType;
import com.project.musicplayer.service.JwtService;
import com.project.musicplayer.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/song")
public class SongController {
    private final JwtService jwtService;
    private final SongService songService;

    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponseDTO<String>> addNewSongs(HttpServletRequest request, @Valid @RequestBody NewSongsDTO newSongsDTO) {
        if (jwtService.checkIfAdminFromHttpRequest(request)) {
            return songService.addNewSongs(newSongsDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDTO<>(false, "You are not authorized to perform this operation", null));
    }

    @PutMapping("/admin/update/{songId}")
    public ResponseEntity<ApiResponseDTO<String>> updateExistingSong(HttpServletRequest request, @Valid @RequestBody UpdateSongDTO updateSongDTO, @PathVariable("songId") String songId) {
        if (jwtService.checkIfAdminFromHttpRequest(request)) {
            return songService.updateExistingSong(updateSongDTO, songId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDTO<>(false, "You are not authorized to perform this operation", null));
    }

    @DeleteMapping("/admin/delete/{songId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteExistingSong(HttpServletRequest request, @PathVariable("songId") String songId) {
        if (jwtService.checkIfAdminFromHttpRequest(request)) {
            return songService.deleteExistingSong(songId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDTO<>(false, "You are not authorized to perform this operation", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<Set<TrackPreviewDTO>>> getAllSongsByRecordId(
            @RequestParam String recordId
    ) {
        return songService.getAllSongsByRecordId(recordId);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<ApiResponseDTO<TrackPreviewDTO>> getSongById(
            @PathVariable("songId") String songId
    ) {
        return songService.getSongById(songId);
    }
}
