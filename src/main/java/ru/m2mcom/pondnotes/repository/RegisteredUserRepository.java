package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.RegisteredUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RegisteredUser entity.
 */
@SuppressWarnings("unused")
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,Long> {

}
