package petermcneil.musiclibrary.services.database;

import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.CRUDService;

import java.util.List;

public class SongService implements CRUDService<Song> {
    @Override
    public Song get(Integer objectId) {
        return null;
    }

    @Override
    public List<Song> getList() {
        return null;
    }

    @Override
    public Integer post(Song object) {
        return null;
    }

    @Override
    public void put(Song object, Integer objectId) {

    }

    @Override
    public void delete(Integer objectId) {

    }
}
