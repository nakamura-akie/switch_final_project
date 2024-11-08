package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;

/**
 * Repository interface for managing Profile entities.
 */
public interface ProfileRepository extends Repository<Profile, ProfileName> {
}
