package com.ponomaryov.recipients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ponomaryov.recipients.domain.Recipient;

public interface RecipientRepository extends JpaRepository<Recipient, String> {
}
