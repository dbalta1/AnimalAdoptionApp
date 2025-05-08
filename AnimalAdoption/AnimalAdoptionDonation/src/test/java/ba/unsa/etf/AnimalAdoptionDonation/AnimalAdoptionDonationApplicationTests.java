package ba.unsa.etf.AnimalAdoptionDonation;


import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Controller.KorisnikController;
import ba.unsa.etf.AnimalAdoptionDonation.Service.KorisnikService;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Uloga;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Spol;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static ba.unsa.etf.AnimalAdoptionDonation.Entity.Uloga.KORISNIK;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class KorisnikControllerTest {

	@InjectMocks
	private KorisnikController korisnikController;

	@Mock
	private KorisnikService korisnikService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(korisnikController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetAllKorisnici() throws Exception {
		// Kreiranje korisnika sa odgovarajućim podacima
		KorisnikDTOBO korisnik1 = new KorisnikDTOBO();
		korisnik1.setId(1);
		korisnik1.setIme("John");
		korisnik1.setPrezime("Doe");
		korisnik1.setEmail("john@example.com");
		korisnik1.setTelefon("062/789-211");
		korisnik1.setGodine(25);
		korisnik1.setUloga(KORISNIK);
		korisnik1.setAdresa("Novo naselje bb");
		korisnik1.setUsername("johndoe1");


		when(korisnikService.getAllKorisnici()).thenReturn(Arrays.asList(korisnik1));

		mockMvc.perform(get("/korisnici"))
				.andExpect(status().isOk())
				.andExpect(jsonPath(".id").value(1))
				.andExpect(jsonPath(".ime").value("John"))
				.andExpect(jsonPath(".prezime").value("Doe"))
				.andExpect(jsonPath(".email").value("john@example.com"))
				.andExpect(jsonPath(".adresa").value("Novo naselje bb"))
				.andExpect(jsonPath(".username").value("johndoe1"))
				.andExpect(jsonPath(".telefon").value("062/789-211"))
				.andExpect(jsonPath(".godine").value(25))
				.andExpect(jsonPath(".uloga").value("KORISNIK"));


		verify(korisnikService, times(1)).getAllKorisnici();
	}

	@Test
	void testGetKorisnikById() throws Exception {
		int korisnikId = 1;
		KorisnikDTOBO korisnik1 = new KorisnikDTOBO();
		korisnik1.setId(1);
		korisnik1.setIme("John");
		korisnik1.setPrezime("Doe");
		korisnik1.setEmail("john@example.com");
		korisnik1.setTelefon("062/789-211");
		korisnik1.setGodine(25);
		korisnik1.setUloga(KORISNIK);
		korisnik1.setAdresa("Novo naselje bb");
		korisnik1.setUsername("johndoe1");

		when(korisnikService.getKorisnikById(korisnikId)).thenReturn(Optional.of(korisnik1));

		mockMvc.perform(get("/korisnici/{id}", korisnikId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(korisnikId))
				.andExpect(jsonPath("$.ime").value("John"))
				.andExpect(jsonPath("$.prezime").value("Doe"))
				.andExpect(jsonPath("$.email").value("john@example.com"))
				.andExpect(jsonPath(".adresa").value("Novo naselje bb"))
				.andExpect(jsonPath(".username").value("johndoe1"))
				.andExpect(jsonPath(".telefon").value("062/789-211"))
				.andExpect(jsonPath(".godine").value(25))
				.andExpect(jsonPath(".uloga").value("KORISNIK"));

		verify(korisnikService, times(1)).getKorisnikById(korisnikId);
	}

	@Test
	void testGetKorisnikById_NotFound() throws Exception {
		int id = 999;

		when(korisnikService.getKorisnikById(id)).thenReturn(Optional.empty());

		mockMvc.perform(get("/korisnici/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Korisnik sa ID " + id + " ne postoji."));
	}


	@Test
	void testCreateKorisnik() throws Exception {
		Korisnik korisnik = new Korisnik();
		korisnik.setUloga(KORISNIK);
		korisnik.setIme("Ana");
		korisnik.setPrezime("Anić");
		korisnik.setUsername("ana123");
		korisnik.setPassword("tajnasifra");
		korisnik.setEmail("ana@example.com");
		korisnik.setTelefon("061/111-111");
		korisnik.setSpol(Spol.ZENSKI);
		korisnik.setGodine(25);
		korisnik.setAdresa("Sarajevo bb");

		// DTO koji vraća servis
		KorisnikDTOBO korisnikDTOBO = new KorisnikDTOBO();
		korisnikDTOBO.setId(1);
		korisnikDTOBO.setIme("Ana");
		korisnikDTOBO.setPrezime("Anić");
		korisnikDTOBO.setEmail("ana@example.com");
		korisnikDTOBO.setUsername("ana123");
		korisnikDTOBO.setPassword("tajnasifra");
		korisnikDTOBO.setEmail("ana@example.com");
		korisnikDTOBO.setTelefon("061/111-111");
		korisnikDTOBO.setSpol(Spol.ZENSKI);
		korisnikDTOBO.setGodine(25);
		korisnikDTOBO.setAdresa("Sarajevo bb");

		// Kada se pozove createKorisnik, vraćamo DTO
		when(korisnikService.createKorisnik(any(Korisnik.class))).thenReturn(korisnikDTOBO);

		mockMvc.perform(post("/korisnici")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{"
								+ "\"uloga\": \"KORISNIK\","
								+ "\"ime\": \"Ana\","
								+ "\"prezime\": \"Anić\","
								+ "\"username\": \"ana123\","
								+ "\"password\": \"tajnasifra\","
								+ "\"email\": \"ana@example.com\","
								+ "\"telefon\": \"061/111-111\","
								+ "\"spol\": \"ZENSKI\","
								+ "\"godine\": 25,"
								+ "\"adresa\": \"Sarajevo bb\""
								+ "}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Korisnik je uspjesno kreiran."))
				.andExpect(jsonPath("$.korisnik.ime").value("Ana"))
				.andExpect(jsonPath("$.korisnik.prezime").value("Anić"))
				.andExpect(jsonPath("$.korisnik.email").value("ana@example.com"))
				.andExpect(jsonPath("$.korisnik.username").value("ana123"))
				.andExpect(jsonPath("$.korisnik.spol").value("ZENSKI"))
				.andExpect(jsonPath("$.korisnik.telefon").value("061/111-111"))
				.andExpect(jsonPath("$.korisnik.godine").value(25))
				.andExpect(jsonPath("$.korisnik.adresa").value("Sarajevo bb"));
	}

	@Test
	public void testUpdateKorisnik_Success() throws Exception {
		KorisnikDTOBO korisnikDTOBO = new KorisnikDTOBO();
		korisnikDTOBO.setIme("Marko");
		korisnikDTOBO.setPrezime("Markić");
		korisnikDTOBO.setEmail("marko@example.com");
		korisnikDTOBO.setUsername("marko123");
		korisnikDTOBO.setPassword("P@ssw0rd123");  // Validan password
		korisnikDTOBO.setTelefon("061/234-567");
		korisnikDTOBO.setSpol(Spol.MUSKI);  // Pretpostavljam da je Spol enum
		korisnikDTOBO.setGodine(25);
		korisnikDTOBO.setAdresa("Adresa 123");

		Korisnik korisnik = new Korisnik();
		korisnik.setId(1);
		korisnik.setIme("Marko");
		korisnik.setPrezime("Markić");
		korisnik.setEmail("marko@example.com");
		korisnik.setUsername("marko123");
		korisnik.setPassword("P@ssw0rd123");
		korisnik.setTelefon("061/234-567");
		korisnik.setSpol(Spol.MUSKI);
		korisnik.setGodine(25);
		korisnik.setAdresa("Adresa 123");

		when(korisnikService.updateKorisnik(eq(1), any(KorisnikDTOBO.class))).thenReturn(Optional.of(korisnik));

		mockMvc.perform(put("/korisnici/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"ime\": \"Marko\", \"prezime\": \"Markić\", \"email\": \"marko@example.com\", " +
								"\"username\": \"marko123\", \"password\": \"P@ssw0rd123\", \"telefon\": \"061/234-567\", " +
								"\"spol\": \"MUSKI\", \"godine\": 25, \"adresa\": \"Adresa 123\"}"))
				.andExpect(status().isOk()) // Očekivani status 200
				.andExpect(jsonPath("$.ime").value("Marko"))
				.andExpect(jsonPath("$.prezime").value("Markić"))
				.andExpect(jsonPath("$.email").value("marko@example.com"))
				.andExpect(jsonPath("$.telefon").value("061/234-567"))
				.andExpect(jsonPath("$.spol").value("MUSKI"))
				.andExpect(jsonPath("$.godine").value(25))
				.andExpect(jsonPath("$.adresa").value("Adresa 123"));
	}

	@Test
	public void testUpdateKorisnik_Failure_NotFound() throws Exception {
		// Priprema podataka za korisnika
		KorisnikDTOBO korisnikDTOBO = new KorisnikDTOBO();
		korisnikDTOBO.setIme("Marko");
		korisnikDTOBO.setPrezime("Markić");
		korisnikDTOBO.setEmail("marko@example.com");
		korisnikDTOBO.setUsername("marko123");
		korisnikDTOBO.setPassword("P@ssw0rd123");
		korisnikDTOBO.setTelefon("061/234-567");
		korisnikDTOBO.setSpol(Spol.MUSKI);
		korisnikDTOBO.setGodine(25);
		korisnikDTOBO.setAdresa("Adresa 123");

		when(korisnikService.updateKorisnik(eq(999), any(KorisnikDTOBO.class))).thenReturn(Optional.empty());

		mockMvc.perform(put("/korisnici/999")  // ID 999 ne postoji
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"ime\": \"Marko\", \"prezime\": \"Markić\", \"email\": \"marko@example.com\", " +
								"\"username\": \"marko123\", \"password\": \"P@ssw0rd123\", \"telefon\": \"061/234-567\", " +
								"\"spol\": \"MUSKI\", \"godine\": 25, \"adresa\": \"Adresa 123\"}"))
				.andExpect(status().isNotFound())  // Očekivani status 404
				.andExpect(jsonPath("$.message").value("Korisnik sa ID 999 ne postoji."));
	}


	@Test
	public void testDeleteKorisnik_Success() throws Exception {
		// Pretpostavljamo da korisnik sa ID 1 postoji
		Korisnik korisnik = new Korisnik();
		korisnik.setId(1);
		korisnik.setIme("Marko");
		korisnik.setPrezime("Markić");
		korisnik.setEmail("marko@example.com");
		korisnik.setUsername("marko123");
		korisnik.setPassword("P@ssw0rd123");
		korisnik.setTelefon("061/234-567");
		korisnik.setSpol(Spol.MUSKI);
		korisnik.setGodine(25);
		korisnik.setAdresa("Adresa 123");

		when(korisnikService.deleteKorisnik(1)).thenReturn(true);

		mockMvc.perform(delete("/korisnici/1"))  // ID 1 je korisnik koji postoji
				.andExpect(status().isOk())  // Očekivani status 200
				.andExpect(jsonPath("$.message").value("Korisnik sa ID 1 je uspjesno obrisan."));
	}

	@Test
	public void testDeleteKorisnik_NotFound() throws Exception {
		when(korisnikService.deleteKorisnik(1)).thenReturn(false);

		mockMvc.perform(delete("/korisnici/1"))  // ID 1 je korisnik koji ne postoji
				.andExpect(status().isNotFound())  // Očekivani status 404
				.andExpect(jsonPath("$.message").value("Korisnik sa ID 1 ne postoji."));
	}

}
