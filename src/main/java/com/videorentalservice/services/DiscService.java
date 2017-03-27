package com.videorentalservice.services;

import com.videorentalservice.models.Disc;

/**
 * Created by Rave on 18.02.2017.
 */
public interface DiscService extends JpaService<Disc> {
    Disc getByTitle(String name);
    Disc getByOriginalTitle(String name);
}
