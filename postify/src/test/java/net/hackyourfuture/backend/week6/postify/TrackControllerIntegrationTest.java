package net.hackyourfuture.backend.week6.postify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TrackControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testGetLyrics_HappyPath() throws Exception {
        // Track 41 is Billie Eilish - LUNCH (guaranteed to have lyrics in the API)
        mockMvc.perform(get("/tracks/41/lyrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackId").value(41))
                .andExpect(jsonPath("$.trackTitle").value("LUNCH"))
                .andExpect(jsonPath("$.lyrics").exists());
    }

    @Test
    void testGetLyrics_TrackNotFoundInDatabase() throws Exception {
        // Track 99999 does not exist in your SQL tables
        mockMvc.perform(get("/tracks/99999/lyrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetLyrics_LyricsNotFoundInExternalApi() throws Exception {
        // Track 1 is usually a Dutch song in the template database (which the lyrics API won't have)
        mockMvc.perform(get("/tracks/1/lyrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}