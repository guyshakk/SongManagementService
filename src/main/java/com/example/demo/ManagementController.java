package com.example.demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {
	ManagementService service;
	private final String baseUrl="/songs";//TODO: should be in properties
	
	@Autowired
	public ManagementController(ManagementService service) {
		this.service = service;
	}
	
	@RequestMapping(
			path = baseUrl,
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song createSong (@RequestBody Song song){
		return this.service
				.create(song);
	}
	
	@RequestMapping(
			path = baseUrl+"/{songId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song getDemoById (@PathVariable("songId") String songId) {
		return this.service
				.getSongById (songId);
	}
	
	@RequestMapping(
			path = baseUrl+"/{songId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update (
			@PathVariable("songId") String songId, 
			@RequestBody Song update) {
		//if (this.service.getSongById(songId) != null)
		this.service
			.updateSong(songId, update);
	}
	
	@RequestMapping(
			path = baseUrl,
			method = RequestMethod.DELETE)
	public void deleteAll () {
		this.service
			.deleteAllSongs();
	}
	
	/**Check id the user supplied a correct criteriaType;
	 * If yes -> call the correct function in the ManagementService
	 */
	@RequestMapping(
			path = baseUrl+"/search",
			method = RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Song [] getSongsBy (@RequestParam(name="criteriaType", required = false, defaultValue = "") String criteriaType,
			@RequestParam(name="criteriaValue", required = false, defaultValue = "") String criteriaValue,
			@RequestParam(name="size", required = false, defaultValue = "10") int size,
			@RequestParam(name="page", required = false, defaultValue = "0") int page,
			@RequestParam(name="sortBy", required = false, defaultValue = "songId") String sortBy,
			@RequestParam(name="sortOrder", required = false, defaultValue = "asc") String sortOrder
			) throws UnsupportedSearchException  {
 		
		String selectedSorting = "";
		
		if (criteriaType.toLowerCase().equals(CriteriaType.BYNAME.toString().toLowerCase())) {
			selectedSorting = CriteriaType.BYNAME.toString().toLowerCase();
		}
		else if (criteriaType.toLowerCase().equals(CriteriaType.BYPERFORMER.toString().toLowerCase())) {
			selectedSorting = CriteriaType.BYPERFORMER.toString().toLowerCase();
		}
		else if (criteriaType.toLowerCase().equals(CriteriaType.BYGENRE.toString().toLowerCase())) {
			selectedSorting = CriteriaType.BYGENRE.toString().toLowerCase();
		}
		else if (criteriaType.trim().length() == 0) {
			return this.service
					.readAllSongs(sortBy, 
							sortOrder, 
							size, 
							page)
					.toArray(new Song[0]);
		}
		else {
			throw new UnsupportedSearchException(criteriaType);
		}
		if (criteriaValue.trim().length() == 0)
		{
			throw new InvalidCriteriaValueException("For this kind of request criteriaValue must have an actual value");
		}
		return this.service
				.readSongsBy(selectedSorting,
						criteriaValue,
						size, 
						page, 
						sortBy, 
						sortOrder)
				.toArray(new Song[0]);
	}
	
	/**Exception that is prompted when a song cannot be retrieved using a supplied songId**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleError (EntityNotInDBException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "song not found";
		}
		return Collections.singletonMap("error", message);
	}
	
	/**Exception that is prompted when wrong search parameter is supplied by the user**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError (UnsupportedSearchException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "serach parameter does not exist";
		}
		return Collections.singletonMap("error", message);
	}
	
	/**Exception that is prompted when wrong sort parameter is supplied by the user**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError (UnsupportedSortByException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "sort parameter does not exist";
		}
		return Collections.singletonMap("error", message);
	}
	
	/**Exception that is prompted when wrong sort order parameter is supplied by the user**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError (UnsupportedSortOrderException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "sort order parameter does not exist";
		}
		return Collections.singletonMap("error", message);
	}
	
	/**Exception that is prompted when wrong page and size parameters are supplied by the user**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError (InvalidPaginationDataException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "Invalid pagination data. Please supply page bigger than 0 and size between 0 and 100";
		}
		return Collections.singletonMap("error", message);
	}
	
	/**Exception that is prompted when no criteriaValue is given while a criteriaType was given**/
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError (InvalidCriteriaValueException e){
		String message = e.getMessage();
		if (message == null || message.trim().length() == 0) {
			message = "For this kind of request criteriaValue must have an actual value";
		}
		return Collections.singletonMap("error", message);
	}
}
