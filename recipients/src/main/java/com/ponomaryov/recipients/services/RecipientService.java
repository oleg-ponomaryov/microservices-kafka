package com.ponomaryov.recipients.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ponomaryov.recipients.domain.Recipient;
import com.ponomaryov.recipients.repository.RecipientRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipientService {

    @Autowired
    private RecipientRepository recipientRepository;

    public Recipient getRecipientById(String id) {
        return recipientRepository.findOne(id);
    }

    public Collection<Recipient> getAllRecipients() {
        return recipientRepository.findAll(new Sort("name"));
    }
    
    @Transactional
    public void delete(String id) {
    	recipientRepository.delete(id);
    }

    @Transactional
    public Recipient save(Recipient rcpt) {
    	Recipient r = recipientRepository.save(rcpt);
    	log.info("Recipient {} has been saved", r);
        return r;
    }
    
    @Transactional
    public Recipient update(Recipient rcpt) {
    	Recipient rc = getRecipientById(rcpt.getId());
    	if (rc == null) {
    		throw new NoSuchElementException(String.format("Recipient=%s not found", rcpt.getId()));
    	}
    	
    	if (rcpt.getName() != null)
    		rc.setName(rcpt.getName());
    	
    	if (rcpt.getDob() != null)
    		rc.setDob(rcpt.getDob());
    	
    	if (rcpt.getEmail() != null)
    		rc.setEmail(rcpt.getEmail());
    		
    	if (rcpt.getFirstName() != null)
    		rc.setFirstName(rcpt.getFirstName());
    		
    	if (rcpt.getGroupId() != null)
    		rc.setGroupId(rcpt.getGroupId());
    		
    	if (rcpt.getLastName() != null)
    		rc.setLastName(rcpt.getLastName());
    	
        return recipientRepository.save(rc);
    }
}
