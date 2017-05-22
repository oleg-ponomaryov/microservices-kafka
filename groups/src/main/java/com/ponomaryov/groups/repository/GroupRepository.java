package com.ponomaryov.groups.repository;

import com.ponomaryov.groups.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
