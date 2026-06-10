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
class UserStatsControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(WebApplicationContext wac) {
        // This sets up MockMvc by hand, using core libraries that are already fully loaded
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testGetUserStats_HappyPath() throws Exception {
        // Asserting on 3 fields just like the instructions requested
        mockMvc.perform(get("/users/1/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("lena_v"))
                .andExpect(jsonPath("$.userCountry").value("NL"));
    }

    @Test
    void testGetUserStats_UserNotFound() throws Exception {
        // Verifying the 404 error behavior
        mockMvc.perform(get("/users/99999/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}