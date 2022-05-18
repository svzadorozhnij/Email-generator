package com.example.Email.generator.repository;

import com.example.Email.generator.entity.Cron;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronRepository extends JpaRepository<Cron,Integer> {

    List<Cron> findByExpression(String expression);

}
