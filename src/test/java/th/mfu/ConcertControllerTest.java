package th.mfu;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ConcertController.class)
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testAddAndListConcerts() throws Exception {
        List<Concert> concerts = new ArrayList<>();
        concerts.add(new Concert("xxxxx", "Performer A", sdf.parse("2025-08-25"), "description of xxxx"));
        concerts.add(new Concert("yyyyyy", "Performer B", sdf.parse("2025-08-26"), "description of yyyyy"));

        for (Concert concert : concerts) {
            mockMvc.perform(post("/concerts")
                    .param("title", concert.getTitle())
                    .param("performer", concert.getPerformer())
                    .param("date", sdf.format(concert.getDate()))
                    .param("description", concert.getDescription()))
                    .andExpect(redirectedUrl("/concerts"));
        }

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concerts"))
                .andExpect(model().attribute("concerts", hasSize(2)));
    }

    @AfterEach
    public void resetDb() throws Exception {
        mockMvc.perform(get("/delete-concert"));
    }

    @Test
    public void testAddAndDeleteConcerts() throws Exception {
        List<Concert> concerts = new ArrayList<>();
        concerts.add(new Concert("xxxxx", "Performer A", sdf.parse("2025-08-25"), "description of xxxx"));
        concerts.add(new Concert("yyyyyy", "Performer B", sdf.parse("2025-08-26"), "description of yyyyy"));

        for (Concert concert : concerts) {
            mockMvc.perform(post("/concerts")
                    .param("title", concert.getTitle())
                    .param("performer", concert.getPerformer())
                    .param("date", sdf.format(concert.getDate()))
                    .param("description", concert.getDescription()))
                    .andExpect(redirectedUrl("/concerts"));
        }

        mockMvc.perform(get("/delete-concert/1"))
                .andExpect(redirectedUrl("/concerts"));

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concerts"))
                .andExpect(model().attribute("concerts", hasSize(1)));
    }
}
