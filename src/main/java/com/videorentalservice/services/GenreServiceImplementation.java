package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Genre;
import com.videorentalservice.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 19.02.2017.
 */
@Service
public class GenreServiceImplementation implements GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<?> listAll() {
        List<Genre> genres = new ArrayList<>();
        genreRepository.findAll().forEach(genres::add);
        return genres;
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.getByName(name);
    }

    @Override
    public Page<Genre> findAll(Predicate predicate, Pageable pageable) {
        return genreRepository.findAll(predicate, pageable);
    }

    @Override
    public Genre getById(Integer id) {
        return genreRepository.findOne(id);
    }

    @Override
    public Genre save(Genre genreObject) {
        return genreRepository.save(genreObject);
    }

    @Override
    public Genre update(Genre genreObject) {
        return genreRepository.save(genreObject);
    }

    @Override
    public void delete(Integer id) {
        genreRepository.delete(id);
    }

}

