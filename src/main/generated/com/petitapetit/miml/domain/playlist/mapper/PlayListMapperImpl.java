package com.petitapetit.miml.domain.playlist.mapper;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto.DetailResponse;
import com.petitapetit.miml.domain.playlist.dto.PlayListDto.DetailResponse.DetailResponseBuilder;
import com.petitapetit.miml.domain.playlist.dto.PlayListDto.SaveResponse;
import com.petitapetit.miml.domain.playlist.dto.PlayListDto.SaveResponse.SaveResponseBuilder;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-21T12:23:35+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Azul Systems, Inc.)"
)
@Component
public class PlayListMapperImpl implements PlayListMapper {

    @Override
    public SaveResponse playListToSaveResponse(PlayList savedPlayList) {
        if ( savedPlayList == null ) {
            return null;
        }

        SaveResponseBuilder saveResponse = SaveResponse.builder();

        saveResponse.playListId( savedPlayList.getPlayListId() );
        saveResponse.name( savedPlayList.getName() );
        saveResponse.isPublic( savedPlayList.getIsPublic() );

        return saveResponse.build();
    }

    @Override
    public DetailResponse playListToDetailResponse(PlayList playList) {
        if ( playList == null ) {
            return null;
        }

        DetailResponseBuilder detailResponse = DetailResponse.builder();

        detailResponse.name( playList.getName() );
        detailResponse.isPublic( playList.getIsPublic() );

        return detailResponse.build();
    }

    @Override
    public List<DetailResponse> playListsToDetailResponseLists(List<PlayList> playLists) {
        if ( playLists == null ) {
            return null;
        }

        List<DetailResponse> list = new ArrayList<DetailResponse>( playLists.size() );
        for ( PlayList playList : playLists ) {
            list.add( playListToDetailResponse( playList ) );
        }

        return list;
    }
}
