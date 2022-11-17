package ca.mcgill.ecse321.museum.integration;

import ca.mcgill.ecse321.museum.controller.DtoUtility;
import ca.mcgill.ecse321.museum.dao.ArtworkRepository;
import ca.mcgill.ecse321.museum.dao.MuseumRepository;
import ca.mcgill.ecse321.museum.dao.RoomRepository;
import ca.mcgill.ecse321.museum.dao.ScheduleRepository;
import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtworkIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        artworkRepository.deleteAll();
        roomRepository.deleteAll();
        museumRepository.deleteAll();
        scheduleRepository.deleteAll();

    }

    @Test
    public void testGetAllArtworksInRoom() {
        // We created an artwork in the DB
        List<ArtworkDto> artworkDtos = createArtworkDtos();
        Long roomId = artworkDtos.get(0).getRoom().getRoomId();

        // We do a get request to see if our controller method works
        ResponseEntity<ArtworkDto[]> response = client.getForEntity("/getAllArtworksInRoom/" + roomId.toString(), ArtworkDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(2, response.getBody().length, "Response has correct number of Artworks");
    }

    @Test
    public void testGetArtworkStatus() {
        // We created an artwork in the DB
        List<ArtworkDto> artworkDtos = createArtworkDtos();
        Long artworkId = artworkDtos.get(0).getArtworkId();
        Long artworkId2 = artworkDtos.get(1).getArtworkId();

        // We do a get request to see if our controller method works -- Artwork 1 should be on loan
        ResponseEntity<String> response = client.getForEntity("/getArtworkStatus/" + artworkId.toString(), String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("loan", response.getBody(), "Response correctly said that artwork is on loan");

        // // We do a get request to see if our controller method works -- Artwork 2 should be on display
        ResponseEntity<String> response2 = client.getForEntity("/getArtworkStatus/" + artworkId2.toString(), String.class);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody(), "Response has body");
        assertEquals("display", response2.getBody(), "Response correctly said that artwork is on display");
    }

    @Test
    public void testGetNumberOfArtworksInRoom() {
        // We created an artwork in the DB
        List<ArtworkDto> artworkDtos = createArtworkDtos();
        Long roomId = artworkDtos.get(0).getRoom().getRoomId();

        // We do a get request to see if our controller method works
        ResponseEntity<Integer> response = client.getForEntity("/getNumberOfArtworksInRoom/" + roomId.toString(), Integer.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        // There are two artworks in the room so the request body should be 2
        assertEquals(2, response.getBody(), "Response correctly said that there are two artworks in room");
    }

    @Test
    public void testMoveArtworkToRoom() {
        List<ArtworkDto> artworkDtoList = createArtworkDtos();
        Long artworkId1 = artworkDtoList.get(0).getArtworkId();
        Long artworkId2 = artworkDtoList.get(1).getArtworkId();
        Long roomIdNew = artworkDtoList.get(2).getRoom().getRoomId();

        // We do a post request to see if our controller method works - Test for artwork1
        ResponseEntity<ArtworkDto> response = client.postForEntity("/moveArtworkToRoom/" + artworkId1.toString() + "/" + roomIdNew.toString(), null, ArtworkDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        // There are two artworks in the room so the request body should be 2
        assertEquals(roomIdNew, response.getBody().getRoom().getRoomId(), "Response correctly showed that the artworks new room id is correct and that it has been moved");

        // We do a get request to see if our controller method works
        ResponseEntity<ArtworkDto> response2 = client.postForEntity("/moveArtworkToRoom/" + artworkId2.toString() + "/" + roomIdNew.toString(),null, ArtworkDto.class);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody(), "Response has body");
        // There are two artworks in the room so the request body should be 2
        assertEquals(roomIdNew, response2.getBody().getRoom().getRoomId(), "Response correctly showed that the artworks new room id is correct and that it has been moved");

    }


    @Test
    public void testGetArtworkStatusNonExisting() {
        // We do a get request to see if our controller handles bad request well
        ResponseEntity<String> response = client.getForEntity("/getArtworkStatus/" + "1234", String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Artwork does not exist", response.getBody(), "Response has correct error message");
    }


    @Test
    public void testGetAllArtworksInRoom_NoRoom() {
        // We created an artwork in the DB
        List<ArtworkDto> artworkDtos = createArtworkDtos();
        Long roomId = artworkDtos.get(0).getRoom().getRoomId();

        // We do a get request to see if our controller method works
        ResponseEntity<ArtworkDto[]> response = client.getForEntity("/getAllArtworksInRoom/" + roomId.toString(), ArtworkDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(2, response.getBody().length, "Response has correct number of Artworks");
    }


    @Test
    public void testGetNumberOfArtworksInRoom_NoRoom() {

        // We do a get request to see if our controller handles bad request well
        ResponseEntity<String> response = client.getForEntity("/getAllArtworksInRoom/" + "1234", String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Artwork does not exist", response.getBody(), "Response has correct error message");

    }

    @Test
    public void testMoveArtworkToRoom_ArtworkNonExisting() {

    }

    @Test
    public void testMoveArtworkToRoom_RoomNonExisting() {

    }

    @Test
    public void testMoveArtworkToRoom_FullCapacity() {

    }


    @Test
    public void testRemoveArtworkFromRoomNonExisting() {}



    public List<ArtworkDto> createArtworkDtos(){
        List<ArtworkDto> artworkDtos = new ArrayList<ArtworkDto>();

        // Expected values for the artwork 1
        String artworkName = "The Art";
        String artist = "Kian";
        boolean isAvailableForLoan = false;
        boolean isOnLoan = true;
        double loanFee = 12.5;
        String image = "https://source.unsplash.com/C54OKB99iuw";

        // Expected values for the artwork 2
        String artworkName2 = "The Art2";
        String artist2 = "Bob";
        boolean isAvailableForLoan2 = true;
        boolean isOnLoan2 = false;
        double loanFee2 = 1000000;
        String image2 = "https://source.unsplash.com/C54OKB9922iuw";

        // Expected values for the artwork 3
        String artworkName3 = "The Art3";
        String artist3 = "Billy";
        boolean isAvailableForLoan3 = false;
        boolean isOnLoan3 = false;
        double loanFee3 = 999999999;
        String image3 = "https://source.unsplash.com/C54OKB9922iuw";

        Schedule schedule = new Schedule();

        // Creating a museum
        Museum museum = new Museum();
        museum.setName("Rougon-Macquart");
        museum.setVisitFee(12.5);
        museum.setSchedule(schedule);
        museumRepository.save(museum);

        // Creating room 1
        Room room = new Room();
        room.setRoomName("Room 1");
        room.setRoomType(RoomType.Small);
        room.setCurrentNumberOfArtwork(2);
        room.setMuseum(museum);
        roomRepository.save(room);


        // Creating room 2
        Room room2 = new Room();
        room2.setRoomName("Room 2");
        room2.setRoomType(RoomType.Large);
        room2.setCurrentNumberOfArtwork(1);
        room2.setMuseum(museum);
        roomRepository.save(room2);

        // Initialize artwork 1
        Artwork artwork = new Artwork();
        artwork.setName(artworkName);
        artwork.setArtist(artist);
        artwork.setIsAvailableForLoan(isAvailableForLoan);
        artwork.setIsOnLoan(isOnLoan);
        artwork.setLoanFee(loanFee);
        artwork.setImage(image);
        artwork.setRoom(room);
        artworkRepository.save(artwork);

        // Initialize artwork 2
        Artwork artwork2 = new Artwork();
        artwork2.setName(artworkName2);
        artwork2.setArtist(artist2);
        artwork2.setIsAvailableForLoan(isAvailableForLoan2);
        artwork2.setIsOnLoan(isOnLoan2);
        artwork2.setLoanFee(loanFee2);
        artwork2.setImage(image2);
        artwork2.setRoom(room);
        artworkRepository.save(artwork2);

        // Initialize artwork 3
        Artwork artwork3 = new Artwork();
        artwork3.setName(artworkName3);
        artwork3.setArtist(artist3);
        artwork3.setIsAvailableForLoan(isAvailableForLoan3);
        artwork3.setIsOnLoan(isOnLoan3);
        artwork3.setLoanFee(loanFee3);
        artwork3.setImage(image3);
        artwork3.setRoom(room2);
        artworkRepository.save(artwork3);

        ArtworkDto artworkDto1 = DtoUtility.convertToDto(artwork);
        ArtworkDto artworkDto2 = DtoUtility.convertToDto(artwork2);
        ArtworkDto artworkDto3 = DtoUtility.convertToDto(artwork3);
        artworkDtos.add(artworkDto1);
        artworkDtos.add(artworkDto2);
        artworkDtos.add(artworkDto3);

        return artworkDtos;
    }



}
