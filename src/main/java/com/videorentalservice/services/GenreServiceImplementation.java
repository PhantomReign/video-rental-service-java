package com.videorentalservice.services;

import com.videorentalservice.models.Genre;
import com.videorentalservice.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 19.02.2017.
 */
@Service
@Profile("springdatajpa")
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
    public Genre getById(Integer id) {
        return genreRepository.findOne(id);
    }

    @Override
    public Genre saveOrUpdate(Genre genreObject) {
        return genreRepository.save(genreObject);
    }

    @Override
    public void delete(Integer id) {
        genreRepository.delete(id);
    }

}

