package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.VRSException;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.Genre;
import com.videorentalservice.repositories.DiscRepository;
import com.videorentalservice.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */
@Service
public class DiscServiceImplementation implements DiscService {
    private DiscRepository discRepository;
    private GenreRepository genreRepository;

    @Autowired
    public void setProductRepository(DiscRepository discRepository) {
        this.discRepository = discRepository;
    }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<?> listAll() {
        List<Disc> discs = new ArrayList<>();
        discRepository.findAll().forEach(discs::add);
        return discs;
    }

    @Override
    public Disc getByTitle (String name) {
        return discRepository.getByTitle(name);
    }

    @Override
    public Disc getByOriginalTitle (String name) {
        return discRepository.getByOriginalTitle(name);
    }

    @Override
    public Page<Disc> findAll(Predicate predicate, Pageable pageable) {
        return discRepository.findAll(predicate, pageable);
    }

    @Override
    public Disc getById(Integer id) {
        return discRepository.findOne(id);
    }

    @Override
    public Disc save(Disc discObject) {
        List<Genre> persistedGenres = new ArrayList<>();
        List<Genre> genres = discObject.getGenres();
        if(genres != null){
            for (Genre genre : genres) {
                if(genre.getId() != null)
                {
                    persistedGenres.add(genreRepository.findOne(genre.getId()));
                }
            }
        }

        discObject.setGenres(persistedGenres);
        return discRepository.save(discObject);
    }

    @Override
    public Disc update(Disc discObject) {
        Disc persistedDisc = getById(discObject.getId());
        if(persistedDisc == null){
            throw new VRSException("Disc "+discObject.getId()+" doesn't exist");
        }
        persistedDisc.setTitle(discObject.getTitle());
        persistedDisc.setSubTitle(discObject.getSubTitle());
        persistedDisc.setOriginalTitle(discObject.getOriginalTitle());
        persistedDisc.setOriginalSubTitle(discObject.getOriginalSubTitle());
        persistedDisc.setDescription(discObject.getDescription());
        persistedDisc.setPrice(discObject.getPrice());
        persistedDisc.setItemCount(discObject.getItemCount());
        persistedDisc.setYear(discObject.getYear());
        persistedDisc.setGenres(discObject.getGenres());
        persistedDisc.setCategory(discObject.getCategory());
        persistedDisc.setImageBGUrl(discObject.getImageBGUrl());
        persistedDisc.setImageUrl(discObject.getImageUrl());
        persistedDisc.setVideoUrl(discObject.getVideoUrl());
        return discRepository.save(persistedDisc);
    }

    @Override
    public void delete(Integer id) {
        discRepository.delete(id);
    }

}