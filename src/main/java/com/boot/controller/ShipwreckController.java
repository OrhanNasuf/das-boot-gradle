package com.boot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Shipwreck;
import com.boot.repository.ShipwreckRepository;

@RestController
@RequestMapping("api/v1/")
public class ShipwreckController {
	
	@Autowired
	private ShipwreckRepository shipwreckRepository;
	
	@RequestMapping(value = "shipwrecks", method = RequestMethod.GET)
	public List<Shipwreck> list() {
		return shipwreckRepository.findAll();
	}
	
	@RequestMapping(value = "shipwrecks", method = RequestMethod.POST)
	public Shipwreck create(@RequestBody Shipwreck shipwreck) {
		return shipwreckRepository.saveAndFlush(shipwreck);
	}
	
	// Have to return an Optional Shipwreck, as it's not guaranteed that database finds a Shipwreck with given ID.
	// Also, findOne doesn't accept a Long, so it has to be findById.
	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.GET)
	public Optional<Shipwreck> get(@PathVariable Long id) {
		return shipwreckRepository.findById(id);
	}
	
	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.PUT)
	public Shipwreck update(@PathVariable Long id, @RequestBody Shipwreck shipwreck) {
		Optional<Shipwreck> existingShipwreck = shipwreckRepository.findById(id);
		BeanUtils.copyProperties(shipwreck, existingShipwreck.get());
		return shipwreckRepository.save(existingShipwreck.get());
	}
	
	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.DELETE)
	public Optional<Shipwreck> delete(@PathVariable Long id) {
		Optional<Shipwreck> existingShipwreck = shipwreckRepository.findById(id);
		shipwreckRepository.deleteById(id);
		return existingShipwreck;
	}
}
