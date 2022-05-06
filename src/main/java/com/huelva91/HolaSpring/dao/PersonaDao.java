package com.huelva91.HolaSpring.dao;


import com.huelva91.HolaSpring.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDao extends CrudRepository<Persona, Long> {


}
