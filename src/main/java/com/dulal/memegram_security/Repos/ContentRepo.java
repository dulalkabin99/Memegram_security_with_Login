package com.dulal.memegram_security.Repos;


import com.dulal.memegram_security.Models.MemeGram;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepo extends CrudRepository<MemeGram, Long> {
}
