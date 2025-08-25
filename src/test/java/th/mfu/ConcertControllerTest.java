package th.mfu;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ConcertController.class)
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddAndListConcerts() throws Exception {
        mockMvc.perform(post("/concerts")
                .param("title", "xxxxx")
                .param("performer", "Performer A")
                .param("date", "2025-08-25")
                .param("description", "description of xxxx"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/concerts"));

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concert"));  // <-- ไม่มี s
    }

    @Test
    public void testAddAndDeleteConcerts() throws Exception {
        mockMvc.perform(post("/concerts")
                .param("title", "yyyyyy")
                .param("performer", "Performer B")
                .param("date", "2025-08-26")
                .param("description", "description of yyyyy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/concerts"));

        mockMvc.perform(get("/delete-concert/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/concerts"));

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concert"));  // <-- ไม่มี s
    }
}
